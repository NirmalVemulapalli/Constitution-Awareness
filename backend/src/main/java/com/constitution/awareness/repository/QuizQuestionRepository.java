package com.constitution.awareness.repository;

import com.constitution.awareness.entity.QuizQuestion;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface QuizQuestionRepository
        extends JpaRepository<QuizQuestion, Long> {


    /*
     * =====================================
     * GET ACTIVE QUESTIONS
     * =====================================
     */

    List<QuizQuestion> findByActiveTrue();


    /*
     * =====================================
     * GET ACTIVE QUESTIONS BY ARTICLE
     * =====================================
     */

    List<QuizQuestion> findByArticleIdAndActiveTrue(
            Long articleId
    );


    /*
     * =====================================
     * GET ALL QUESTIONS
     *
     * ADMIN
     * =====================================
     */

    Page<QuizQuestion> findAll(
            Pageable pageable
    );


    /*
     * =====================================
     * GET QUESTIONS CREATED BY USER
     *
     * EDUCATOR
     * =====================================
     */

    Page<QuizQuestion> findByCreatedById(
            Long userId,
            Pageable pageable
    );


    /*
     * =====================================
     * DELETE QUESTIONS BY ARTICLE
     * =====================================
     */

    @Modifying
    @Query("""
            DELETE FROM QuizQuestion q
            WHERE q.article.id = :articleId
            """)
    void deleteByArticleId(

            @Param("articleId")
            Long articleId

    );
}