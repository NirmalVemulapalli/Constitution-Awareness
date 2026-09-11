package com.constitution.awareness.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "learning_resources",
        indexes = {
                @Index(
                        name = "idx_resource_article",
                        columnList = "article_id"
                ),
                @Index(
                        name = "idx_resource_creator",
                        columnList = "created_by"
                ),
                @Index(
                        name = "idx_resource_type",
                        columnList = "resourceType"
                )
        }
)
public class LearningResource {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;


    @Column(
            nullable = false
    )
    private String title;


    @Column(
            columnDefinition = "TEXT",
            nullable = false
    )
    private String description;


    @Column(
            columnDefinition = "TEXT"
    )
    private String content;


    @Enumerated(
            EnumType.STRING
    )
    @Column(
            nullable = false
    )
    private ResourceType resourceType;


    /*
     * Optional external link.
     *
     * Useful for VIDEO resources or
     * other external learning material.
     */

    private String resourceUrl;


    /*
     * Related Constitution Article.
     */

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "article_id",
            nullable = false
    )
    private Article article;


    /*
     * Educator who created this resource.
     */

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "created_by",
            nullable = false
    )
    private User createdBy;


    /*
     * Admin can control whether
     * citizens can see this resource.
     */

    @Column(
            nullable = false
    )
    private boolean published = false;


    @Column(
            nullable = false,
            updatable = false
    )
    private LocalDateTime createdAt;


    @Column(
            nullable = false
    )
    private LocalDateTime updatedAt;


    @PrePersist
    public void onCreate() {

        this.createdAt =
                LocalDateTime.now();

        this.updatedAt =
                LocalDateTime.now();
    }


    @PreUpdate
    public void onUpdate() {

        this.updatedAt =
                LocalDateTime.now();
    }


    public LearningResource() {
    }


    public Long getId() {
        return id;
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
        this.resourceType =
                resourceType;
    }


    public String getResourceUrl() {
        return resourceUrl;
    }


    public void setResourceUrl(
            String resourceUrl
    ) {
        this.resourceUrl =
                resourceUrl;
    }


    public Article getArticle() {
        return article;
    }


    public void setArticle(
            Article article
    ) {
        this.article =
                article;
    }


    public User getCreatedBy() {
        return createdBy;
    }


    public void setCreatedBy(
            User createdBy
    ) {
        this.createdBy =
                createdBy;
    }


    public boolean isPublished() {
        return published;
    }


    public void setPublished(
            boolean published
    ) {
        this.published =
                published;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}