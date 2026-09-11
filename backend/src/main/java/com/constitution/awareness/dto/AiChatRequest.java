package com.constitution.awareness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class AiChatRequest {

    @NotBlank(
            message = "Question cannot be empty"
    )
    @Size(
            max = 2000,
            message = "Question must not exceed 2000 characters"
    )
    private String question;


    /*
     * Null means:
     * Create a new conversation.
     *
     * Existing ID means:
     * Continue that conversation.
     */

    @Positive(
            message = "Session ID must be positive"
    )
    private Long sessionId;


    public String getQuestion() {
        return question;
    }


    public void setQuestion(
            String question
    ) {
        this.question = question;
    }


    public Long getSessionId() {
        return sessionId;
    }


    public void setSessionId(
            Long sessionId
    ) {
        this.sessionId = sessionId;
    }
}