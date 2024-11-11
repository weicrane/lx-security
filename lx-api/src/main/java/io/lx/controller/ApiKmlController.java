package io.lx.controller;

import io.lx.annotation.Login;
import io.lx.common.utils.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/kml")
@Tag(name = "地图解析")
public class ApiKmlController {

    @GetMapping("/parseKML")
    @Login
    public Result parseKML(@RequestParam String filePath) {
        List<List<Double>> latlngs = new ArrayList<>();

        File kmlFile = new File(filePath);
        if (!kmlFile.exists() || !kmlFile.isFile()) {
            return new Result().error("文件不存在或不是有效的文件路径");
        }

        try {
            // 初始化 XML 解析器并开启命名空间支持
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(kmlFile);
            document.getDocumentElement().normalize();

            // 获取根元素
            Element root = document.getDocumentElement();

            // 递归遍历所有 <Placemark> 和 <MultiGeometry> 元素
            parsePlacemarkOrMultiGeometry(root, latlngs);

        } catch (Exception e) {
            e.printStackTrace();
            return new Result().error("解析KML文件时发生错误: " + e.getMessage());
        }
        return new Result().ok(latlngs);
    }

    private void parsePlacemarkOrMultiGeometry(Element parentElement, List<List<Double>> latlngs) {
        // 处理 <Placemark> 元素
        NodeList placemarks = parentElement.getElementsByTagName("Placemark");
        for (int i = 0; i < placemarks.getLength(); i++) {
            Element placemark = (Element) placemarks.item(i);
            parseTrackOrLineString(placemark, latlngs);

            // 递归解析 <MultiGeometry> 元素
            NodeList multiGeometries = placemark.getElementsByTagName("MultiGeometry");
            for (int j = 0; j < multiGeometries.getLength(); j++) {
                parsePlacemarkOrMultiGeometry((Element) multiGeometries.item(j), latlngs);
            }
        }
    }

    private void parseTrackOrLineString(Element placemark, List<List<Double>> latlngs) {
        // 处理 <gx:Track> 或 <LineString> 元素
        NodeList tracks = placemark.getElementsByTagNameNS("http://www.google.com/kml/ext/2.2", "Track");
        if (tracks.getLength() == 0) {
            // 如果没有 <gx:Track> 元素，检查 <LineString>
            tracks = placemark.getElementsByTagName("LineString");
        }

        // 解析路径坐标
        for (int i = 0; i < tracks.getLength(); i++) {
            Element track = (Element) tracks.item(i);
            NodeList coords;

            if (track.getLocalName().equals("Track")) {
                // 解析 <gx:coord> 坐标
                coords = track.getElementsByTagNameNS("http://www.google.com/kml/ext/2.2", "coord");
            } else {
                // 解析 <coordinates> 坐标
                coords = track.getElementsByTagName("coordinates");
            }

            // 处理坐标数据
            for (int j = 0; j < coords.getLength(); j++) {
                String coordText = coords.item(j).getTextContent().trim();
                String[] coordLines = coordText.split("\\s+");

                for (String line : coordLines) {
                    String[] coordParts = line.split(",");
                    if (coordParts.length >= 2) {
                        try {
                            double lng = Double.parseDouble(coordParts[0].trim());
                            double lat = Double.parseDouble(coordParts[1].trim());

                            List<Double> latlng = new ArrayList<>();
                            latlng.add(lat);
                            latlng.add(lng);
                            latlngs.add(latlng);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
