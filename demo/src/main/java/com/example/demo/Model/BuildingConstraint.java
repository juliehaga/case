package com.example.demo.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class BuildingConstraint {
    private @GeneratedValue @Id long id;
    private String type;
    private int elevation;
    private long lastModified;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="buildingProjectId", nullable=false)
    private BuildingProject buildingProject;

    public BuildingConstraint(){}

    public BuildingConstraint(String type, int elevation, BuildingProject buildingProject){
        this.type = type;
        this.elevation = elevation;
        this.buildingProject = buildingProject;
        Date date= new Date();
        this.lastModified = date.getTime();
    }

}
