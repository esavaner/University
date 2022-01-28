package com.project.server.controllers;

import com.project.server.model.Project;
import com.project.server.model.Scan;
import com.project.server.services.ProjectService;
import com.project.server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController

public class ProjectController {
    @Autowired
    ProjectService service;

    @GetMapping("/projectTest")
    public List<Project> getProjectTest() {
        return service.getProjectByUserEmail("admin");
    }

    @GetMapping("/projects")
    public List<Project> getProjectsForUser(@RequestHeader("Email") String email) {
        return service.getProjectByUserEmail(email);
    }

    @RequestMapping("/projects_add")
    public String addProject(@RequestHeader("Name") String name) {
        return service.addProject(name);
    }

    @RequestMapping("/projects_add_all")
    public String addAllProject(@RequestBody Project project) {
        return service.addProject(project);
    }

    @RequestMapping("/project_add_user")
    public String addUserToProject(@RequestHeader("ProjectName") String projectName, @RequestHeader("Email") String email) {
        return service.addUserToProject(email, projectName);
    }

    @RequestMapping("/project_add_tool")
    public String addToolToProject(@RequestHeader("ProjectName") String projectName, @RequestHeader("ToolName") String toolName) {
        return service.addToolToProject(toolName, projectName);
    }

    @RequestMapping("/project_remove_user")
    public String removeUserFromProject(@RequestHeader("ProjectName") String projectName, @RequestHeader("Email") String email) {
        return service.removeUserFromProject(email, projectName);
    }

    @RequestMapping("/project_remove_tool")
    public String removeToolFromProject(@RequestHeader("ProjectName") String projectName, @RequestHeader("ToolName") String toolName) {
        return service.removeToolFromProject(toolName, projectName);
    }

}
