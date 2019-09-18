package com.example.demo.BuildingSiteProperties;

import lombok.Data;
import java.awt.geom.Point2D;
import java.util.List;

@Data
public class PolygonArea {
    private List<Point2D> coordinates;
    private int elevation;

    public PolygonArea(){}

    public PolygonArea(List<Point2D> coordinates, int elevation){
        this.coordinates = coordinates;
        this.elevation = elevation;
    }
}
