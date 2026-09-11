package com.constitution.awareness.repository;

import com.constitution.awareness.entity.Article;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import java.util.Optional;


public interface ArticleRepository
        extends JpaRepository<Article, Long> {


    /*
     * =====================================
     * FIND ARTICLE
     * =====================================
     */

    Optional<Article> findByArticleNumber(
            String articleNumber
    );


    /*
     * =====================================
     * CHECK ARTICLE EXISTS
     * =====================================
     */

    boolean existsByArticleNumber(
            String articleNumber
    );


    /*
     * =====================================
     * PUBLIC ARTICLES
     *
     * Only published articles.
     * =====================================
     */

    @EntityGraph(
            attributePaths = {
                    "part",
                    "category",
                    "createdBy"
            }
    )
    Page<Article> findByPublishedTrue(
            Pageable pageable
    );


    /*
     * =====================================
     * ADMIN ARTICLES
     *
     * Returns all articles
     * including drafts.
     * =====================================
     */

    @Override
    @EntityGraph(
            attributePaths = {
                    "part",
                    "category",
                    "createdBy"
            }
    )
    Page<Article> findAll(
            Pageable pageable
    );


    /*
     * =====================================
     * EDUCATOR ARTICLES
     *
     * Returns articles created
     * by a specific educator.
     *
     * Includes published articles
     * and drafts.
     * =====================================
     */

    @EntityGraph(
            attributePaths = {
                    "part",
                    "category",
                    "createdBy"
            }
    )
    Page<Article> findByCreatedById(

            Long userId,

            Pageable pageable

    );


    /*
     * =====================================
     * PUBLIC SEARCH
     *
     * Search only published articles.
     * =====================================
     */

    @EntityGraph(
            attributePaths = {
                    "part",
                    "category",
                    "createdBy"
            }
    )
    @Query(
            value = """
                    SELECT a
                    FROM Article a
                    WHERE a.published = true
                    AND (
                        LOWER(a.articleNumber)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.title)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.simplifiedExplanation)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.keywords)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                    """,

            countQuery = """
                    SELECT COUNT(a)
                    FROM Article a
                    WHERE a.published = true
                    AND (
                        LOWER(a.articleNumber)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.title)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.simplifiedExplanation)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.keywords)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                    """
    )
    Page<Article> searchPublishedArticles(

            @Param("keyword")
            String keyword,

            Pageable pageable

    );


    /*
     * =====================================
     * ADMIN SEARCH
     *
     * Search all articles
     * including drafts.
     * =====================================
     */

    @EntityGraph(
            attributePaths = {
                    "part",
                    "category",
                    "createdBy"
            }
    )
    @Query(
            value = """
                    SELECT a
                    FROM Article a
                    WHERE (
                        LOWER(a.articleNumber)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.title)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.simplifiedExplanation)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.keywords)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                    """,

            countQuery = """
                    SELECT COUNT(a)
                    FROM Article a
                    WHERE (
                        LOWER(a.articleNumber)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.title)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.simplifiedExplanation)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.keywords)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                    """
    )
    Page<Article> searchAllArticles(

            @Param("keyword")
            String keyword,

            Pageable pageable

    );


    /*
     * =====================================
     * EDUCATOR SEARCH
     *
     * Search only articles created
     * by a specific educator.
     *
     * Includes drafts.
     * =====================================
     */

    @EntityGraph(
            attributePaths = {
                    "part",
                    "category",
                    "createdBy"
            }
    )
    @Query(
            value = """
                    SELECT a
                    FROM Article a
                    WHERE a.createdBy.id = :userId
                    AND (
                        LOWER(a.articleNumber)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.title)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.simplifiedExplanation)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.keywords)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                    """,

            countQuery = """
                    SELECT COUNT(a)
                    FROM Article a
                    WHERE a.createdBy.id = :userId
                    AND (
                        LOWER(a.articleNumber)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.title)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.simplifiedExplanation)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))

                        OR LOWER(a.keywords)
                            LIKE LOWER(CONCAT('%', :keyword, '%'))
                    )
                    """
    )
    Page<Article> searchArticlesByCreator(

            @Param("userId")
            Long userId,

            @Param("keyword")
            String keyword,

            Pageable pageable

    );


    /*
     * =====================================
     * PUBLIC ARTICLES BY CATEGORY
     *
     * Only published articles.
     * =====================================
     */

    @EntityGraph(
            attributePaths = {
                    "part",
                    "category",
                    "createdBy"
            }
    )
    Page<Article> findByCategoryIdAndPublishedTrue(

            Long categoryId,

            Pageable pageable

    );


    /*
     * =====================================
     * PUBLIC ARTICLES BY PART
     *
     * Only published articles.
     * =====================================
     */

    @EntityGraph(
            attributePaths = {
                    "part",
                    "category",
                    "createdBy"
            }
    )
    Page<Article> findByPartIdAndPublishedTrue(

            Long partId,

            Pageable pageable

    );
}