package com.example.demo.Model;
import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;


@Entity
@Data
public class BuildingProject {
    private @GeneratedValue @Id long id;
    private long lastModified;


    public BuildingProject(){
        Date date= new Date();
        this.lastModified = date.getTime();
    }

}
