package com.constitution.awareness.repository;

import com.constitution.awareness.entity.Article;
import com.constitution.awareness.entity.LearningResource;
import com.constitution.awareness.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LearningResourceRepository
        extends JpaRepository<LearningResource, Long> {

    /*
     * Citizens can only see
     * published resources.
     */

    Page<LearningResource> findByPublishedTrue(
            Pageable pageable
    );


    /*
     * Resources related to
     * a particular Article.
     */

    Page<LearningResource> findByArticleAndPublishedTrue(
            Article article,
            Pageable pageable
    );


    /*
     * Educator can view all resources
     * created by themselves, including
     * unpublished resources.
     */

    Page<LearningResource> findByCreatedBy(
            User user,
            Pageable pageable
    );


    /*
     * Admin can view resources waiting
     * for approval.
     */

    Page<LearningResource> findByPublishedFalse(
            Pageable pageable
    );
}