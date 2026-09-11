package com.constitution.awareness.service;

import com.constitution.awareness.dto.ArticleRequest;
import com.constitution.awareness.dto.ArticleResponse;

import com.constitution.awareness.entity.Article;
import com.constitution.awareness.entity.Category;
import com.constitution.awareness.entity.Part;
import com.constitution.awareness.entity.Role;
import com.constitution.awareness.entity.User;

import com.constitution.awareness.exception.DuplicateResourceException;
import com.constitution.awareness.exception.ResourceNotFoundException;

import com.constitution.awareness.repository.ArticleRepository;
import com.constitution.awareness.repository.CategoryRepository;
import com.constitution.awareness.repository.PartRepository;
import com.constitution.awareness.repository.QuizQuestionRepository;
import com.constitution.awareness.repository.UserRepository;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;


@Service
public class ArticleService {


    /*
     * =====================================
     * DEPENDENCIES
     * =====================================
     */

    private final ArticleRepository articleRepository;

    private final CategoryRepository categoryRepository;

    private final PartRepository partRepository;

    private final UserRepository userRepository;

    private final QuizQuestionRepository quizQuestionRepository;


    /*
     * =====================================
     * CONSTRUCTOR
     * =====================================
     */

    public ArticleService(

            ArticleRepository articleRepository,

            CategoryRepository categoryRepository,

            PartRepository partRepository,

            UserRepository userRepository,

            QuizQuestionRepository quizQuestionRepository

    ) {

        this.articleRepository =
                articleRepository;

        this.categoryRepository =
                categoryRepository;

        this.partRepository =
                partRepository;

        this.userRepository =
                userRepository;

        this.quizQuestionRepository =
                quizQuestionRepository;
    }


    /*
     * =====================================
     * CREATE ARTICLE
     * =====================================
     */

    public ArticleResponse createArticle(
            ArticleRequest request
    ) {

        User currentUser =
                getCurrentUser();


        if (
                articleRepository.existsByArticleNumber(
                        request.getArticleNumber()
                )
        ) {

            throw new DuplicateResourceException(
                    "Article number already exists"
            );
        }


        Part part =
                partRepository.findById(
                                request.getPartId()
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Part not found"
                                )
                        );


        Category category =
                categoryRepository.findById(
                                request.getCategoryId()
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Category not found"
                                )
                        );


        Article article =
                new Article();


        article.setArticleNumber(
                request.getArticleNumber()
        );

        article.setTitle(
                request.getTitle()
        );

        article.setConstitutionalText(
                request.getConstitutionalText()
        );

        article.setSimplifiedExplanation(
                request.getSimplifiedExplanation()
        );

        article.setKeywords(
                request.getKeywords()
        );

        article.setPart(
                part
        );

        article.setCategory(
                category
        );

        article.setCreatedBy(
                currentUser
        );

        article.setPublished(
                request.isPublished()
        );


        return mapToResponse(
                articleRepository.save(
                        article
                )
        );
    }


    /*
     * =====================================
     * UPDATE ARTICLE
     * =====================================
     */

    public ArticleResponse updateArticle(

            Long articleId,

            ArticleRequest request

    ) {

        User currentUser =
                getCurrentUser();


        Article article =
                articleRepository.findById(
                                articleId
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Article not found"
                                )
                        );


        validateArticleOwnership(
                article,
                currentUser
        );


        articleRepository
                .findByArticleNumber(
                        request.getArticleNumber()
                )
                .ifPresent(
                        existingArticle -> {

                            if (
                                    !existingArticle.getId()
                                            .equals(articleId)
                            ) {

                                throw new DuplicateResourceException(
                                        "Article number already exists"
                                );
                            }
                        }
                );


        Part part =
                partRepository.findById(
                                request.getPartId()
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Part not found"
                                )
                        );


        Category category =
                categoryRepository.findById(
                                request.getCategoryId()
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Category not found"
                                )
                        );


        article.setArticleNumber(
                request.getArticleNumber()
        );

        article.setTitle(
                request.getTitle()
        );

        article.setConstitutionalText(
                request.getConstitutionalText()
        );

        article.setSimplifiedExplanation(
                request.getSimplifiedExplanation()
        );

        article.setKeywords(
                request.getKeywords()
        );

        article.setPart(
                part
        );

        article.setCategory(
                category
        );

        article.setPublished(
                request.isPublished()
        );


        return mapToResponse(
                articleRepository.save(
                        article
                )
        );
    }


    /*
     * =====================================
     * CHANGE PUBLISH STATUS
     * =====================================
     */

    public ArticleResponse updatePublishStatus(

            Long id,

            boolean published

    ) {

        User currentUser =
                getCurrentUser();


        Article article =
                articleRepository.findById(
                                id
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Article not found"
                                )
                        );


        validateArticleOwnership(
                article,
                currentUser
        );


        article.setPublished(
                published
        );


        return mapToResponse(
                articleRepository.save(
                        article
                )
        );
    }


    /*
     * =====================================
     * GET MY ARTICLES
     * =====================================
     */

    public Page<ArticleResponse> getMyArticles(
            Pageable pageable
    ) {

        User currentUser =
                getCurrentUser();


        return articleRepository
                .findByCreatedById(
                        currentUser.getId(),
                        pageable
                )
                .map(
                        this::mapToResponse
                );
    }


    /*
     * =====================================
     * GET ALL ARTICLES FOR MANAGEMENT
     * =====================================
     */

    public Page<ArticleResponse> getAllArticlesForManagement(
            Pageable pageable
    ) {

        User currentUser =
                getCurrentUser();


        if (
                currentUser.getRole() != Role.ADMIN
        ) {

            throw new AccessDeniedException(
                    "Only administrators can view all articles."
            );
        }


        return articleRepository
                .findAll(pageable)
                .map(
                        this::mapToResponse
                );
    }


    /*
     * =====================================
     * GET SINGLE ARTICLE
     * =====================================
     */

    public ArticleResponse getArticle(
            Long id
    ) {

        Article article =
                articleRepository.findById(
                                id
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Article not found"
                                )
                        );


        if (!article.isPublished()) {

            Authentication authentication =
                    SecurityContextHolder
                            .getContext()
                            .getAuthentication();


            boolean authenticated =
                    authentication != null
                            &&
                    authentication.isAuthenticated()
                            &&
                    !"anonymousUser".equals(
                            authentication.getPrincipal()
                    );


            if (!authenticated) {

                throw new ResourceNotFoundException(
                        "Article not found"
                );
            }


            User currentUser =
                    getCurrentUser();


            validateArticleOwnership(
                    article,
                    currentUser
            );
        }


        return mapToResponse(
                article
        );
    }


    /*
     * =====================================
     * GET ALL PUBLISHED ARTICLES
     * =====================================
     */

    public Page<ArticleResponse> getArticles(
            Pageable pageable
    ) {

        return articleRepository
                .findByPublishedTrue(
                        pageable
                )
                .map(
                        this::mapToResponse
                );
    }


    /*
     * =====================================
     * SEARCH PUBLISHED ARTICLES
     * =====================================
     */

    public Page<ArticleResponse> searchArticles(

            String keyword,

            Pageable pageable

    ) {

        return articleRepository
                .searchPublishedArticles(
                        keyword,
                        pageable
                )
                .map(
                        this::mapToResponse
                );
    }


    /*
     * =====================================
     * GET ARTICLES BY CATEGORY
     * =====================================
     */

    public Page<ArticleResponse> getByCategory(

            Long categoryId,

            Pageable pageable

    ) {

        return articleRepository
                .findByCategoryIdAndPublishedTrue(
                        categoryId,
                        pageable
                )
                .map(
                        this::mapToResponse
                );
    }


    /*
     * =====================================
     * GET ARTICLES BY PART
     * =====================================
     */

    public Page<ArticleResponse> getByPart(

            Long partId,

            Pageable pageable

    ) {

        return articleRepository
                .findByPartIdAndPublishedTrue(
                        partId,
                        pageable
                )
                .map(
                        this::mapToResponse
                );
    }


    /*
     * =====================================
     * GET MANAGEABLE ARTICLES
     * =====================================
     */

    public Page<ArticleResponse> getManageableArticles(
            Pageable pageable
    ) {

        User currentUser =
                getCurrentUser();


        Page<Article> articles;


        if (
                currentUser.getRole() == Role.ADMIN
        ) {

            articles =
                    articleRepository.findAll(
                            pageable
                    );
        }

        else if (
                currentUser.getRole() == Role.EDUCATOR
        ) {

            articles =
                    articleRepository.findByCreatedById(
                            currentUser.getId(),
                            pageable
                    );
        }

        else {

            throw new AccessDeniedException(
                    "You are not allowed to manage articles"
            );
        }


        return articles.map(
                this::mapToResponse
        );
    }


    /*
     * =====================================
     * SEARCH MANAGEABLE ARTICLES
     * =====================================
     */

    public Page<ArticleResponse> searchManageableArticles(

            String keyword,

            Pageable pageable

    ) {

        User currentUser =
                getCurrentUser();


        Page<Article> articles;


        if (
                currentUser.getRole() == Role.ADMIN
        ) {

            articles =
                    articleRepository.searchAllArticles(
                            keyword,
                            pageable
                    );
        }

        else if (
                currentUser.getRole() == Role.EDUCATOR
        ) {

            articles =
                    articleRepository.searchArticlesByCreator(
                            currentUser.getId(),
                            keyword,
                            pageable
                    );
        }

        else {

            throw new AccessDeniedException(
                    "You are not allowed to manage articles"
            );
        }


        return articles.map(
                this::mapToResponse
        );
    }


    /*
     * =====================================
     * DELETE ARTICLE
     * =====================================
     */

    @Transactional
    public void deleteArticle(
            Long id
    ) {

        User currentUser =
                getCurrentUser();


        if (
                currentUser.getRole() != Role.ADMIN
        ) {

            throw new AccessDeniedException(
                    "Only administrators can delete articles"
            );
        }


        Article article =
                articleRepository.findById(
                                id
                        )
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Article not found"
                                )
                        );


        quizQuestionRepository.deleteByArticleId(
                article.getId()
        );


        articleRepository.delete(
                article
        );
    }


    /*
     * =====================================
     * GET CURRENT LOGGED-IN USER
     * =====================================
     */

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();


        if (
                authentication == null
                        ||
                !authentication.isAuthenticated()
                        ||
                "anonymousUser".equals(
                        authentication.getPrincipal()
                )
        ) {

            throw new AccessDeniedException(
                    "User is not authenticated"
            );
        }


        String email =
                authentication.getName();


        return userRepository
                .findByEmail(
                        email
                )
                .orElseThrow(
                        () -> new ResourceNotFoundException(
                                "Logged-in user not found"
                        )
                );
    }


    /*
     * =====================================
     * VALIDATE ARTICLE OWNERSHIP
     * =====================================
     */

    private void validateArticleOwnership(

            Article article,

            User currentUser

    ) {

        if (
                currentUser.getRole() == Role.ADMIN
        ) {

            return;
        }


        if (
                currentUser.getRole() != Role.EDUCATOR
        ) {

            throw new AccessDeniedException(
                    "You are not allowed to manage articles"
            );
        }


        if (
                article.getCreatedBy() == null
                        ||
                !article.getCreatedBy()
                        .getId()
                        .equals(
                                currentUser.getId()
                        )
        ) {

            throw new AccessDeniedException(
                    "You can only modify articles created by you."
            );
        }
    }


    /*
     * =====================================
     * MAP ENTITY TO RESPONSE
     * =====================================
     */

    private ArticleResponse mapToResponse(
            Article article
    ) {

        ArticleResponse response =
                new ArticleResponse();


        response.setId(
                article.getId()
        );

        response.setArticleNumber(
                article.getArticleNumber()
        );

        response.setTitle(
                article.getTitle()
        );

        response.setConstitutionalText(
                article.getConstitutionalText()
        );

        response.setSimplifiedExplanation(
                article.getSimplifiedExplanation()
        );

        response.setKeywords(
                article.getKeywords()
        );


        /*
         * PART
         */

        if (article.getPart() != null) {

            response.setPartId(
                    article.getPart().getId()
            );

            response.setPartNumber(
                    article.getPart()
                            .getPartNumber()
            );

            response.setPartTitle(
                    article.getPart()
                            .getTitle()
            );
        }


        /*
         * CATEGORY
         */

        if (article.getCategory() != null) {

            response.setCategoryId(
                    article.getCategory()
                            .getId()
            );

            response.setCategoryName(
                    article.getCategory()
                            .getName()
            );
        }


        /*
         * CREATOR
         */

        if (article.getCreatedBy() != null) {

            response.setCreatedById(
                    article.getCreatedBy()
                            .getId()
            );

            response.setCreatedByName(
                    article.getCreatedBy()
                            .getName()
            );

            response.setCreatedByRole(
                    article.getCreatedBy()
                            .getRole()
                            .name()
            );
        }


        /*
         * STATUS
         */

        response.setPublished(
                article.isPublished()
        );


        /*
         * TIMESTAMPS
         */

        response.setCreatedAt(
                article.getCreatedAt()
        );

        response.setUpdatedAt(
                article.getUpdatedAt()
        );


        return response;
    }
}