package com.constitution.awareness.dto;

import java.time.LocalDateTime;


/*
 * =====================================
 * STANDARD API ERROR RESPONSE
 *
 * Used by GlobalExceptionHandler
 * to return consistent error responses.
 * =====================================
 */

public class ErrorResponse {


    /*
     * =====================================
     * TIMESTAMP
     * =====================================
     */

    private LocalDateTime timestamp;


    /*
     * =====================================
     * HTTP STATUS CODE
     *
     * Example:
     * 404
     * 400
     * 409
     * =====================================
     */

    private int status;


    /*
     * =====================================
     * ERROR TYPE
     *
     * Example:
     * Not Found
     * Bad Request
     * Conflict
     * =====================================
     */

    private String error;


    /*
     * =====================================
     * ERROR MESSAGE
     *
     * Example:
     * Article not found
     * =====================================
     */

    private String message;


    /*
     * =====================================
     * REQUEST PATH
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

        this.timestamp = timestamp;

        this.status = status;

        this.error = error;

        this.message = message;

        this.path = path;
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

        this.timestamp = timestamp;
    }


    public int getStatus() {
        return status;
    }


    public void setStatus(
            int status
    ) {

        this.status = status;
    }


    public String getError() {
        return error;
    }


    public void setError(
            String error
    ) {

        this.error = error;
    }


    public String getMessage() {
        return message;
    }


    public void setMessage(
            String message
    ) {

        this.message = message;
    }


    public String getPath() {
        return path;
    }


    public void setPath(
            String path
    ) {

        this.path = path;
    }
}