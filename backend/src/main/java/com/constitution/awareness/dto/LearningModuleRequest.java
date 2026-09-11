package com.constitution.awareness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class LearningModuleRequest {

    @NotBlank(message = "Module title is required")
    @Size(
            max = 255,
            message = "Module title must not exceed 255 characters"
    )
    private String title;


    @Size(
            max = 2000,
            message = "Description must not exceed 2000 characters"
    )
    private String description;


    private String thumbnailUrl;


    @PositiveOrZero(
            message = "Display order cannot be negative"
    )
    private Integer displayOrder;


    private boolean published;


    public String getTitle() {
        return title;
    }

    public void setTitle(
            String title
    ) {
        this.title = title;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(
            String description
    ) {
        this.description = description;
    }


    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(
            String thumbnailUrl
    ) {
        this.thumbnailUrl = thumbnailUrl;
    }


    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(
            Integer displayOrder
    ) {
        this.displayOrder = displayOrder;
    }


    public boolean isPublished() {
        return published;
    }

    public void setPublished(
            boolean published
    ) {
        this.published = published;
    }
}