package com.example.demo.Repositories;

import com.example.demo.Model.BuildingConstraint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingConstraintRepository extends JpaRepository<BuildingConstraint, Long> {

    List<BuildingConstraint> findByBuildingProject_IdAndType(long id, String type);

}