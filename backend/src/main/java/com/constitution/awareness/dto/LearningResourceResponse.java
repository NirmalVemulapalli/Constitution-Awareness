package com.constitution.awareness.dto;

import com.constitution.awareness.entity.ResourceType;

import java.time.LocalDateTime;

public class LearningResourceResponse {

    private Long id;

    private String title;

    private String description;

    private String content;

    private ResourceType resourceType;

    private String resourceUrl;

    private Long articleId;

    private String articleNumber;

    private String articleTitle;

    private Long createdById;

    private String createdByName;

    private boolean published;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;


    public LearningResourceResponse() {
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


    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(
            String articleNumber
    ) {
        this.articleNumber = articleNumber;
    }


    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(
            String articleTitle
    ) {
        this.articleTitle = articleTitle;
    }


    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(
            Long createdById
    ) {
        this.createdById = createdById;
    }


    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(
            String createdByName
    ) {
        this.createdByName = createdByName;
    }


    public boolean isPublished() {
        return published;
    }

    public void setPublished(
            boolean published
    ) {
        this.published = published;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(
            LocalDateTime createdAt
    ) {
        this.createdAt = createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(
            LocalDateTime updatedAt
    ) {
        this.updatedAt = updatedAt;
    }
}