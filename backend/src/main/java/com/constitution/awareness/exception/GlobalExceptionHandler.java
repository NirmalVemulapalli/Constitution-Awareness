package com.constitution.awareness.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.AccessDeniedException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


/*
 * =====================================
 * GLOBAL EXCEPTION HANDLER
 *
 * Handles exceptions across the
 * entire application and returns
 * consistent API error responses.
 * =====================================
 */

@RestControllerAdvice
public class GlobalExceptionHandler {


    /*
     * =====================================
     * RESOURCE NOT FOUND
     *
     * Examples:
     * Article not found
     * User not found
     * Category not found
     * =====================================
     */

    @ExceptionHandler(
            ResourceNotFoundException.class
    )
    public ResponseEntity<ErrorResponse> handleResourceNotFound(

            ResourceNotFoundException exception,

            HttpServletRequest request

    ) {

        ErrorResponse errorResponse =
                new ErrorResponse(

                        LocalDateTime.now(),

                        HttpStatus.NOT_FOUND.value(),

                        HttpStatus.NOT_FOUND.getReasonPhrase(),

                        exception.getMessage(),

                        request.getRequestURI()

                );


        return ResponseEntity

                .status(
                        HttpStatus.NOT_FOUND
                )

                .body(
                        errorResponse
                );
    }


    /*
     * =====================================
     * DUPLICATE RESOURCE
     *
     * Examples:
     * Article number already exists
     * Email already registered
     * =====================================
     */

    @ExceptionHandler(
            DuplicateResourceException.class
    )
    public ResponseEntity<ErrorResponse> handleDuplicateResource(

            DuplicateResourceException exception,

            HttpServletRequest request

    ) {

        ErrorResponse errorResponse =
                new ErrorResponse(

                        LocalDateTime.now(),

                        HttpStatus.CONFLICT.value(),

                        HttpStatus.CONFLICT.getReasonPhrase(),

                        exception.getMessage(),

                        request.getRequestURI()

                );


        return ResponseEntity

                .status(
                        HttpStatus.CONFLICT
                )

                .body(
                        errorResponse
                );
    }


    /*
     * =====================================
     * ACCESS DENIED
     *
     * Examples:
     * User tries to edit another
     * educator's article.
     * =====================================
     */

    @ExceptionHandler(
            AccessDeniedException.class
    )
    public ResponseEntity<ErrorResponse> handleAccessDenied(

            AccessDeniedException exception,

            HttpServletRequest request

    ) {

        ErrorResponse errorResponse =
                new ErrorResponse(

                        LocalDateTime.now(),

                        HttpStatus.FORBIDDEN.value(),

                        HttpStatus.FORBIDDEN.getReasonPhrase(),

                        exception.getMessage(),

                        request.getRequestURI()

                );


        return ResponseEntity

                .status(
                        HttpStatus.FORBIDDEN
                )

                .body(
                        errorResponse
                );
    }


    /*
     * =====================================
     * ILLEGAL ARGUMENT
     *
     * Used when invalid values
     * are passed to the API.
     * =====================================
     */

    @ExceptionHandler(
            IllegalArgumentException.class
    )
    public ResponseEntity<ErrorResponse> handleIllegalArgument(

            IllegalArgumentException exception,

            HttpServletRequest request

    ) {

        ErrorResponse errorResponse =
                new ErrorResponse(

                        LocalDateTime.now(),

                        HttpStatus.BAD_REQUEST.value(),

                        HttpStatus.BAD_REQUEST.getReasonPhrase(),

                        exception.getMessage(),

                        request.getRequestURI()

                );


        return ResponseEntity

                .status(
                        HttpStatus.BAD_REQUEST
                )

                .body(
                        errorResponse
                );
    }


    /*
     * =====================================
     * GENERIC EXCEPTION
     *
     * Fallback for unexpected errors.
     *
     * This prevents Spring from returning
     * inconsistent error responses.
     * =====================================
     */

    @ExceptionHandler(
            Exception.class
    )
    public ResponseEntity<ErrorResponse> handleGenericException(

            Exception exception,

            HttpServletRequest request

    ) {

        exception.printStackTrace();


        ErrorResponse errorResponse =
                new ErrorResponse(

                        LocalDateTime.now(),

                        HttpStatus.INTERNAL_SERVER_ERROR.value(),

                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),

                        "Something went wrong. Please try again later.",

                        request.getRequestURI()

                );


        return ResponseEntity

                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR
                )

                .body(
                        errorResponse
                );
    }
}