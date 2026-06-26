package com.asycoo.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "members")
public class Member {

    @Id
    private String id;
    private String name;

    @Column(unique = true)
    private String username;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    protected Member() {
    }

    public Member(String id, String name, String username, String password, Role role) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }
}
