package com.constitution.awareness.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "learning_modules",
        indexes = {
                @Index(
                        name = "idx_learning_module_title",
                        columnList = "title"
                ),
                @Index(
                        name = "idx_learning_module_published",
                        columnList = "published"
                )
        }
)
public class LearningModule {


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;


    @Column(
            nullable = false,
            length = 200
    )
    private String title;


    @Column(
            length = 1000
    )
    private String description;


    @Column(
            length = 500
    )
    private String thumbnailUrl;


    /*
     * Controls the display order
     *
     * Example:
     *
     * 1 → Introduction
     * 2 → Preamble
     * 3 → Fundamental Rights
     */
    @Column(
            nullable = false
    )
    private Integer displayOrder = 0;


    /*
     * Draft / Published control
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


    /*
     * =====================================
     * JPA LIFECYCLE
     * =====================================
     */

    @PrePersist
    protected void onCreate() {

        LocalDateTime now =
                LocalDateTime.now();

        createdAt = now;

        updatedAt = now;
    }


    @PreUpdate
    protected void onUpdate() {

        updatedAt =
                LocalDateTime.now();
    }


    /*
     * =====================================
     * GETTERS AND SETTERS
     * =====================================
     */

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