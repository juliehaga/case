package com.example.demo.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class BuildingCoordinates {
    private @GeneratedValue @Id long id;

    private Double x;
    private Double y;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="buildingConstraint", nullable=false)
    BuildingConstraint buildingConstraint;

    public BuildingCoordinates(){
    }

    public BuildingCoordinates(Double x, Double y, BuildingConstraint buildingConstraint){
        this.x = x;
        this.y = y;
        this.buildingConstraint = buildingConstraint;
    }
}