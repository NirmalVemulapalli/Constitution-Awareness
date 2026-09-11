package com.constitution.awareness.dto;

import java.time.LocalDateTime;

public class ArticleResponse {

/*
 * =====================================
 * BASIC ARTICLE INFORMATION
 * =====================================
 */

private Long id;

private String articleNumber;

private String title;

private String constitutionalText;

private String simplifiedExplanation;

private String keywords;


/*
 * =====================================
 * CONSTITUTIONAL PART
 * =====================================
 */

private Long partId;

private String partNumber;

private String partTitle;


/*
 * =====================================
 * CATEGORY
 * =====================================
 */

private Long categoryId;

private String categoryName;


/*
 * =====================================
 * ARTICLE CREATOR
 * =====================================
 */

private Long createdById;

private String createdByName;

private String createdByRole;


/*
 * =====================================
 * PUBLISH STATUS
 * =====================================
 */

private boolean published;


/*
 * =====================================
 * AUDIT TIMESTAMPS
 * =====================================
 */

private LocalDateTime createdAt;

private LocalDateTime updatedAt;


/*
 * =====================================
 * CONSTRUCTOR
 * =====================================
 */

public ArticleResponse() {
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


public String getArticleNumber() {
    return articleNumber;
}

public void setArticleNumber(
        String articleNumber
) {
    this.articleNumber = articleNumber;
}


public String getTitle() {
    return title;
}

public void setTitle(
        String title
) {
    this.title = title;
}


public String getConstitutionalText() {
    return constitutionalText;
}

public void setConstitutionalText(
        String constitutionalText
) {
    this.constitutionalText = constitutionalText;
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

public void setKeywords(
        String keywords
) {
    this.keywords = keywords;
}


/*
 * =====================================
 * PART
 * =====================================
 */

public Long getPartId() {
    return partId;
}

public void setPartId(
        Long partId
) {
    this.partId = partId;
}


public String getPartNumber() {
    return partNumber;
}

public void setPartNumber(
        String partNumber
) {
    this.partNumber = partNumber;
}


public String getPartTitle() {
    return partTitle;
}

public void setPartTitle(
        String partTitle
) {
    this.partTitle = partTitle;
}


/*
 * =====================================
 * CATEGORY
 * =====================================
 */

public Long getCategoryId() {
    return categoryId;
}

public void setCategoryId(
        Long categoryId
) {
    this.categoryId = categoryId;
}


public String getCategoryName() {
    return categoryName;
}

public void setCategoryName(
        String categoryName
) {
    this.categoryName = categoryName;
}


/*
 * =====================================
 * ARTICLE CREATOR
 * =====================================
 */

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


public String getCreatedByRole() {
    return createdByRole;
}

public void setCreatedByRole(
        String createdByRole
) {
    this.createdByRole = createdByRole;
}


/*
 * =====================================
 * PUBLISH STATUS
 * =====================================
 */

public boolean isPublished() {
    return published;
}

public void setPublished(
        boolean published
) {
    this.published = published;
}


/*
 * =====================================
 * AUDIT TIMESTAMPS
 * =====================================
 */

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
