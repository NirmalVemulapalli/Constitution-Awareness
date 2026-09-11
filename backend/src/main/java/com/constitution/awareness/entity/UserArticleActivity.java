package com.constitution.awareness.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "user_article_activities",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = {
                                "user_id",
                                "article_id"
                        }
                )
        }
)
public class UserArticleActivity {

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;


    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;


    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(
            name = "article_id",
            nullable = false
    )
    private Article article;


    @Column(
            nullable = false
    )
    private LocalDateTime lastViewedAt;


    @PrePersist
    @PreUpdate
    public void updateTimestamp() {

        this.lastViewedAt =
                LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }


    public void setId(
            Long id
    ) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }


    public void setUser(
            User user
    ) {
        this.user = user;
    }


    public Article getArticle() {
        return article;
    }


    public void setArticle(
            Article article
    ) {
        this.article = article;
    }


    public LocalDateTime getLastViewedAt() {
        return lastViewedAt;
    }


    public void setLastViewedAt(
            LocalDateTime lastViewedAt
    ) {
        this.lastViewedAt = lastViewedAt;
    }
}