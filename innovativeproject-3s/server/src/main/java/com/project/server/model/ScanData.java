package com.project.server.model;

import com.project.server.model.Scan;


public class ScanData {
    private String toolName;    // nazwa przy tworzeniu toola
    private String projectName; // nazwa projektu, którego dotyczy skan
    private String email;       // email, na który wysłać
    private String login;       // login użytkownika, który zlecił skan

    // to są dla prywatnych repo:
    private String username;
    private String password;
    private String name; // this is login/reponame:version
    private String isPrivate;

    public void setEmail(String email) {
        this.email = email;
    }

    public String getisPrivate() {
        return isPrivate;
    }

    public void setisPrivate(String isPrivate) {
        this.isPrivate = isPrivate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

	public String getEmail() {
		return email;
	}

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setToolName(String toolName) {
        this.toolName = toolName;
    }

    public String getToolName() {
        return toolName;
    }
}
