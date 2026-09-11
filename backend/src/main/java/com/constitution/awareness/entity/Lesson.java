package com.constitution.awareness.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "lessons",
        indexes = {
                @Index(
                        name = "idx_lesson_module",
                        columnList = "module_id"
                ),
                @Index(
                        name = "idx_lesson_published",
                        columnList = "published"
                )
        }
)
public class Lesson {


    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;


    /*
     * =====================================
     * MODULE RELATIONSHIP
     * =====================================
     */

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "module_id",
            nullable = false
    )
    private LearningModule module;


    /*
     * =====================================
     * BASIC INFORMATION
     * =====================================
     */

    @Column(
            nullable = false,
            length = 250
    )
    private String title;


    @Column(
            length = 1000
    )
    private String description;


    /*
     * Main lesson content
     *
     * LONGTEXT allows detailed educational
     * content.
     */

    @Column(
            columnDefinition = "LONGTEXT",
            nullable = false
    )
    private String content;


    /*
     * Example:
     *
     * Beginner
     * Intermediate
     * Advanced
     */

    @Column(
            length = 50
    )
    private String difficultyLevel;


    /*
     * Estimated reading time in minutes.
     */

    @Column
    private Integer estimatedMinutes;


    /*
     * Controls lesson order inside module.
     */

    @Column(
            nullable = false
    )
    private Integer displayOrder = 0;


    /*
     * Admin publishing control.
     */

    @Column(
            nullable = false
    )
    private boolean published = false;


    /*
     * =====================================
     * TIMESTAMPS
     * =====================================
     */

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


    public LearningModule getModule() {
        return module;
    }


    public void setModule(
            LearningModule module
    ) {
        this.module = module;
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