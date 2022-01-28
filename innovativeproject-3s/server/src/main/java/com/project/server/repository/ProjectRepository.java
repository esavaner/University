package com.project.server.repository;

import com.project.server.model.Permission;
import com.project.server.model.Project;
import com.project.server.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findByName(String name);
    Optional<Project> findById(Long id);
}
