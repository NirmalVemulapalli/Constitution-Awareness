package com.constitution.awareness.controller;

import com.constitution.awareness.dto.ArticleRequest;
import com.constitution.awareness.dto.ArticleResponse;
import com.constitution.awareness.service.ArticleService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

/*
 * =====================================
 * DEPENDENCY
 * =====================================
 */

private final ArticleService articleService;


/*
 * =====================================
 * CONSTRUCTOR
 * =====================================
 */

public ArticleController(
        ArticleService articleService
) {

    this.articleService =
            articleService;
}


/*
 * =====================================
 * PUBLIC
 *
 * GET ALL PUBLISHED ARTICLES
 *
 * GET
 * /api/articles
 * =====================================
 */

@GetMapping
public ResponseEntity<Page<ArticleResponse>> getArticles(

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

            articleService.getArticles(
                    pageable
            )
    );
}


/*
 * =====================================
 * PUBLIC
 *
 * SEARCH PUBLISHED ARTICLES
 *
 * GET
 * /api/articles/search?keyword=rights
 * =====================================
 */

@GetMapping("/search")
public ResponseEntity<Page<ArticleResponse>> search(

        @RequestParam
        String keyword,

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

            articleService.searchArticles(

                    keyword,

                    pageable

            )
    );
}


/*
 * =====================================
 * PUBLIC
 *
 * GET ARTICLES BY CATEGORY
 *
 * GET
 * /api/articles/category/{categoryId}
 *
 * Only published articles.
 * =====================================
 */

@GetMapping("/category/{categoryId}")
public ResponseEntity<Page<ArticleResponse>> getByCategory(

        @PathVariable
        Long categoryId,

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

            articleService.getByCategory(

                    categoryId,

                    pageable

            )
    );
}


/*
 * =====================================
 * PUBLIC
 *
 * GET ARTICLES BY PART
 *
 * GET
 * /api/articles/part/{partId}
 *
 * Only published articles.
 * =====================================
 */

@GetMapping("/part/{partId}")
public ResponseEntity<Page<ArticleResponse>> getByPart(

        @PathVariable
        Long partId,

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

            articleService.getByPart(

                    partId,

                    pageable

            )
    );
}


/*
 * =====================================
 * ARTICLE MANAGEMENT
 *
 * GET MANAGEABLE ARTICLES
 *
 * GET
 * /api/articles/manage
 *
 * ADMIN:
 * - All articles
 * - Published + Drafts
 *
 * EDUCATOR:
 * - Only own articles
 * - Published + Drafts
 * =====================================
 */

@PreAuthorize("hasAnyRole('ADMIN', 'EDUCATOR')")
@GetMapping("/manage")
public ResponseEntity<Page<ArticleResponse>> getManageableArticles(

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

            articleService.getManageableArticles(
                    pageable
            )
    );
}


/*
 * =====================================
 * ARTICLE MANAGEMENT
 *
 * SEARCH MANAGEABLE ARTICLES
 *
 * GET
 * /api/articles/manage/search?keyword=rights
 * =====================================
 */

@PreAuthorize("hasAnyRole('ADMIN', 'EDUCATOR')")
@GetMapping("/manage/search")
public ResponseEntity<Page<ArticleResponse>> searchManageableArticles(

        @RequestParam
        String keyword,

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

            articleService.searchManageableArticles(

                    keyword,

                    pageable

            )
    );
}


/*
 * =====================================
 * PUBLIC / AUTHORIZED
 *
 * GET SINGLE ARTICLE
 *
 * GET
 * /api/articles/{id}
 *
 * Published:
 * Public access.
 *
 * Draft:
 * Only creator or Admin.
 * =====================================
 */

@GetMapping("/{id}")
public ResponseEntity<ArticleResponse> getArticle(

        @PathVariable
        Long id

) {

    return ResponseEntity.ok(

            articleService.getArticle(
                    id
            )
    );
}


/*
 * =====================================
 * EDUCATOR / ADMIN
 *
 * CREATE ARTICLE
 *
 * POST
 * /api/articles
 * =====================================
 */

@PreAuthorize("hasAnyRole('ADMIN', 'EDUCATOR')")
@PostMapping
public ResponseEntity<ArticleResponse> createArticle(

        @Valid
        @RequestBody
        ArticleRequest request

) {

    return ResponseEntity

            .status(
                    HttpStatus.CREATED
            )

            .body(

                    articleService.createArticle(
                            request
                    )
            );
}


/*
 * =====================================
 * EDUCATOR / ADMIN
 *
 * UPDATE ARTICLE
 *
 * PUT
 * /api/articles/{id}
 *
 * ADMIN:
 * Can update any article.
 *
 * EDUCATOR:
 * Can update only own article.
 * =====================================
 */

@PreAuthorize("hasAnyRole('ADMIN', 'EDUCATOR')")
@PutMapping("/{id}")
public ResponseEntity<ArticleResponse> updateArticle(

        @PathVariable
        Long id,

        @Valid
        @RequestBody
        ArticleRequest request

) {

    return ResponseEntity.ok(

            articleService.updateArticle(

                    id,

                    request

            )
    );
}


/*
 * =====================================
 * EDUCATOR / ADMIN
 *
 * PUBLISH / UNPUBLISH ARTICLE
 *
 * PATCH
 * /api/articles/{id}/publish?published=true
 *
 * ADMIN:
 * Can change any article.
 *
 * EDUCATOR:
 * Can change only own article.
 * =====================================
 */

@PreAuthorize("hasAnyRole('ADMIN', 'EDUCATOR')")
@PatchMapping("/{id}/publish")
public ResponseEntity<ArticleResponse> updatePublishStatus(

        @PathVariable
        Long id,

        @RequestParam
        boolean published

) {

    return ResponseEntity.ok(

            articleService.updatePublishStatus(

                    id,

                    published

            )
    );
}


/*
 * =====================================
 * ADMIN ONLY
 *
 * DELETE ARTICLE
 *
 * DELETE
 * /api/articles/{id}
 *
 * ADMIN:
 * Can delete any article.
 *
 * Related quiz questions are also
 * deleted automatically.
 * =====================================
 */

@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteArticle(

        @PathVariable
        Long id

) {

    articleService.deleteArticle(
            id
    );


    return ResponseEntity
            .noContent()
            .build();
}

}
