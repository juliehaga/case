package com.example.demo.BuildinSiteTest;

import com.example.demo.BuildingSite.BuildingSite;
import org.junit.Assert;
import org.junit.Test;

import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class buildingSiteTest {
    BuildingSite buildingSite = new BuildingSite();


    @Test
    public void testIntersectionbetweenLines(){
        Line2D line1 = new Line2D.Double(1.0, 1.0, 1.5, 3.0);
        Line2D line2 = new Line2D.Double(1.0, 1.0, 4.0, 1.5);

        Point2D calculatedIntersection = buildingSite.pointsOfInterSectionLines(line1, line2);
        Point2D intersection = new Point2D.Double(1.0, 1.0);
        Assert.assertEquals(intersection, calculatedIntersection);

        Line2D line3 = new Line2D.Double(1.0, 0.5, 3.0, 3.5);
        Line2D line4 = new Line2D.Double(0.5, 2.5, 3.5, 1.5);

        Point2D calculatedIntersection2 = buildingSite.pointsOfInterSectionLines(line3, line4);
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


        List<Line2D> calculatedAllLines = buildingSite.polygonToLines(polygon);

        Line2D line1 = new Line2D.Double(1.5, 1.0, 3.0, 1.5);
        Line2D line2 = new Line2D.Double(3.0, 1.5, 3.0, 3.0);
        Line2D line3 = new Line2D.Double(3.0, 3.0, 2.0, 3.5);
        Line2D line4 = new Line2D.Double(2.0, 3.5, 1.5, 1.0);

        List<Line2D> allLines = new ArrayList<>();

        allLines.add(line1);
        allLines.add(line2);
        allLines.add(line3);
        allLines.add(line4);
        //check equal

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

        BuildingSite buildingSite1 = new BuildingSite();
        Set<Point2D> intersectionpoints = buildingSite1.calculateOverlapArea(pol1, pol2);


        Set<Point2D> points = new HashSet<>();
        points.add(new Point2D.Double(4,6 ));
        points.add(new Point2D.Double(6,4 ));
        points.add(new Point2D.Double(8,6 ));
        points.add(new Point2D.Double(6,8 ));
        points.add(new Point2D.Double(5,8 ));
        points.add(new Point2D.Double(7,4 ));
    }


}
