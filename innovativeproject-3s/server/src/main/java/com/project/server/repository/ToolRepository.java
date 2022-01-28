package com.project.server.repository;

import java.util.List;
import java.util.Optional;
import com.project.server.model.Tool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolRepository extends JpaRepository<Tool, Long> {

    Optional<Tool> findByName (String name);
    Optional<Tool> findById(Long id);
}