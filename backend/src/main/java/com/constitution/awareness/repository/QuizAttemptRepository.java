package com.constitution.awareness.repository;

import com.constitution.awareness.entity.QuizAttempt;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface QuizAttemptRepository
        extends JpaRepository<QuizAttempt, Long> {


    /*
     * =====================================
     * GET USER ATTEMPTS
     * =====================================
     */

    List<QuizAttempt>
    findByUserIdOrderByAttemptedAtDesc(
            Long userId
    );


    /*
     * =====================================
     * GET ALL ATTEMPTS
     *
     * ADMIN
     * =====================================
     */

    Page<QuizAttempt>
    findAllByOrderByAttemptedAtDesc(
            Pageable pageable
    );
}