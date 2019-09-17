package com.example.demo.BuildingSite;

import lombok.Data;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.*;
import java.util.List;


@Data
public class BuildingSite {
    private List<PolygonArea> buildingLimits;
    private List<PolygonArea> heightPlateaus;
    private List<PolygonArea> splitBuildingLimits;

    public BuildingSite(List<PolygonArea> buildingLimits, List<PolygonArea> heightPlateaus) {
        this.buildingLimits = buildingLimits;
        this.heightPlateaus = heightPlateaus;
        this.splitBuildingLimits = new ArrayList<>();
        calculatesplitBuildingLimits();
    }


    private void calculatesplitBuildingLimits(){
        for (PolygonArea buildingArea: buildingLimits){
            for(PolygonArea heightPlat: heightPlateaus){
                PolygonArea splitBuildLimit = new PolygonArea();
                splitBuildLimit.setCoordinates(new ArrayList<>(calculateOverlapArea(buildingArea.getCoordinates(), heightPlat.getCoordinates())));
                splitBuildLimit.setElevation(heightPlat.getElevation());
                this.splitBuildingLimits.add(splitBuildLimit);
            }
        }
    }



    private Set<Point2D> calculateOverlapArea(List<Point2D> polygon1, List<Point2D> polygon2){

        Path2D.Double polygon1Shape = createPolygonShape(polygon2);
        Path2D.Double polygon2Shape = createPolygonShape(polygon2);

        Set<Point2D> intersections = pointsOfInterSectionPolygon(polygonToLines(polygon1), polygonToLines(polygon2));

        //check if corners in polygon1 is inside polygon2 polygon
        for(Point2D corner: polygon1){
            if (polygon2Shape.contains(corner)){
                intersections.add(corner);
            }
        }
        for(Point2D corner: polygon2){
            if (polygon1Shape.contains(corner)){
                intersections.add(corner);
            }
        }

        return intersections;
    }

    private Set<Point2D> pointsOfInterSectionPolygon(List<Line2D> polygon1, List<Line2D> polygon2){
        Set<Point2D> allInterSectionPoints = new HashSet<>();

        for(Line2D line: polygon1){
            for (Line2D line2 : polygon2){
                //Find all intersection points
                Point2D point = pointsOfInterSectionLines(line, line2);
                if (!Double.isNaN(point.getX()) && !Double.isNaN(point.getY())) {
                    //Valid intersection is found
                    allInterSectionPoints.add(point);
                }

            }
        }
        return allInterSectionPoints;
    }

    private Point2D pointsOfInterSectionLines(Line2D line1, Line2D line2){
        // Line1 represented as a1x + b1y = c1
        BigDecimal a1 = new BigDecimal(line1.getY2() - line1.getY1());
        BigDecimal b1 = new BigDecimal(line1.getX1() - line1.getX2());
        BigDecimal c1 = a1.multiply(new BigDecimal(line1.getX1())).add(b1.multiply(new BigDecimal(line1.getY1())));

        // Line2 represented as a2x + b2y = c2
        BigDecimal a2 = new BigDecimal(line2.getY2() - line2.getY1());
        BigDecimal b2 = new BigDecimal(line2.getX1() - line2.getX2());

        BigDecimal c2 = a2.multiply(new BigDecimal(line2.getX1())).add(b2.multiply(new BigDecimal(line2.getY1())));

        BigDecimal determinant = a1.multiply(b2).subtract(a2.multiply(b1));


        if (determinant.doubleValue()==BigDecimal.ZERO.doubleValue()) {  return new Point.Double(Double.NaN, Double.NaN); }


        BigDecimal x = (b2.multiply(c1).subtract(b1.multiply(c2))).divide(determinant, MathContext.DECIMAL128);
        BigDecimal y = (a1.multiply(c2).subtract(a2.multiply(c1))).divide(determinant, MathContext.DECIMAL128);


        Point2D intersectionPoint = new Point2D.Double(x.doubleValue(), y.doubleValue());



        //Check if the intersection point is between the segments


        if (lineSegmentContainsPoint(line1, intersectionPoint) && lineSegmentContainsPoint(line2, intersectionPoint)){
            return intersectionPoint;
        }

        return new Point2D.Double(Double.NaN, Double.NaN);
    }

    private boolean lineSegmentContainsPoint(Line2D line, Point2D point){
        if (point.getX() >= Math.min(line.getX1(), line.getX2()) && point.getX() <= Math.max(line.getX1(), line.getX2())){
            return point.getY() >= Math.min(line.getY1(), line.getY2()) && point.getY() <= Math.max(line.getY1(), line.getY2());
        }
        return false;
    }


    private Path2D.Double createPolygonShape(List<Point2D> polygon){
  
        Path2D.Double pol = new Path2D.Double(Path2D.WIND_EVEN_ODD, polygon.size());
        pol.moveTo(polygon.get(0).getX(), polygon.get(0).getY());
        
        for (int i = 1; i < polygon.size()-1; i++){
            pol.lineTo(polygon.get(i).getX(), polygon.get(i).getY());
        }
        pol.closePath();
        
        return pol; 
    }

    private List<Line2D> polygonToLines(List<Point2D> polygon){
        List<Line2D> lines = new ArrayList<>();

        for (int i = 0; i < polygon.size() -1; i++){
            Line2D.Double line = new Line2D.Double();
            line.x1 = polygon.get(i).getX();
            line.y1 = polygon.get(i).getY();


            line.x2 = polygon.get(i + 1).getX();
            line.y2 = polygon.get(i + 1).getY();

            lines.add(line);
        }
        return lines;
    }
}
