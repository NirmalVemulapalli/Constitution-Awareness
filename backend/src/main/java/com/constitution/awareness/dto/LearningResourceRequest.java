package com.constitution.awareness.dto;

import com.constitution.awareness.entity.ResourceType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class LearningResourceRequest {

    @NotBlank(message = "Title is required")
    @Size(
            max = 255,
            message = "Title must not exceed 255 characters"
    )
    private String title;


    @NotBlank(message = "Description is required")
    @Size(
            max = 1000,
            message = "Description must not exceed 1000 characters"
    )
    private String description;


    private String content;


    @NotNull(message = "Resource type is required")
    private ResourceType resourceType;


    private String resourceUrl;


    @NotNull(message = "Article ID is required")
    @Positive(message = "Article ID must be positive")
    private Long articleId;


    public LearningResourceRequest() {
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


    public ResourceType getResourceType() {
        return resourceType;
    }

    public void setResourceType(
            ResourceType resourceType
    ) {
        this.resourceType = resourceType;
    }


    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(
            String resourceUrl
    ) {
        this.resourceUrl = resourceUrl;
    }


    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(
            Long articleId
    ) {
        this.articleId = articleId;
    }
}