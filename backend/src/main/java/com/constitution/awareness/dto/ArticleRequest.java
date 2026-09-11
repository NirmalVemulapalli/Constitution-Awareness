package com.constitution.awareness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class ArticleRequest {

    @NotBlank(message = "Article number is required")
    @Size(
            max = 50,
            message = "Article number must not exceed 50 characters"
    )
    private String articleNumber;


    @NotBlank(message = "Title is required")
    @Size(
            max = 255,
            message = "Title must not exceed 255 characters"
    )
    private String title;


    private String constitutionalText;


    private String simplifiedExplanation;


    @Size(
            max = 1000,
            message = "Keywords must not exceed 1000 characters"
    )
    private String keywords;


    @NotNull(message = "Part ID is required")
    @Positive(message = "Part ID must be positive")
    private Long partId;


    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be positive")
    private Long categoryId;


    private boolean published = true;


    public String getArticleNumber() {
        return articleNumber;
    }

    public void setArticleNumber(String articleNumber) {
        this.articleNumber = articleNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getConstitutionalText() {
        return constitutionalText;
    }

    public void setConstitutionalText(
            String constitutionalText
    ) {
        this.constitutionalText =
                constitutionalText;
    }

    public String getSimplifiedExplanation() {
        return simplifiedExplanation;
    }

    public void setSimplifiedExplanation(
            String simplifiedExplanation
    ) {
        this.simplifiedExplanation =
                simplifiedExplanation;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Long getPartId() {
        return partId;
    }

    public void setPartId(Long partId) {
        this.partId = partId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }
}