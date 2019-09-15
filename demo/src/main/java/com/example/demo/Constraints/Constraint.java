package com.example.demo.Constraints;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Constraint {
    private String type;
    private List<Feature> features;

    public List<List<List<Double>>> getAllCoordinates(){
        List<List<List<Double>>> coordinateList = new ArrayList<>();

        for (Feature f: features)
            if(f != null){
                Feature firstFeature = features.get(0);
                if(firstFeature.geometry != null){
                    Geometry geometry = firstFeature.geometry;
                    if (geometry.coordinates.get(0) != null){
                        coordinateList.add(geometry.coordinates.get(0));
                    }
                }
            }

    return coordinateList;
    }

}

