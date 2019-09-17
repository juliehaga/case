package com.example.demo.Constraints;

import lombok.Data;

@Data
public class Feature {
    private Geometry geometry;
    private Properties properties;
    private String type;
}
