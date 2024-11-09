package io.lx.utils;

import io.lx.common.exception.RenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class KmlUtilsBak1 {

    @Value("${web.kml-path}")
    private String kmlPath;

    public List<List<Double>> parseKML(String filePath) {
        List<List<Double>> latlngs = new ArrayList<>();  // 存储纬度和经度的列表

        if (kmlPath == null || filePath == null) {
            throw new IllegalArgumentException("kmlPath和filePath不能为空");
        }

        String path = kmlPath.endsWith(File.separator) ? kmlPath + filePath : kmlPath + File.separator + filePath;
        log.info("解析kml文件 >>>>>>>>>>>>>>>>> 地址：{}", path);

        File kmlFile = new File(path);
        if (!kmlFile.exists() || !kmlFile.isFile()) {
            throw new RenException("文件不存在或不是有效的文件路径");
        }

        try {
            // 初始化 XML 解析器，支持命名空间
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(kmlFile);
            document.getDocumentElement().normalize();

            // 获取根元素
            Element root = document.getDocumentElement();

            // 递归解析 <Placemark> 和 <MultiGeometry> 元素
            parsePlacemarkOrMultiGeometry(root, latlngs);

        } catch (Exception e) {
            log.error("解析KML文件时发生错误", e);
            throw new RenException("解析KML文件时发生错误: " + e.getMessage());
        }
        return latlngs;
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
                String[] coordLines = coordText.split("\\s+");  // 按空格或换行分割

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
                            log.error("坐标解析错误，跳过该条数据: {}", line, e);
                        }
                    }
                }
            }
        }
    }
}
