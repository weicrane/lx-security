package io.lx.controller;

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
    public Result parseKML(@RequestParam String filePath) {
        List<List<Double>> latlngs = new ArrayList<>();  // 存储纬度和经度的列表
        try {
            // 解析KML文件
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(filePath));

            // 获取根元素
            Element root = document.getDocumentElement();

            // 处理<Placemark>元素
            NodeList placemarks = root.getElementsByTagName("Placemark");
            for (int i = 0; i < placemarks.getLength(); i++) {
                Element placemark = (Element) placemarks.item(i);

                // 处理<gx:Track>元素
                NodeList tracks = placemark.getElementsByTagName("gx:Track");
                for (int j = 0; j < tracks.getLength(); j++) {
                    Element track = (Element) tracks.item(j);
                    NodeList coords = track.getElementsByTagName("gx:coord");
                    for (int k = 0; k < coords.getLength(); k++) {
                        String coordText = coords.item(k).getTextContent().trim();
                        String[] coordParts = coordText.split(" ");
                        if (coordParts.length >= 2) {
                            double lng = Double.parseDouble(coordParts[0]);  // 经度
                            double lat = Double.parseDouble(coordParts[1]);  // 纬度

                            // 构建 [纬度, 经度] 并添加到列表中
                            List<Double> latlng = new ArrayList<>();
                            latlng.add(lat);
                            latlng.add(lng);
                            latlngs.add(latlng);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();  // 或者记录日志
        }
        return new Result().ok(latlngs);
    }
}
