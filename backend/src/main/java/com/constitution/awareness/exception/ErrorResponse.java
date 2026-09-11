package com.constitution.awareness.exception;

import java.time.LocalDateTime;


/*
 * =====================================
 * STANDARD API ERROR RESPONSE
 *
 * Used by GlobalExceptionHandler to
 * return consistent error responses.
 *
 * Example:
 *
 * {
 *     "timestamp": "2026-09-07T10:30:00",
 *     "status": 404,
 *     "error": "Not Found",
 *     "message": "Article not found",
 *     "path": "/api/articles/100"
 * }
 * =====================================
 */

public class ErrorResponse {


    /*
     * =====================================
     * ERROR TIMESTAMP
     * =====================================
     */

    private LocalDateTime timestamp;


    /*
     * =====================================
     * HTTP STATUS CODE
     *
     * Examples:
     * 400
     * 401
     * 403
     * 404
     * 409
     * 500
     * =====================================
     */

    private int status;


    /*
     * =====================================
     * HTTP ERROR NAME
     *
     * Examples:
     * Not Found
     * Bad Request
     * Forbidden
     * =====================================
     */

    private String error;


    /*
     * =====================================
     * HUMAN-READABLE MESSAGE
     * =====================================
     */

    private String message;


    /*
     * =====================================
     * API REQUEST PATH
     *
     * Example:
     * /api/articles/10
     * =====================================
     */

    private String path;


    /*
     * =====================================
     * DEFAULT CONSTRUCTOR
     * =====================================
     */

    public ErrorResponse() {
    }


    /*
     * =====================================
     * PARAMETERIZED CONSTRUCTOR
     * =====================================
     */

    public ErrorResponse(

            LocalDateTime timestamp,

            int status,

            String error,

            String message,

            String path

    ) {

        this.timestamp =
                timestamp;

        this.status =
                status;

        this.error =
                error;

        this.message =
                message;

        this.path =
                path;
    }


    /*
     * =====================================
     * GETTERS AND SETTERS
     * =====================================
     */

    public LocalDateTime getTimestamp() {

        return timestamp;
    }


    public void setTimestamp(
            LocalDateTime timestamp
    ) {

        this.timestamp =
                timestamp;
    }


    public int getStatus() {

        return status;
    }


    public void setStatus(
            int status
    ) {

        this.status =
                status;
    }


    public String getError() {

        return error;
    }


    public void setError(
            String error
    ) {

        this.error =
                error;
    }


    public String getMessage() {

        return message;
    }


    public void setMessage(
            String message
    ) {

        this.message =
                message;
    }


    public String getPath() {

        return path;
    }


    public void setPath(
            String path
    ) {

        this.path =
                path;
    }
}