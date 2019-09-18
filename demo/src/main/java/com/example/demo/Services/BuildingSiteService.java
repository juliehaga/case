package com.example.demo.Services;

import com.example.demo.Model.BuildingConstraint;
import com.example.demo.Model.BuildingCoordinates;
import com.example.demo.Model.BuildingProject;
import com.example.demo.BuildingSiteProperties.BuildingSiteProperties;
import com.example.demo.BuildingSiteProperties.PolygonArea;
import com.example.demo.Constraints.Constraint;
import com.example.demo.Constraints.Feature;
import com.example.demo.Repositories.BuildingConstraintRepository;
import com.example.demo.Repositories.BuildingCoordinatesRepository;
import com.example.demo.Repositories.BuildingProjectRepository;
import com.example.demo.eto.BuildingConstraintsETO;
import com.example.demo.eto.ResponseETO;
import com.example.demo.eto.UpdateConstraintETO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class BuildingSiteService {
    private BuildingProjectRepository buildingProjectRepository;
    private BuildingConstraintRepository buildingConstraintRepository;
    private BuildingCoordinatesRepository buildingCoordinatesRepository;


    public BuildingSiteService(BuildingProjectRepository buildingProjectRepository, BuildingConstraintRepository
            buildingConstraintRepository, BuildingCoordinatesRepository buildingCoordinatesRepository){
        this.buildingProjectRepository = buildingProjectRepository;
        this.buildingConstraintRepository = buildingConstraintRepository;
        this.buildingCoordinatesRepository = buildingCoordinatesRepository;
    }

    public ResponseEntity<ResponseETO> createNewProject(BuildingConstraintsETO buildingConstraintsETO){

        BuildingSiteProperties buildingSiteProperties = defineProperties(buildingConstraintsETO);


        BuildingProject buildingProject = new BuildingProject();
        long projectId = buildingProject.getId();

        //check valid input
        if (!buildingSiteProperties.validInput()){
            //height plateaus area is not covering buildingLimits. Invalid input.
            return new ResponseEntity<>(new ResponseETO(projectId, false), HttpStatus.OK);
        }

        //Store in DB
        buildingProjectRepository.save(buildingProject);

        //If valid, store the constraints in the DB
        saveConstraintToDB("buildingLimits", buildingSiteProperties.getBuildingLimits(), buildingProject);
        saveConstraintToDB("heightPlateaus", buildingSiteProperties.getHeightPlateaus(), buildingProject);
        saveConstraintToDB("splitBuildingLimit", buildingSiteProperties.getSplitBuildingLimits(), buildingProject);

        return new ResponseEntity<>(new ResponseETO(projectId, true), HttpStatus.OK);
    }

    public ResponseEntity<?> updateConstraint(UpdateConstraintETO updateConstraintETO) {

        Optional<BuildingConstraint> constraint = buildingConstraintRepository.findById(updateConstraintETO.getConstraintId());
        if (!constraint.isPresent()) {
            //Project don't exists
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            BuildingSiteProperties buildingSiteProperties = new BuildingSiteProperties();

            if(updateConstraintETO.getType().equals("heightPlateaus")){
                //oppdater akkurat denne constraint
                updateConstraint(constraint.get(), updateConstraintETO);

            } else if (updateConstraintETO.getType().equals("buildingLimits")){
                //oppdater akkurat denne constraint
                updateConstraint(constraint.get(), updateConstraintETO);
            }


            // Henter alle constraint i prosjektet
            List<BuildingConstraint> buildingLimits = buildingConstraintRepository
                    .findByBuildingProject_IdAndType(updateConstraintETO.getProjectId(), "buildingLimits");

            List<BuildingConstraint> heightPlateaus = buildingConstraintRepository
                    .findByBuildingProject_IdAndType(updateConstraintETO.getProjectId(), "heightPlateaus");

            buildingSiteProperties.setBuildingLimits(getPolygonAreas(buildingLimits));
            buildingSiteProperties.setHeightPlateaus(getPolygonAreas(heightPlateaus));

            // Beregn split building constraint på nytt, slett gamle og lagre de nye
            if(!buildingSiteProperties.validInput()){
                return new ResponseEntity<>(new ResponseETO(updateConstraintETO.getProjectId(), false), HttpStatus.OK);
            }
            buildingSiteProperties.calculatesplitBuildingLimits();

            List<BuildingConstraint> splitBuildingLimits = buildingConstraintRepository
                    .findByBuildingProject_IdAndType(updateConstraintETO.getProjectId(),"splitBuildingLimit");

            for(BuildingConstraint splitBuildConstraint: splitBuildingLimits){
                //buildingConstraintRepository.deleteById(splitBuildConstraint.getId());
            }

            Optional<BuildingProject> buildingProject = buildingProjectRepository.findById(updateConstraintETO.getProjectId());
            Date date= new Date();
            buildingProject.get().setLastModified(date.getTime());
            saveConstraintToDB("splitBuildingLimit", buildingSiteProperties.getSplitBuildingLimits(), buildingProject.get());

            return new ResponseEntity<>(new ResponseETO(updateConstraintETO.getProjectId(), false), HttpStatus.OK);
        }
    }

    public ResponseEntity<?> deleteProject(long projectID){
        return new ResponseEntity<>(HttpStatus.OK);
    }


    private List<PolygonArea> getPolygonAreas(List<BuildingConstraint> heightPlateaus) {
        List<PolygonArea> newHeightPlateaus = new ArrayList<>();

        for(BuildingConstraint buildingConstraint: heightPlateaus){
            List<BuildingCoordinates> buildingCoordinates = buildingCoordinatesRepository
                    .findByBuildingConstraint_Id(buildingConstraint.getId());
            newHeightPlateaus.add(new PolygonArea(restructureCoordinates(buildingCoordinates), buildingConstraint.getElevation()));
        }
        return newHeightPlateaus;
    }


    private BuildingSiteProperties defineProperties(BuildingConstraintsETO buildingConstraintsETO) {
        List<PolygonArea> buildingLimits = restructureConstraint(buildingConstraintsETO.getBuildingLimits());
        List<PolygonArea> heightPlateaus = restructureConstraint(buildingConstraintsETO.getHeightPlateaus());

        BuildingSiteProperties buildingSiteProperties = new BuildingSiteProperties(buildingLimits, heightPlateaus);
        buildingSiteProperties.calculatesplitBuildingLimits();
        return buildingSiteProperties;
    }

    private List<PolygonArea> restructureConstraint(Constraint constraint) {
        List<PolygonArea> buildingSiteConstraints = new ArrayList<>();

        for (Feature f : constraint.getFeatures()) {
            PolygonArea newArea = new PolygonArea();
            List<Point2D> coords = new ArrayList<>();
            newArea.setElevation(f.getProperties().getElevation());

            for (List<Double> corner : f.getGeometry().getCoordinates().get(0)) {
                Point2D cornerPoint = new Point2D.Double(corner.get(0), corner.get(1));
                coords.add(cornerPoint);
            }
            newArea.setCoordinates(coords);
            buildingSiteConstraints.add(newArea);
        }
        return buildingSiteConstraints;
    }

    private List<Point2D> restructureCoordinates(List<BuildingCoordinates> coordinates){
        List<Point2D> structuredCoordinates = new ArrayList<>();
        for (BuildingCoordinates coord: coordinates){
            structuredCoordinates.add(new Point2D.Double(coord.getX(), coord.getY()));
        }
        return structuredCoordinates;
    }

    private void saveConstraintToDB(String type, List<PolygonArea> constraints, BuildingProject buildingProject){
        for (PolygonArea area: constraints){
            BuildingConstraint constraint = new BuildingConstraint(type,
                    area.getElevation(), buildingProject);
            buildingConstraintRepository.save(constraint);

            for(Point2D coord: area.getCoordinates()){
                BuildingCoordinates coordinates = new BuildingCoordinates(coord.getX(), coord.getY(), constraint);
                buildingCoordinatesRepository.save(coordinates);
            }
        }
    }

    private void updateConstraint(BuildingConstraint constraint, UpdateConstraintETO updateConstraintETO){
        Date date= new Date();
        constraint.setLastModified(date.getTime());
        constraint.setElevation(updateConstraintETO.getNewElevation());
        buildingConstraintRepository.save(constraint);
        // Delete old coordinates
        List<BuildingCoordinates> coordinates = buildingCoordinatesRepository.findByBuildingConstraint_Id(constraint.getId());
        for(BuildingCoordinates buildingCoordinates: coordinates){
            //buildingCoordinatesRepository.delete(buildingCoordinates);
        }


        //update all coordinates
        for(List<Double> coord: updateConstraintETO.getNewCoordinates()){
            BuildingCoordinates newCoord = new BuildingCoordinates(coord.get(0), coord.get(1), constraint);
            buildingCoordinatesRepository.save(newCoord);
        }
    }



}


