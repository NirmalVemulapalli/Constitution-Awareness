package com.constitution.awareness.controller;

import com.constitution.awareness.dto.LearningResourceRequest;
import com.constitution.awareness.dto.LearningResourceResponse;
import com.constitution.awareness.service.LearningResourceService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/resources")
public class LearningResourceController {

    private final LearningResourceService learningResourceService;


    public LearningResourceController(

            LearningResourceService learningResourceService

    ) {

        this.learningResourceService =
                learningResourceService;
    }


    /*
     * =====================================
     * CITIZEN
     *
     * VIEW ALL PUBLISHED RESOURCES
     * =====================================
     */

    @GetMapping
    public ResponseEntity<Page<LearningResourceResponse>>
    getPublishedResources(

            @RequestParam(
                    defaultValue = "0"
            )
            int page,

            @RequestParam(
                    defaultValue = "10"
            )
            int size

    ) {


        Pageable pageable =

                PageRequest.of(
                        page,
                        size
                );


        return ResponseEntity.ok(

                learningResourceService
                        .getPublishedResources(
                                pageable
                        )

        );
    }


    /*
     * =====================================
     * CITIZEN
     *
     * VIEW RESOURCES BY ARTICLE
     * =====================================
     */

    @GetMapping("/article/{articleId}")
    public ResponseEntity<Page<LearningResourceResponse>>
    getResourcesByArticle(

            @PathVariable
            Long articleId,

            @RequestParam(
                    defaultValue = "0"
            )
            int page,

            @RequestParam(
                    defaultValue = "10"
            )
            int size

    ) {


        Pageable pageable =

                PageRequest.of(
                        page,
                        size
                );


        return ResponseEntity.ok(

                learningResourceService
                        .getResourcesByArticle(

                                articleId,

                                pageable

                        )

        );
    }


    /*
     * =====================================
     * EDUCATOR
     *
     * CREATE RESOURCE
     * =====================================
     */

    @PostMapping
    public ResponseEntity<LearningResourceResponse>
    createResource(

            @Valid
            @RequestBody
            LearningResourceRequest request

    ) {


        return ResponseEntity

                .status(
                        HttpStatus.CREATED
                )

                .body(

                        learningResourceService
                                .createResource(
                                        request
                                )

                );
    }


    /*
     * =====================================
     * EDUCATOR
     *
     * VIEW MY RESOURCES
     * =====================================
     */

    @GetMapping("/my")
    public ResponseEntity<Page<LearningResourceResponse>>
    getMyResources(

            @RequestParam(
                    defaultValue = "0"
            )
            int page,

            @RequestParam(
                    defaultValue = "10"
            )
            int size

    ) {


        Pageable pageable =

                PageRequest.of(
                        page,
                        size
                );


        return ResponseEntity.ok(

                learningResourceService
                        .getMyResources(
                                pageable
                        )

        );
    }


    /*
     * =====================================
     * ADMIN
     *
     * VIEW PENDING RESOURCES
     * =====================================
     */

    @GetMapping("/pending")
    public ResponseEntity<Page<LearningResourceResponse>>
    getPendingResources(

            @RequestParam(
                    defaultValue = "0"
            )
            int page,

            @RequestParam(
                    defaultValue = "10"
            )
            int size

    ) {


        Pageable pageable =

                PageRequest.of(
                        page,
                        size
                );


        return ResponseEntity.ok(

                learningResourceService
                        .getPendingResources(
                                pageable
                        )

        );
    }


    /*
     * =====================================
     * ADMIN
     *
     * PUBLISH RESOURCE
     * =====================================
     */

    @PutMapping("/{resourceId}/publish")
    public ResponseEntity<LearningResourceResponse>
    publishResource(

            @PathVariable
            Long resourceId

    ) {


        return ResponseEntity.ok(

                learningResourceService
                        .publishResource(
                                resourceId
                        )

        );
    }
}