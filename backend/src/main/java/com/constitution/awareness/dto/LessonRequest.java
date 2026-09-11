package com.constitution.awareness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public class LessonRequest {

    /*
     * Nullable for update.
     *
     * If null during update,
     * lesson remains in current module.
     */
    @Positive(
            message = "Module ID must be positive"
    )
    private Long moduleId;


    @NotBlank(message = "Lesson title is required")
    @Size(
            max = 255,
            message = "Lesson title must not exceed 255 characters"
    )
    private String title;


    @Size(
            max = 2000,
            message = "Description must not exceed 2000 characters"
    )
    private String description;


    private String content;


    @Size(
            max = 50,
            message = "Difficulty level must not exceed 50 characters"
    )
    private String difficultyLevel;


    @Positive(
            message = "Estimated minutes must be positive"
    )
    private Integer estimatedMinutes;


    @PositiveOrZero(
            message = "Display order cannot be negative"
    )
    private Integer displayOrder;


    private boolean published;


    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(
            Long moduleId
    ) {
        this.moduleId = moduleId;
    }


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


    public String getContent() {
        return content;
    }

    public void setContent(
            String content
    ) {
        this.content = content;
    }


    public String getDifficultyLevel() {
        return difficultyLevel;
    }

    public void setDifficultyLevel(
            String difficultyLevel
    ) {
        this.difficultyLevel = difficultyLevel;
    }


    public Integer getEstimatedMinutes() {
        return estimatedMinutes;
    }

    public void setEstimatedMinutes(
            Integer estimatedMinutes
    ) {
        this.estimatedMinutes = estimatedMinutes;
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