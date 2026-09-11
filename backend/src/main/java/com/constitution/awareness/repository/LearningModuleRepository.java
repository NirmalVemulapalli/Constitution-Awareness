package com.constitution.awareness.repository;

import com.constitution.awareness.entity.LearningModule;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LearningModuleRepository
        extends JpaRepository<LearningModule, Long> {


    /*
     * Get all published modules
     * in learning order.
     */

    List<LearningModule>
    findByPublishedTrueOrderByDisplayOrderAsc();


    /*
     * Admin can view all modules
     * in display order.
     */

    List<LearningModule>
    findAllByOrderByDisplayOrderAsc();
}