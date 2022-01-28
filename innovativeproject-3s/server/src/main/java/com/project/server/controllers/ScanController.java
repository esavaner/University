package com.project.server.controllers;

import java.util.List;

import com.project.server.model.Scan;
import com.project.server.model.User;
import com.project.server.services.ScanService;
import com.project.server.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController

public class ScanController {

    @Autowired
    ScanService service;


    @GetMapping("/scanTest")
    public List<Scan> getScanForTest() {
        return service.getScanByEmail("admin@nokia.com");
    }


    @GetMapping("/scans")
    public List<Scan> getScanForUser(@RequestHeader ("Email") String email) {
        return service.getScanByEmail(email);
    }
}