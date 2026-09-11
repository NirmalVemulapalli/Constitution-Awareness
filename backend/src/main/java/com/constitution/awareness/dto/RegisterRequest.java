package com.constitution.awareness.dto;

import com.constitution.awareness.entity.Role;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank(message = "Name is required")
    @Size(
            max = 100,
            message = "Name must not exceed 100 characters"
    )
    private String name;


    @NotBlank(message = "Email is required")
    @Email(message = "Enter a valid email")
    @Size(
            max = 150,
            message = "Email must not exceed 150 characters"
    )
    private String email;


    @NotBlank(message = "Password is required")
    @Size(
            min = 6,
            max = 100,
            message = "Password must contain between 6 and 100 characters"
    )
    private String password;


    @NotNull(message = "Role is required")
    private Role role;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public void setRole(Role role) {
        this.role = role;
    }
}