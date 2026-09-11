package com.constitution.awareness.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "articles",
        indexes = {
                @Index(
                        name = "idx_article_number",
                        columnList = "articleNumber"
                ),
                @Index(
                        name = "idx_article_title",
                        columnList = "title"
                ),
                @Index(
                        name = "idx_article_created_by",
                        columnList = "created_by"
                ),
                @Index(
                        name = "idx_article_published",
                        columnList = "published"
                ),
                @Index(
                        name = "idx_article_category_published",
                        columnList = "category_id, published"
                ),
                @Index(
                        name = "idx_article_part_published",
                        columnList = "part_id, published"
                )
        }
)
public class Article {


    /*
     * =====================================
     * PRIMARY KEY
     * =====================================
     */

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;


    /*
     * =====================================
     * ARTICLE BASIC INFORMATION
     * =====================================
     */

    @Column(
            nullable = false,
            unique = true
    )
    private String articleNumber;


    @Column(
            nullable = false
    )
    private String title;


    @Column(
            columnDefinition = "TEXT"
    )
    private String constitutionalText;


    @Column(
            columnDefinition = "TEXT"
    )
    private String simplifiedExplanation;


    @Column(
            columnDefinition = "TEXT"
    )
    private String keywords;


    /*
     * =====================================
     * PART
     * =====================================
     */

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "part_id",
            nullable = false
    )
    private Part part;


    /*
     * =====================================
     * CATEGORY
     * =====================================
     */

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "category_id",
            nullable = false
    )
    private Category category;


    /*
     * =====================================
     * ARTICLE CREATOR
     * =====================================
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
     * =====================================
     * PUBLISH STATUS
     * =====================================
     */

    @Column(
            nullable = false
    )
    private boolean published = false;


    /*
     * =====================================
     * AUDIT TIMESTAMPS
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
     * CONSTRUCTOR
     * =====================================
     */

    public Article() {
    }


    /*
     * =====================================
     * JPA LIFECYCLE
     * =====================================
     */

    @PrePersist
    protected void onCreate() {

        LocalDateTime now =
                LocalDateTime.now();

        this.createdAt =
                now;

        this.updatedAt =
                now;
    }


    @PreUpdate
    protected void onUpdate() {

        this.updatedAt =
                LocalDateTime.now();
    }


    /*
     * =====================================
     * GETTERS
     * =====================================
     */

    public Long getId() {
        return id;
    }


    public String getArticleNumber() {
        return articleNumber;
    }


    public String getTitle() {
        return title;
    }


    public String getConstitutionalText() {
        return constitutionalText;
    }


    public String getSimplifiedExplanation() {
        return simplifiedExplanation;
    }


    public String getKeywords() {
        return keywords;
    }


    public Part getPart() {
        return part;
    }


    public Category getCategory() {
        return category;
    }


    public User getCreatedBy() {
        return createdBy;
    }


    public boolean isPublished() {
        return published;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }


    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    /*
     * =====================================
     * SETTERS
     * =====================================
     */

    public void setArticleNumber(
            String articleNumber
    ) {
        this.articleNumber =
                articleNumber;
    }


    public void setTitle(
            String title
    ) {
        this.title =
                title;
    }


    public void setConstitutionalText(
            String constitutionalText
    ) {
        this.constitutionalText =
                constitutionalText;
    }


    public void setSimplifiedExplanation(
            String simplifiedExplanation
    ) {
        this.simplifiedExplanation =
                simplifiedExplanation;
    }


    public void setKeywords(
            String keywords
    ) {
        this.keywords =
                keywords;
    }


    public void setPart(
            Part part
    ) {
        this.part =
                part;
    }


    public void setCategory(
            Category category
    ) {
        this.category =
                category;
    }


    public void setCreatedBy(
            User createdBy
    ) {
        this.createdBy =
                createdBy;
    }


    public void setPublished(
            boolean published
    ) {
        this.published =
                published;
    }
}