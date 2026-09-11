package com.constitution.awareness.dto;


public class AiChatResponse {

    private String answer;

    private String relatedArticle;

    private Long sessionId;


    public AiChatResponse(
            String answer,
            String relatedArticle,
            Long sessionId
    ) {

        this.answer =
                answer;

        this.relatedArticle =
                relatedArticle;

        this.sessionId =
                sessionId;
    }


    public String getAnswer() {

        return answer;
    }


    public void setAnswer(
            String answer
    ) {

        this.answer =
                answer;
    }


    public String getRelatedArticle() {

        return relatedArticle;
    }


    public void setRelatedArticle(
            String relatedArticle
    ) {

        this.relatedArticle =
                relatedArticle;
    }


    public Long getSessionId() {

        return sessionId;
    }


    public void setSessionId(
            Long sessionId
    ) {

        this.sessionId =
                sessionId;
    }
}