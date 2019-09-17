package com.example.demo.BuildingSite;

import lombok.Data;
import java.awt.geom.Point2D;
import java.util.List;

@Data
public class PolygonArea {
    private List<Point2D> coordinates;
    private int elevation;
}
