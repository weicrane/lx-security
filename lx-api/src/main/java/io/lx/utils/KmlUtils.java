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
public class KmlUtils {
    @Value("${web.kml-path}")
    private String kmlPath;

    public List<List<Double>> parseKML(String filePath) {
        List<List<Double>> latlngs = new ArrayList<>();  // 存储纬度和经度的列表

        // 确保文件存在
        // 确保路径格式正确
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
            // 解析KML文件
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(kmlFile);

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
            throw new RenException("解析KML文件时发生错误: " + e.getMessage());
        }
        return latlngs;
    }
}
