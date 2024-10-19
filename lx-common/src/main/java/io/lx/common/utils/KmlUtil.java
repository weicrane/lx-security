package io.lx.common.utils;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Element;
import org.jdom2.Namespace;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class KmlUtil {
  public static void main(String[] args) {
    try {
      File kmlFile = new File("path/to/your/file.kml");
      SAXBuilder saxBuilder = new SAXBuilder();
      Document document = saxBuilder.build(kmlFile);

      // 解析 KML 文件
      List<Coordinate> coordinates = new ArrayList<>();
      Element rootElement = document.getRootElement();
      Namespace ns = Namespace.getNamespace("http://www.opengis.net/kml/2.2");

      // 找到所有的 Placemark 元素
      List<Element> placemarks = rootElement.getChild("Document", ns).getChildren("Placemark", ns);
      for (Element placemark : placemarks) {
        Element point = placemark.getChild("Point", ns);
        if (point != null) {
          Element coordinatesElement = point.getChild("coordinates", ns);
          if (coordinatesElement != null) {
            String[] coords = coordinatesElement.getText().trim().split(" ");
            for (String coord : coords) {
              String[] latLng = coord.split(",");
              if (latLng.length == 2) {
                double longitude = Double.parseDouble(latLng[0]);
                double latitude = Double.parseDouble(latLng[1]);
                coordinates.add(new Coordinate(latitude, longitude));
              }
            }
          }
        }
      }

      // 输出解析的经纬度
      for (Coordinate coordinate : coordinates) {
        System.out.println("Latitude: " + coordinate.getLatitude() + ", Longitude: " + coordinate.getLongitude());
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}

class Coordinate {
  private double latitude;
  private double longitude;

  public Coordinate(double latitude, double longitude) {
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }
}
