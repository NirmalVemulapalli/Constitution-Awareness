package com.constitution.awareness.exception;


/*
 * =====================================
 * DUPLICATE RESOURCE EXCEPTION
 *
 * Used when attempting to create
 * a resource that already exists.
 *
 * Examples:
 * - Article number already exists
 * - Email already registered
 * - Duplicate category
 * =====================================
 */

public class DuplicateResourceException
        extends RuntimeException {


    public DuplicateResourceException(
            String message
    ) {

        super(
                message
        );
    }
}