package com.constitution.awareness.repository;

import com.constitution.awareness.entity.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository
        extends JpaRepository<User, Long> {


    /*
     * =====================================
     * FIND USER BY EMAIL
     * =====================================
     */

    Optional<User> findByEmail(
            String email
    );


    /*
     * =====================================
     * CHECK EMAIL EXISTS
     * =====================================
     */

    boolean existsByEmail(
            String email
    );


    /*
     * =====================================
     * SEARCH USERS
     *
     * Searches by:
     * - Name
     * - Email
     *
     * Supports pagination.
     * =====================================
     */

    Page<User>
    findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(

            String name,

            String email,

            Pageable pageable

    );
}
