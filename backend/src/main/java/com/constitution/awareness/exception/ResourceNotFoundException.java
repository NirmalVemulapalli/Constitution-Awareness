package com.constitution.awareness.exception;


/*
 * =====================================
 * RESOURCE NOT FOUND EXCEPTION
 *
 * Used when a requested resource
 * does not exist in the database.
 *
 * Examples:
 * - Article not found
 * - User not found
 * - Category not found
 * - Constitutional Part not found
 * =====================================
 */

public class ResourceNotFoundException
        extends RuntimeException {


    public ResourceNotFoundException(
            String message
    ) {

        super(
                message
        );
    }
}