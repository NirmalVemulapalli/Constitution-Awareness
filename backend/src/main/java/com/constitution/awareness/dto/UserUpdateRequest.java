package com.constitution.awareness.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class UserUpdateRequest {


    @NotBlank(
            message = "Name is required"
    )
    private String name;


    @NotBlank(
            message = "Role is required"
    )
    private String role;


    @NotNull(
            message = "Active status is required"
    )
    private Boolean active;


    public String getName() {

        return name;
    }


    public void setName(
            String name
    ) {

        this.name = name;
    }


    public String getRole() {

        return role;
    }


    public void setRole(
            String role
    ) {

        this.role = role;
    }


    public Boolean getActive() {

        return active;
    }


    public void setActive(
            Boolean active
    ) {

        this.active = active;
    }
}