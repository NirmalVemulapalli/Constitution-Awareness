package com.constitution.awareness.repository;

import com.constitution.awareness.entity.LearningModule;
import com.constitution.awareness.entity.Lesson;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface LessonRepository
        extends JpaRepository<Lesson, Long> {


    /*
     * Get published lessons
     * for users.
     */

    List<Lesson>
    findByModuleAndPublishedTrueOrderByDisplayOrderAsc(
            LearningModule module
    );


    /*
     * Get all lessons for admin.
     */

    List<Lesson>
    findByModuleOrderByDisplayOrderAsc(
            LearningModule module
    );


    /*
     * Optional safety method:
     * Find a published lesson.
     */

    Optional<Lesson>
    findByIdAndPublishedTrue(
            Long id
    );


    /*
     * Useful for progress tracking later.
     */

    long countByModuleAndPublishedTrue(
            LearningModule module
    );
}