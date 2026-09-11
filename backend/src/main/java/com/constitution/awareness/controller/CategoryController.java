package com.constitution.awareness.controller;

import com.constitution.awareness.dto.CategoryRequest;
import com.constitution.awareness.entity.Category;
import com.constitution.awareness.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    public CategoryController(
            CategoryService service
    ) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<Category>> getAll() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<Category> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                service.getById(id)
        );
    }


    @PostMapping
    public ResponseEntity<Category> create(
            @Valid @RequestBody CategoryRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.create(request));
    }


    @PutMapping("/{id}")
    public ResponseEntity<Category> update(
            @PathVariable Long id,
            @Valid @RequestBody CategoryRequest request
    ) {

        return ResponseEntity.ok(
                service.update(id, request)
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}