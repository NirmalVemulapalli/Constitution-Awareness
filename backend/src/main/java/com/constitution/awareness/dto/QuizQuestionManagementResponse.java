package com.constitution.awareness.dto;


public class QuizQuestionManagementResponse {


    /*
     * =====================================
     * QUESTION
     * =====================================
     */

    private Long id;

    private String question;

    private String optionA;

    private String optionB;

    private String optionC;

    private String optionD;

    private String correctAnswer;


    /*
     * =====================================
     * ARTICLE
     * =====================================
     */

    private Long articleId;

    private String articleTitle;


    /*
     * =====================================
     * CREATOR
     * =====================================
     */

    private Long createdById;

    private String createdByName;

    private String createdByRole;


    /*
     * =====================================
     * STATUS
     * =====================================
     */

    private boolean active;


    /*
     * =====================================
     * GETTERS
     * =====================================
     */

    public Long getId() {

        return id;
    }


    public String getQuestion() {

        return question;
    }


    public String getOptionA() {

        return optionA;
    }


    public String getOptionB() {

        return optionB;
    }


    public String getOptionC() {

        return optionC;
    }


    public String getOptionD() {

        return optionD;
    }


    public String getCorrectAnswer() {

        return correctAnswer;
    }


    public Long getArticleId() {

        return articleId;
    }


    public String getArticleTitle() {

        return articleTitle;
    }


    public Long getCreatedById() {

        return createdById;
    }


    public String getCreatedByName() {

        return createdByName;
    }


    public String getCreatedByRole() {

        return createdByRole;
    }


    public boolean isActive() {

        return active;
    }


    /*
     * =====================================
     * SETTERS
     * =====================================
     */

    public void setId(
            Long id
    ) {

        this.id = id;
    }


    public void setQuestion(
            String question
    ) {

        this.question = question;
    }


    public void setOptionA(
            String optionA
    ) {

        this.optionA = optionA;
    }


    public void setOptionB(
            String optionB
    ) {

        this.optionB = optionB;
    }


    public void setOptionC(
            String optionC
    ) {

        this.optionC = optionC;
    }


    public void setOptionD(
            String optionD
    ) {

        this.optionD = optionD;
    }


    public void setCorrectAnswer(
            String correctAnswer
    ) {

        this.correctAnswer = correctAnswer;
    }


    public void setArticleId(
            Long articleId
    ) {

        this.articleId = articleId;
    }


    public void setArticleTitle(
            String articleTitle
    ) {

        this.articleTitle = articleTitle;
    }


    public void setCreatedById(
            Long createdById
    ) {

        this.createdById = createdById;
    }


    public void setCreatedByName(
            String createdByName
    ) {

        this.createdByName = createdByName;
    }


    public void setCreatedByRole(
            String createdByRole
    ) {

        this.createdByRole = createdByRole;
    }


    public void setActive(
            boolean active
    ) {

        this.active = active;
    }
}