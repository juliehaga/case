package com.example.demo.Repositories;

import com.example.demo.Model.BuildingProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingProjectRepository extends JpaRepository<BuildingProject, Long> {

}