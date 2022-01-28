package com.project.server.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class Permission {
    public enum PermissionType {
        USER,
        ADMIN;
    }

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private long id;
    private String type;

    @ManyToMany(mappedBy = "permission", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private Collection<User> user = new ArrayList<>();

    public Permission() {}

    public Permission(PermissionType type) {
        this.type = type.toString();
    }

    public Permission(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getType() {
        return type.toString();
    }
    public void setType(PermissionType type) {
        this.type = type.toString();
    }
    public Collection<User> getUser() {
        return user;
    }
    public void setUser(Collection<User> user) {
        this.user = user;
    }
}
