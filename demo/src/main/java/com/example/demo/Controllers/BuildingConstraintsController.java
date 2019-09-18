package com.example.demo.Controllers;

import com.example.demo.Services.BuildingSiteService;
import com.example.demo.eto.BuildingConstraintsETO;
import com.example.demo.eto.ResponseETO;
import com.example.demo.eto.UpdateConstraintETO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
public class BuildingConstraintsController {

    @Autowired
    BuildingSiteService buildingSiteService;



    @PostMapping("/newProject")
    ResponseEntity<ResponseETO> input(@RequestBody BuildingConstraintsETO buildingConstraintsETO){
        return buildingSiteService.createNewProject(buildingConstraintsETO);
    }

    @Transactional
    @PutMapping("/update")
    ResponseEntity<?> updateConstraint(@RequestBody UpdateConstraintETO updateConstraintETO) {
        return buildingSiteService.updateConstraint(updateConstraintETO);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteProject(@PathVariable long projectId){
        return buildingSiteService.deleteProject(projectId);
    }







}
