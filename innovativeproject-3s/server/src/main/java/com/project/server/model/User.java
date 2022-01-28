package com.project.server.model;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity

@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(allowGetters = true)
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    @Column(unique = true, length = 100)
    private String email;
    @JsonIgnore
    private String password;
    private Date created;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="user_permission",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="permission_type"))
    @JsonIgnore
    private Collection<Permission> permission=new ArrayList<>();

    public Collection<Permission> getPermissions() {
        return permission;
    }

    public void setPermissions(Collection<Permission> permissions) {
        this.permission = permissions;
    }

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name="user_project",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="project_id"))
    @JsonIgnore
    private Collection<Project> projects = new ArrayList<>();

    public Collection<Project> getProjects() {
        return projects;
    }
    public void setProjects (Collection<Project> projects) {
        this.projects = projects;
    }

    protected User() {}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, Date created) {
        this.email = email;
        this.password = password;
        this.created = created;
    }

    @Override
    public String toString() {
        return String.format(
                "User[id=%d, email='%s', password='%s']",
                id, email, password);
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getCreated() {
        return created;
    }
}