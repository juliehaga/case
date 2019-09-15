package com.example.demo.Controllers;

import com.example.demo.Services.BuildingSiteService;
import com.example.demo.eto.BuildingConstraintsETO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class NewProjectController {

    @Autowired
    BuildingSiteService buildingSiteService;



    @PostMapping("/input")
    HttpEntity<HttpStatus> input(@RequestBody BuildingConstraintsETO buildingConstraintsETO){
        return buildingSiteService.createNew(buildingConstraintsETO);
    }
}
