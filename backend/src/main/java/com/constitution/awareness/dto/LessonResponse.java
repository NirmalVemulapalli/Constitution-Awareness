package com.constitution.awareness.dto;


public class LessonResponse {


    private Long id;

    private Long moduleId;

    private String moduleTitle;

    private String title;

    private String description;

    private String content;

    private String difficultyLevel;

    private Integer estimatedMinutes;

    private Integer displayOrder;

    private boolean published;


    public LessonResponse() {
    }


    public LessonResponse(

            Long id,

            Long moduleId,

            String moduleTitle,

            String title,

            String description,

            String content,

            String difficultyLevel,

            Integer estimatedMinutes,

            Integer displayOrder,

            boolean published

    ) {

        this.id = id;

        this.moduleId = moduleId;

        this.moduleTitle = moduleTitle;

        this.title = title;

        this.description = description;

        this.content = content;

        this.difficultyLevel = difficultyLevel;

        this.estimatedMinutes = estimatedMinutes;

        this.displayOrder = displayOrder;

        this.published = published;
    }


    public Long getId() {
        return id;
    }


    public void setId(
            Long id
    ) {
        this.id = id;
    }


    public Long getModuleId() {
        return moduleId;
    }


    public void setModuleId(
            Long moduleId
    ) {
        this.moduleId = moduleId;
    }


    public String getModuleTitle() {
        return moduleTitle;
    }


    public void setModuleTitle(
            String moduleTitle
    ) {
        this.moduleTitle = moduleTitle;
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