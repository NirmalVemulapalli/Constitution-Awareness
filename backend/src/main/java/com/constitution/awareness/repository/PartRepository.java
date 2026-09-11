package com.constitution.awareness.repository;

import com.constitution.awareness.entity.Part;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PartRepository
        extends JpaRepository<Part, Long> {

    Optional<Part> findByPartNumber(
            String partNumber
    );


    boolean existsByPartNumber(
            String partNumber
    );
}