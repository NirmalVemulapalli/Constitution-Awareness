package com.constitution.awareness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PartRequest {

    @NotBlank(message = "Part number is required")
    @Size(
            max = 50,
            message = "Part number must not exceed 50 characters"
    )
    private String partNumber;


    @NotBlank(message = "Part title is required")
    @Size(
            max = 255,
            message = "Title must not exceed 255 characters"
    )
    private String title;


    @Size(
            max = 1000,
            message = "Description must not exceed 1000 characters"
    )
    private String description;


    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}