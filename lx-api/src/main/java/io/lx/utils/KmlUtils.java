package io.lx.utils;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class KmlUtils {
    @Value("${web.kml-path}")
    private String kmlPath;

    public  Map<String, List<double[]>> parseRoutes(String filePath) {
        // 确保路径格式正确
        if (filePath == null) {
            throw new IllegalArgumentException("filePath不能为空");
        }

        String path = kmlPath.endsWith(File.separator) ? kmlPath + filePath : kmlPath + File.separator + filePath;
        log.info("解析kml文件 >>>>>>>>>>>>>>>>> 地址：{}", path);

        Map<String, List<double[]>> routeData = new HashMap<>();
        File file = new File(path);

        // 检查文件是否存在
        if (!file.exists()) {
            log.error("文件不存在: " + path);
            return routeData;
        }

        try {
            // 使用 SAXReader 解析 KML 文件
            SAXReader reader = new SAXReader();
            Document document = reader.read(file);
            Element root = document.getRootElement();

            // 创建命名空间映射
            Map<String, String> namespaceMap = new HashMap<>();
            namespaceMap.put("kml", "http://www.opengis.net/kml/2.2");
            namespaceMap.put("gx", "http://www.google.com/kml/ext/2.2");

            // 绑定命名空间并选择所有 <Placemark> 元素
            XPath xpath = document.createXPath("//kml:Placemark");
            xpath.setNamespaceURIs(namespaceMap);
            List<Node> placemarks = xpath.selectNodes(root);

            for (Node node : placemarks) {
                Element placemark = (Element) node;
                String name = placemark.elementText("name");

                List<double[]> coordinates = new ArrayList<>();

                // 尝试解析 <gx:Track> 元素中的坐标
                XPath gxTrackXPath = placemark.createXPath(".//gx:Track/gx:coord");
                gxTrackXPath.setNamespaceURIs(namespaceMap);
                List<Node> gxCoords = gxTrackXPath.selectNodes(placemark);
                for (Node coord : gxCoords) {
                    String[] parts = coord.getText().trim().split(" ");
                    if (parts.length >= 2) {
                        double lon = Double.parseDouble(parts[0]);
                        double lat = Double.parseDouble(parts[1]);
                        coordinates.add(new double[]{lat, lon});
                    }
                }

                // 如果没有 <gx:Track>，尝试解析 <LineString> 中的坐标
                if (coordinates.isEmpty()) {
                    Element lineString = placemark.element("LineString");
                    if (lineString != null) {
                        String[] coordText = lineString.elementText("coordinates").trim().split("\\s+");
                        for (String coord : coordText) {
                            String[] parts = coord.split(",");
                            if (parts.length >= 2) {
                                double lon = Double.parseDouble(parts[0]);
                                double lat = Double.parseDouble(parts[1]);
                                coordinates.add(new double[]{lat, lon});
                            }
                        }
                    }
                }

                // 将路线名称及坐标数据加入 map
                if (name != null && !coordinates.isEmpty()) {
                    routeData.put(name, coordinates);
                }
            }

        } catch (Exception e) {
            log.error("解析 KML 文件时发生错误: " + e.getMessage(), e);
        }

        return routeData;
    }
}
