package com.constitution.awareness.controller;

import com.constitution.awareness.dto.PartRequest;
import com.constitution.awareness.entity.Part;
import com.constitution.awareness.service.PartService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/parts")
public class PartController {

    private final PartService service;


    public PartController(
            PartService service
    ) {
        this.service = service;
    }


    @GetMapping
    public ResponseEntity<List<Part>> getAll() {

        return ResponseEntity.ok(
                service.getAll()
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<Part> getById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                service.getById(id)
        );
    }


    @PostMapping
    public ResponseEntity<Part> create(
            @Valid @RequestBody PartRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        service.create(request)
                );
    }


    @PutMapping("/{id}")
    public ResponseEntity<Part> update(

            @PathVariable Long id,

            @Valid @RequestBody
            PartRequest request
    ) {

        return ResponseEntity.ok(
                service.update(
                        id,
                        request
                )
        );
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        service.delete(id);

        return ResponseEntity.noContent()
                .build();
    }
}