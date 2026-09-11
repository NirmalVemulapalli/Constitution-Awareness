package com.constitution.awareness.service;

import com.constitution.awareness.dto.LearningResourceRequest;
import com.constitution.awareness.dto.LearningResourceResponse;

import com.constitution.awareness.entity.Article;
import com.constitution.awareness.entity.LearningResource;
import com.constitution.awareness.entity.Role;
import com.constitution.awareness.entity.User;

import com.constitution.awareness.exception.ResourceNotFoundException;

import com.constitution.awareness.repository.ArticleRepository;
import com.constitution.awareness.repository.LearningResourceRepository;
import com.constitution.awareness.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;


@Service
public class LearningResourceService {


    private final LearningResourceRepository learningResourceRepository;

    private final ArticleRepository articleRepository;

    private final UserRepository userRepository;


    public LearningResourceService(

            LearningResourceRepository learningResourceRepository,

            ArticleRepository articleRepository,

            UserRepository userRepository

    ) {

        this.learningResourceRepository =
                learningResourceRepository;

        this.articleRepository =
                articleRepository;

        this.userRepository =
                userRepository;
    }


    /*
     * =====================================
     * EDUCATOR - CREATE RESOURCE
     * =====================================
     */

    public LearningResourceResponse createResource(

            LearningResourceRequest request

    ) {


        User educator =
                getLoggedInUser();


        /*
         * Only educators can create
         * learning resources.
         */

        if (
                educator.getRole() != Role.EDUCATOR
        ) {

            throw new AccessDeniedException(
                    "Only educators can create learning resources"
            );
        }


        /*
         * Find related Article
         */

        Article article =
                articleRepository
                        .findById(
                                request.getArticleId()
                        )
                        .orElseThrow(

                                () ->
                                        new ResourceNotFoundException(
                                                "Article not found"
                                        )

                        );


        /*
         * Create Resource
         */

        LearningResource resource =
                new LearningResource();


        resource.setTitle(
                request.getTitle()
        );


        resource.setDescription(
                request.getDescription()
        );


        resource.setContent(
                request.getContent()
        );


        resource.setResourceType(
                request.getResourceType()
        );


        resource.setResourceUrl(
                request.getResourceUrl()
        );


        resource.setArticle(
                article
        );


        /*
         * Automatically assign
         * logged-in educator.
         */

        resource.setCreatedBy(
                educator
        );


        /*
         * Always false initially.
         *
         * Admin must approve.
         */

        resource.setPublished(
                false
        );


        return mapToResponse(

                learningResourceRepository.save(
                        resource
                )

        );
    }


    /*
     * =====================================
     * CITIZEN - VIEW PUBLISHED RESOURCES
     * =====================================
     */

    public Page<LearningResourceResponse> getPublishedResources(

            Pageable pageable

    ) {

        return learningResourceRepository
                .findByPublishedTrue(
                        pageable
                )
                .map(
                        this::mapToResponse
                );
    }


    /*
     * =====================================
     * VIEW PUBLISHED RESOURCES
     * BY ARTICLE
     * =====================================
     */

    public Page<LearningResourceResponse> getResourcesByArticle(

            Long articleId,

            Pageable pageable

    ) {


        Article article =
                articleRepository
                        .findById(
                                articleId
                        )
                        .orElseThrow(

                                () ->
                                        new ResourceNotFoundException(
                                                "Article not found"
                                        )

                        );


        return learningResourceRepository
                .findByArticleAndPublishedTrue(

                        article,

                        pageable

                )
                .map(
                        this::mapToResponse
                );
    }


    /*
     * =====================================
     * EDUCATOR - VIEW MY RESOURCES
     * =====================================
     */

    public Page<LearningResourceResponse> getMyResources(

            Pageable pageable

    ) {


        User educator =
                getLoggedInUser();


        if (
                educator.getRole() != Role.EDUCATOR
        ) {

            throw new AccessDeniedException(
                    "Only educators can view educator resources"
            );
        }


        return learningResourceRepository
                .findByCreatedBy(

                        educator,

                        pageable

                )
                .map(
                        this::mapToResponse
                );
    }


    /*
     * =====================================
     * ADMIN - VIEW PENDING RESOURCES
     * =====================================
     */

    public Page<LearningResourceResponse> getPendingResources(

            Pageable pageable

    ) {


        User admin =
                getLoggedInUser();


        if (
                admin.getRole() != Role.ADMIN
        ) {

            throw new AccessDeniedException(
                    "Only admins can view pending resources"
            );
        }


        return learningResourceRepository
                .findByPublishedFalse(
                        pageable
                )
                .map(
                        this::mapToResponse
                );
    }


    /*
     * =====================================
     * ADMIN - PUBLISH RESOURCE
     * =====================================
     */

    public LearningResourceResponse publishResource(

            Long resourceId

    ) {


        User admin =
                getLoggedInUser();


        if (
                admin.getRole() != Role.ADMIN
        ) {

            throw new AccessDeniedException(
                    "Only admins can publish learning resources"
            );
        }


        LearningResource resource =
                learningResourceRepository
                        .findById(
                                resourceId
                        )
                        .orElseThrow(

                                () ->
                                        new ResourceNotFoundException(
                                                "Learning resource not found"
                                        )

                        );


        resource.setPublished(
                true
        );


        return mapToResponse(

                learningResourceRepository.save(
                        resource
                )

        );
    }


    /*
     * =====================================
     * GET LOGGED-IN USER
     * =====================================
     */

    private User getLoggedInUser() {


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

                        () ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )

                );
    }


    /*
     * =====================================
     * MAP ENTITY TO RESPONSE
     * =====================================
     */

    private LearningResourceResponse mapToResponse(

            LearningResource resource

    ) {


        LearningResourceResponse response =
                new LearningResourceResponse();


        response.setId(
                resource.getId()
        );


        response.setTitle(
                resource.getTitle()
        );


        response.setDescription(
                resource.getDescription()
        );


        response.setContent(
                resource.getContent()
        );


        response.setResourceType(
                resource.getResourceType()
        );


        response.setResourceUrl(
                resource.getResourceUrl()
        );


        /*
         * Article Information
         */

        response.setArticleId(
                resource.getArticle().getId()
        );


        response.setArticleNumber(
                resource.getArticle().getArticleNumber()
        );


        response.setArticleTitle(
                resource.getArticle().getTitle()
        );


        /*
         * Educator Information
         */

        response.setCreatedById(
                resource.getCreatedBy().getId()
        );


        response.setCreatedByName(
                resource.getCreatedBy().getName()
        );


        /*
         * Status
         */

        response.setPublished(
                resource.isPublished()
        );


        /*
         * Timestamps
         */

        response.setCreatedAt(
                resource.getCreatedAt()
        );


        response.setUpdatedAt(
                resource.getUpdatedAt()
        );


        return response;
    }
}