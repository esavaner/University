package com.project.server.services;

import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.project.server.model.Scan;
import com.project.server.model.Tool;
import com.project.server.model.User;
import com.project.server.repository.ToolRepository;
import com.project.server.services.exceptions.ProjectAlreadyExistsException;
import com.project.server.services.exceptions.ProjectNotFoundException;
import com.project.server.model.Project;
import com.project.server.repository.ProjectRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;


import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;



import com.project.server.job.ScanJob;

import com.project.server.model.ScheduleScanResponse;
import org.quartz.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;


@Component

public class ProjectService {
    @Autowired
	ProjectRepository repository;
    @Autowired
	UserService userService;
	@Autowired
    ToolService toolService;
	@Autowired
	ToolRepository toolRepo;
	@Autowired
	private Scheduler scheduler;
	
	private static final Logger logger = LoggerFactory.getLogger(ProjectService.class);


	public List<Project> getProject() {
        return (List<Project>) repository.findAll();
	}

	public Project getProjectByName(String name) {
		Optional<Project> project = repository.findByName(name);
		return (Project) project.orElseThrow(() -> new ProjectNotFoundException(name));
	}

	public List<Project> getProjectByUserEmail(String email) {
		User user = userService.getUserByEmail(email);
		List<Project> result = userService.getProjects(user.getEmail());
		return result;
	}

	public String addProject(Project project) {
		if (repository.findByName(project.getName()).isPresent()) {
			throw  new ProjectAlreadyExistsException(project);
		} else {
			Project newProject = new Project(project.getName());
			User match;
			Tool tool;
			for (User u : project.getUsers()) {
				match = userService.getUserByEmail(u.getEmail());
				match.getProjects().add(newProject);
				newProject.getUsers().add(match);
			}
			for (Scan s : project.getScans()) {
				s.setStatus("waiting");
				newProject.getScans().add(s);
				s.setProject(newProject);
			}
			for (Tool t : project.getTools()) {
				if (toolRepo.findByName(t.getName()).isPresent()) {
					tool = toolService.getToolByName(t.getName());
					newProject.getTools().add(tool);
					tool.getProject().add(newProject);
				} else {
					toolService.add(t);
					tool = toolService.getToolByName(t.getName());
					newProject.getTools().add(tool);
					tool.getProject().add(newProject);
				}
			}
			repository.save(newProject);
			for (Scan s : project.getScans()) {
				scheduleProjectScan(s);
			}
			return "Project " + newProject.getName() + " created.";
		}
	}

	public String addProject(String name) {
		if (repository.findByName(name).isPresent()) {
			throw new ProjectAlreadyExistsException(name);
		} else {
			repository.save(new Project(name));
			return "Project " + name + " created.";
		}
	}

	public Project getProjectById(long id) {
        Optional<Project> project = repository.findById(id);
        return (Project) project.orElseThrow(() -> new ProjectNotFoundException(id));	}

	public void delete(long id) {
        repository.deleteById(id);
	}

	public String addUserToProject(String email, String projectName) {
		User user = userService.getUserByEmail(email);
		Project project = getProjectByName(projectName);

		project.getUsers().add(user);
		user.getProjects().add(project);

		return "User " + email + " added to " + projectName;
	}

	public String addToolToProject(String toolName, String projectName) {
		Tool tool = toolService.getToolByName(toolName);
		Project project = getProjectByName(projectName);

		project.getTools().add(tool);
		tool.getProject().add(project);

		return "Tool " + tool.getName() + " added to " + project.getName();
	}

	public String removeUserFromProject(String email, String projectName) {
		User user = userService.getUserByEmail(email);
		Project project = getProjectByName(projectName);

		project.getUsers().remove(user);
		user.getProjects().remove(project);

		return "User " + email + " removed from " + projectName;
	}

	public String removeToolFromProject(String toolName, String projectName) {
		Tool tool = toolService.getToolByName(toolName);
		Project project = getProjectByName(projectName);

		project.getTools().remove(tool);
		tool.getProject().remove(project);

		return "Tool " + toolName + " removed from " + projectName;
	}

	private ResponseEntity<ScheduleScanResponse> scheduleProjectScan(Scan scheduleScanRequest) {
        try {

			Tool tool = toolService.getToolByName(scheduleScanRequest.getToolName());
			
            		String dateMon = scheduleScanRequest.getStringDate();
			String[] cronHelperTable = dateMon.split("/");
			String[] repeat = dateMon.split("\\.");

			String cron = "0 0/3 * ? * * *";
			System.out.println(repeat[0]);
			System.out.println(repeat[1]);
			//String cron = String.format("0 0 12 %s %s/1 ? *",cronHelperTable[1], cronHelperTable[0]);
			if(repeat[1].equals("1")){
				cron = "0 0/4 * ? * * *";
			} else if (repeat[1].equals("2")){
				cron = String.format("0 0 12 %s/1 %s/1 ? *",cronHelperTable[1], cronHelperTable[0]);
			}else if (repeat[1].equals("3")){
				cron = String.format("0 0 12 %s/1 %s/1 ? *",cronHelperTable[1], cronHelperTable[0]);
			}else if (repeat[1].equals("4")){
				cron = String.format("0 0 12 %s %s/1 ? *",cronHelperTable[1], cronHelperTable[0]);
			}

			System.out.println(cron);
		
            JobDetail jobDetail = buildJobDetail(scheduleScanRequest, tool);
            CronTrigger trigger = newTrigger().forJob(jobDetail).withIdentity(jobDetail.getKey().getName(), "email-triggers").withSchedule(cronSchedule(cron)).build();
            scheduler.scheduleJob(jobDetail, trigger);

            ScheduleScanResponse scheduleScanResponse = new ScheduleScanResponse(true,
                    jobDetail.getKey().getName(), jobDetail.getKey().getGroup(), "Scan Scheduled Successfully!");
            return ResponseEntity.ok(scheduleScanResponse);
        } catch (Exception ex) {
			System.out.println(ex);
			ex.printStackTrace();
			
            ScheduleScanResponse scheduleScanResponse = new ScheduleScanResponse(false,
                    "Error scheduling scan. Please try later!");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(scheduleScanResponse);
        }
	}
	


	private JobDetail buildJobDetail(Scan newScan, Tool newTool) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("id", newScan.getId());      
        jobDataMap.put("email", newScan.getEmail());
        jobDataMap.put("tool", newScan.getToolName());
        jobDataMap.put("username", newTool.getLogin());
        jobDataMap.put("password", newTool.getPassword());
        jobDataMap.put("private", newTool.isPrivate());

        return JobBuilder.newJob(ScanJob.class)
                .withIdentity(UUID.randomUUID().toString(), "email-jobs")
                .withDescription("Send Scan Job")
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();
    }
}
