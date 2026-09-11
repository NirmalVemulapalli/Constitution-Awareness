package com.constitution.awareness.dto;


public class LearningModuleResponse {


    private Long id;

    private String title;

    private String description;

    private String thumbnailUrl;

    private Integer displayOrder;

    private boolean published;


    public LearningModuleResponse() {
    }


    public LearningModuleResponse(

            Long id,

            String title,

            String description,

            String thumbnailUrl,

            Integer displayOrder,

            boolean published

    ) {

        this.id = id;

        this.title = title;

        this.description = description;

        this.thumbnailUrl = thumbnailUrl;

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