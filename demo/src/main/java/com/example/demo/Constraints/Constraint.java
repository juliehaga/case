package com.example.demo.Constraints;

import lombok.Data;
import java.util.List;

@Data
public class Constraint {
    private String type;
    private List<Feature> features;

}

