package com.example.demo.Services;

import com.example.demo.BuildingSite.BuildingSite;
import com.example.demo.eto.BuildingConstraintsETO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;


@Service
public class BuildingSiteService {

    public HttpEntity<HttpStatus> createNew(BuildingConstraintsETO buildingConstraintsETO){
        BuildingSite buildingSite = new BuildingSite();
       // buildingSite.setBuildingLimits(buildingConstraintsETO.getBuildingLimits());
       // buildingSite.setHeightPlateaus(buildingConstraintsETO.getHeightPlateaus());

        //Beregn Splittet building Limitations
      // buildingSite.findSplitBuildingLimits();

        //Store til Database

        return new HttpEntity<>(HttpStatus.OK);
    }

    HttpEntity<HttpStatus> update(){
        //Denne trenger også å få inn hvilket prosjekt vi skal oppdatere
        return new HttpEntity<>(HttpStatus.OK);
    }
}
