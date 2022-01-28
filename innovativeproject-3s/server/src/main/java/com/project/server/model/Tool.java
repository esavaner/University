package com.project.server.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Tool {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, length = 100)
    private String name;
    private String repo;
    private boolean isPrivate;
    private String login;
    private String password;

    @ManyToMany(mappedBy = "tools", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Collection<Project> project = new ArrayList<>();

    public Collection<Project> getProject() {
        return project;
    }
    public void setProject(Collection<Project> project) {
        this.project = project;
    }

    protected Tool() {}

    public Tool(String name, String repo) {
        this.name = name;
        this.repo = repo;
    }

    @Override
    public String toString() {
        return String.format(
                "Tool[id=%d, name='%s', repo='%s']",
                id, name, repo);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setRepo(String repo) {
        this.repo = repo;
    }

    public String getRepo() {
        return repo;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
