package com.constitution.awareness.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class ProfileUpdateRequest {


    /*
     * =====================================
     * NAME
     * =====================================
     */

    @NotBlank(
            message = "Name is required"
    )
    @Size(
            min = 2,
            max = 100,
            message =
                    "Name must be between 2 and 100 characters"
    )
    private String name;


    /*
     * =====================================
     * GETTERS
     * =====================================
     */

    public String getName() {

        return name;

    }


    /*
     * =====================================
     * SETTERS
     * =====================================
     */

    public void setName(
            String name
    ) {

        this.name =
                name;

    }

}