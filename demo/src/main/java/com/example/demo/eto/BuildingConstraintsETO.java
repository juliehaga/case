package com.example.demo.eto;

import com.example.demo.Constraints.Constraint;
import lombok.Data;


@Data
public class BuildingConstraintsETO {
    private Constraint buildingLimits;
    private Constraint heightPlateaus;
}
