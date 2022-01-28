package com.project.server.services;

import com.project.server.model.*;
import com.project.server.repository.UserRepository;
import com.project.server.services.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.ArrayList;

@Component
public class DataService {
    @Autowired UserRepository users;
    @Autowired UserService userService;

    public boolean isDBEmpty() {
        User user;

        try {
            user = userService.getUserByEmail("admin@nokia.com");
        } catch (UserNotFoundException exc) {
            return true;
        }

        return  false;
    }

    public void addMockData() {
        ArrayList<User> testUsers = new ArrayList<>();
        testUsers.add(new User("admin@nokia.com", "admin"));
        testUsers.add(new User("user@nokia.com", "user"));

        ArrayList<User> teamUsers = new ArrayList<>();
        for (int i = 0; i < 5; ++i) {
            teamUsers.add(new User("user" + i + "@nokia.com", "user" + i));
            teamUsers.get(i).setCreated(new Date(2019-1900, 11, 10 + i));
        }

//            Permissions
        Permission adminPermission = new Permission("ADMIN");
        Permission userPermission = new Permission("USER");

        testUsers.get(0).getPermissions().add(adminPermission);
        adminPermission.getUser().add(testUsers.get(0));
        for (User u : testUsers) {
            u.getPermissions().add(userPermission);
            userPermission.getUser().add(u);
        }

        for (User u : teamUsers) {
            u.getPermissions().add(userPermission);
            userPermission.getUser().add(u);
        }

//            Projects
        Project project1 = new Project("project1");
        testUsers.get(0).getProjects().add(project1);
        project1.getUsers().add(testUsers.get(0));
        teamUsers.get(0).getProjects().add(project1);
        project1.getUsers().add(teamUsers.get(0));

        Project project2 = new Project("project2");
        testUsers.get(0).getProjects().add(project2);
        project2.getUsers().add(testUsers.get(0));
        teamUsers.get(0).getProjects().add(project2);
        project2.getUsers().add(teamUsers.get(0));
        teamUsers.get(1).getProjects().add(project2);
        project2.getUsers().add(teamUsers.get(1));

//            Tools
        ArrayList<Tool> tools = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            tools.add(new Tool("tool" + i,  "repo" + i));
            tools.get(i).getProject().add(project1);
            project1.getTools().add(tools.get(i));
        }

//            Scans
        Scan testScan = new Scan();
        Date date = new Date(2019-1900, 11, 19);
        testScan.setStringDate(date.toString());
        testScan.setStatus("positive");
        testScan.setEmail("admin@nokia.com");
        testScan.setLogin("admin@nokia.com");
        testScan.setToolName(tools.get(0).getName());
        testScan.setProject(project1);
        project1.getScans().add(testScan);

        Scan negScan = new Scan();
        date = new Date(2019-1900, 11, 19);
        negScan.setStringDate(date.toString());
        negScan.setStatus("negative");
        negScan.setEmail("admin@nokia.com");
        negScan.setLogin("admin@nokia.com");
        negScan.setToolName(tools.get(1).getName());
        negScan.setProject(project1);
        project1.getScans().add(negScan);

        Scan scan2 = new Scan();
        date = new Date(2019-1900, 11, 22);
        scan2.setStringDate(date.toString());
        scan2.setStatus("waiting");
        scan2.setEmail("admin@nokia.com");
        scan2.setLogin("admin@nokia.com");
        scan2.setToolName(tools.get(0).getName());
        scan2.setProject(project1);
        project1.getScans().add(scan2);

        Scan posScan1 = new Scan();
        date = new Date(2019-1900, 11, 17);
        posScan1.setStringDate(date.toString());
        posScan1.setStatus("positive");
        posScan1.setEmail("user1@nokia.com");
        posScan1.setLogin("user1@nokia.com");
        posScan1.setToolName(tools.get(0).getName());
        posScan1.setProject(project2);
        project2.getScans().add(posScan1);

        Scan posScan2 = new Scan();
        date = new Date(2019-1900, 11, 15);
        posScan2.setStringDate(date.toString());
        posScan2.setStatus("positive");
        posScan2.setEmail("admin@nokia.com");
        posScan2.setLogin("admin@nokia.com");
        posScan2.setToolName(tools.get(1).getName());
        posScan2.setProject(project2);
        project2.getScans().add(posScan2);

        for (User u : testUsers) {
            users.save(u);
        }

        for (User u : teamUsers) {
            users.save(u);
        }
    }
}
