package com.project.server.repository;

import java.util.List;
import java.util.Optional;

import com.project.server.model.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    List<Permission> findByType(String type);
    Optional<Permission> findById(Long id);
}