package com.constitution.awareness.dto;

import com.constitution.awareness.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class UserRequest {

    @NotBlank(
            message = "Name is required"
    )
    @Size(
            max = 100,
            message = "Name cannot exceed 100 characters"
    )
    private String name;


    @NotBlank(
            message = "Email is required"
    )
    @Email(
            message = "Invalid email format"
    )
    private String email;


    @Size(
            min = 6,
            message = "Password must be at least 6 characters"
    )
    private String password;


    @NotNull(
            message = "Role is required"
    )
    private Role role;


    private boolean active = true;


    public String getName() {
        return name;
    }


    public void setName(
            String name
    ) {
        this.name = name;
    }


    public String getEmail() {
        return email;
    }


    public void setEmail(
            String email
    ) {
        this.email = email;
    }


    public String getPassword() {
        return password;
    }


    public void setPassword(
            String password
    ) {
        this.password = password;
    }


    public Role getRole() {
        return role;
    }


    public void setRole(
            Role role
    ) {
        this.role = role;
    }


    public boolean isActive() {
        return active;
    }


    public void setActive(
            boolean active
    ) {
        this.active = active;
    }
}