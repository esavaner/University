package com.project.server.services;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import com.project.server.model.Project;
import com.project.server.model.Scan;
import com.project.server.model.User;
import com.project.server.repository.ProjectRepository;
import com.project.server.repository.ScanRepository;

import com.project.server.services.exceptions.ProjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component

public class ScanService {
    @Autowired ScanRepository scanRepository;
    @Autowired ProjectService projectService;
    @Autowired UserService userService;

	public Collection<Scan> getScan() {
        return (Collection<Scan>) scanRepository.findAll();
	}

	public void add(Scan scan) {
        scanRepository.save(scan);
	}

	public List<Scan> getScanByEmail(String email) {
        return scanRepository.findByEmail(email);
    }
	public List<Scan> getScanByLogin(String login) {
		return scanRepository.findByLogin(login);
	}

	public List<Scan> getScanByProject(String projectName) {
        return scanRepository.findByProjectName(projectName);
    }

	public void delete(long id) {
        scanRepository.deleteById(id);
	}

	public void createScanAndAdd(String date, String status, String login, String email, String toolName, String projectName, String content) {
	    Scan scan = new Scan();

	    scan.setStringDate(date);
	    scan.setStatus(status);
	    scan.setLogin(login);
	    scan.setEmail(login);
	    scan.setToolName(toolName);
	    scan.setProjectName(projectName);
	    scan.setLog(content);

        Project project = projectService.getProjectByName(projectName);
	    scan.setProject(project);
	    project.getScans().add(scan);

	    add(scan);
    }

}
