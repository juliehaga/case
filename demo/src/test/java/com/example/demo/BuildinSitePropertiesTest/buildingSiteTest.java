package com.example.demo.BuildinSitePropertiesTest;

import com.example.demo.BuildingSiteProperties.BuildingSiteProperties;
import com.example.demo.BuildingSiteProperties.PolygonArea;
import org.junit.Assert;
import org.junit.Test;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;


public class buildingSiteTest {

    @Test
    public void testIntersectionbetweenLines(){
        BuildingSiteProperties buildingSiteProperties = new BuildingSiteProperties();
        Line2D line1 = new Line2D.Double(1.0, 1.0, 1.5, 3.0);
        Line2D line2 = new Line2D.Double(1.0, 1.0, 4.0, 1.5);

        Point2D calculatedIntersection = buildingSiteProperties.pointsOfInterSectionLines(line1, line2);
        Point2D intersection = new Point2D.Double(1.0, 1.0);
        Assert.assertEquals(intersection, calculatedIntersection);

        Line2D line3 = new Line2D.Double(1.0, 0.5, 3.0, 3.5);
        Line2D line4 = new Line2D.Double(0.5, 2.5, 3.5, 1.5);

        Point2D calculatedIntersection2 = buildingSiteProperties.pointsOfInterSectionLines(line3, line4);
        Point2D intersection2 = new Point2D.Double(2.0, 2.0);
        Assert.assertEquals(intersection2, calculatedIntersection2);
    }

    @Test
    public void testPolygonToLine(){
        List<Point2D> polygon = new ArrayList<>();
        Point2D point1 = new Point2D.Double(1.5, 1.0);
        Point2D point2 = new Point2D.Double(3.0, 1.5);
        Point2D point3 = new Point2D.Double(3.0, 3.0);
        Point2D point4 = new Point2D.Double(2.0, 3.5);
        Point2D point5 = new Point2D.Double(1.5, 1.0);
        polygon.add(point1);
        polygon.add(point2);
        polygon.add(point3);
        polygon.add(point4);
        polygon.add(point5);

        BuildingSiteProperties buildingSiteProperties = new BuildingSiteProperties();

        List<Line2D> calculatedAllLines = buildingSiteProperties.polygonToLines(polygon);

        Line2D line1 = new Line2D.Double(1.5, 1.0, 3.0, 1.5);
        Line2D line2 = new Line2D.Double(3.0, 1.5, 3.0, 3.0);
        Line2D line3 = new Line2D.Double(3.0, 3.0, 2.0, 3.5);
        Line2D line4 = new Line2D.Double(2.0, 3.5, 1.5, 1.0);

        List<Line2D> allLines = new ArrayList<>();
        allLines.add(line1);
        allLines.add(line2);
        allLines.add(line3);
        allLines.add(line4);


        Assert.assertEquals(line1.getX1(), calculatedAllLines.get(0).getX1(), 0);
        Assert.assertEquals(line1.getY1(), calculatedAllLines.get(0).getY1(),0 );
        Assert.assertEquals(line2.getX1(), calculatedAllLines.get(1).getX1(), 0);
        Assert.assertEquals(line2.getY1(), calculatedAllLines.get(1).getY1(), 0);
        Assert.assertEquals(line3.getX1(), calculatedAllLines.get(2).getX1(), 0);
        Assert.assertEquals(line3.getY1(), calculatedAllLines.get(2).getY1(), 0);
        Assert.assertEquals(line4.getX1(), calculatedAllLines.get(3).getX1(), 0);
        Assert.assertEquals(line4.getY1(), calculatedAllLines.get(3).getY1(), 0);

        Assert.assertEquals(line1.getX2(), calculatedAllLines.get(0).getX2(), 0);
        Assert.assertEquals(line1.getY2(), calculatedAllLines.get(0).getY2(), 0);
        Assert.assertEquals(line2.getX2(), calculatedAllLines.get(1).getX2(), 0);
        Assert.assertEquals(line2.getY2(), calculatedAllLines.get(1).getY2(), 0);
        Assert.assertEquals(line3.getX2(), calculatedAllLines.get(2).getX2(), 0);
        Assert.assertEquals(line3.getY2(), calculatedAllLines.get(2).getY2(), 0);
        Assert.assertEquals(line4.getX2(), calculatedAllLines.get(3).getX2(), 0);
        Assert.assertEquals(line4.getY2(), calculatedAllLines.get(3).getY2(), 0);

    }

    @Test
    public void testOverlapArea(){
        List<Point2D> pol1 = new ArrayList<>();
        pol1.add(new Point2D.Double(6.0, 2.0));
        pol1.add(new Point2D.Double(8.0, 6.0));
        pol1.add(new Point2D.Double(6.0, 8.0));
        pol1.add(new Point2D.Double(4.0, 8.0));
        pol1.add(new Point2D.Double(2.0, 6.0));
        pol1.add(new Point2D.Double(6.0, 2.0));


        List<Point2D> pol2 = new ArrayList<>();
        pol2.add(new Point2D.Double(6.0, 10.0));
        pol2.add(new Point2D.Double(4.0, 6.0));
        pol2.add(new Point2D.Double(6.0, 4.0));
        pol2.add(new Point2D.Double(10.0, 4.0));
        pol2.add(new Point2D.Double(10.0, 8.0));
        pol2.add(new Point2D.Double(6.0, 10.0));

        BuildingSiteProperties buildingSiteProperties1 = new BuildingSiteProperties();
        Set<Point2D> intersectionpoints = buildingSiteProperties1.calculateOverlapArea(pol1, pol2);


        Set<Point2D> points = new HashSet<>();
        points.add(new Point2D.Double(4,6 ));
        points.add(new Point2D.Double(6,4 ));
        points.add(new Point2D.Double(8,6 ));
        points.add(new Point2D.Double(6,8 ));
        points.add(new Point2D.Double(5,8 ));
        points.add(new Point2D.Double(7,4 ));

        Assert.assertTrue(points.equals(intersectionpoints));

    }



    @Test
    public void testSplitCalculations(){
        List<Point2D> pol1 = new ArrayList<>();
        pol1.add(new Point2D.Double(6.0, 2.0));
        pol1.add(new Point2D.Double(8.0, 6.0));
        pol1.add(new Point2D.Double(6.0, 8.0));
        pol1.add(new Point2D.Double(4.0, 8.0));
        pol1.add(new Point2D.Double(2.0, 6.0));
        pol1.add(new Point2D.Double(6.0, 2.0));


        List<Point2D> pol2 = new ArrayList<>();
        pol2.add(new Point2D.Double(6.0, 10.0));
        pol2.add(new Point2D.Double(4.0, 6.0));
        pol2.add(new Point2D.Double(6.0, 4.0));
        pol2.add(new Point2D.Double(6.0, 10.0));

        List<Point2D> pol3 = new ArrayList<>();
        pol3.add(new Point2D.Double(6.0, 10.0));
        pol3.add(new Point2D.Double(6.0, 4.0));
        pol3.add(new Point2D.Double(10.0, 4.0));
        pol3.add(new Point2D.Double(10.0, 8.0));
        pol3.add(new Point2D.Double(6.0, 10.0));



        PolygonArea polArea1 = new PolygonArea();
        polArea1.setElevation(0);
        polArea1.setCoordinates(pol1);

        PolygonArea polArea2 = new PolygonArea();
        polArea2.setElevation(100);
        polArea2.setCoordinates(pol2);

        PolygonArea polArea3 = new PolygonArea();
        polArea3.setElevation(70);
        polArea3.setCoordinates(pol3);


        ArrayList<PolygonArea> buildingLimits = new ArrayList<>();
        buildingLimits.add(polArea1);

        ArrayList<PolygonArea> heightPlateaus = new ArrayList<>();
        heightPlateaus.add(polArea2);
        heightPlateaus.add(polArea3);

        BuildingSiteProperties buildingSiteProperties = new BuildingSiteProperties(buildingLimits, heightPlateaus);
        System.out.println(buildingSiteProperties.getSplitBuildingLimits());

        Assert.assertEquals(2, buildingSiteProperties.getSplitBuildingLimits().size());
        Assert.assertEquals(4, buildingSiteProperties.getSplitBuildingLimits().get(0).getCoordinates().size());
        Assert.assertEquals(100, buildingSiteProperties.getSplitBuildingLimits().get(0).getElevation());
        Assert.assertEquals(4, buildingSiteProperties.getSplitBuildingLimits().get(1).getCoordinates().size());
        Assert.assertEquals(70, buildingSiteProperties.getSplitBuildingLimits().get(1).getElevation());

    }

    @Test
    public void testPolygonArea(){
        BuildingSiteProperties buildingSiteProperties = new BuildingSiteProperties();

        List<Point2D> polygon = new ArrayList<>();
        polygon.add(new Point2D.Double(6.0, 10.0));
        polygon.add(new Point2D.Double(4.0, 6.0));
        polygon.add(new Point2D.Double(6.0, 4.0));
        polygon.add(new Point2D.Double(10.0, 4.0));
        polygon.add(new Point2D.Double(6.0, 10.0));

        double area = buildingSiteProperties.calculateAreaOfPolygon(polygon);

        Assert.assertEquals(18.0, area, 0);


    }




}
