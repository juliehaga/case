package com.example.demo.eto;

import lombok.Data;

import java.util.List;

@Data
public class UpdateConstraintETO {
    private long projectId;
    private long constraintId;
    private String type;
    private List<List<Double>> newCoordinates;
    private int newElevation;
}
