package com.example.demo.Services;

import com.example.demo.BuildingSite.BuildingSite;
import com.example.demo.BuildingSite.PolygonArea;
import com.example.demo.Constraints.Constraint;
import com.example.demo.Constraints.Feature;
import com.example.demo.eto.BuildingConstraintsETO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;


@Service
public class BuildingSiteService {

    public HttpEntity<HttpStatus> createNew(BuildingConstraintsETO buildingConstraintsETO){
        List<PolygonArea> buildingLimits = restructureConstraint(buildingConstraintsETO.getBuildingLimits());
        List<PolygonArea> heightPlateaus = restructureConstraint(buildingConstraintsETO.getHeightPlateaus());

        BuildingSite buildingSite = new BuildingSite(buildingLimits, heightPlateaus);

        int a = 0;
        //Store til Database
        return new HttpEntity<>(HttpStatus.OK);
    }

    HttpEntity<HttpStatus> update(){
        //Denne trenger også å få inn hvilket prosjekt vi skal oppdatere
        return new HttpEntity<>(HttpStatus.OK);
    }

    private List<PolygonArea> restructureConstraint(Constraint constraint) {
        List<PolygonArea> buildingSiteConstraints = new ArrayList<>();

        for (Feature f : constraint.getFeatures()) {
            PolygonArea newArea = new PolygonArea();
            List<Point2D> coords = new ArrayList<>();
            newArea.setElevation(f.getProperties().getElevation());

            for (List<Double> corner : f.getGeometry().getCoordinates().get(0)) {
                Point2D cornerPoint = new Point2D.Double(corner.get(0), corner.get(1));
                coords.add(cornerPoint);
            }
            newArea.setCoordinates(coords);
            buildingSiteConstraints.add(newArea);
        }
        return buildingSiteConstraints;
    }

}


