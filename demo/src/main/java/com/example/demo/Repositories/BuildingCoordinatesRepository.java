package com.example.demo.Repositories;

import com.example.demo.Model.BuildingCoordinates;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingCoordinatesRepository extends JpaRepository<BuildingCoordinates, Long> {



    List<BuildingCoordinates> findByBuildingConstraint_Id(long id);

}