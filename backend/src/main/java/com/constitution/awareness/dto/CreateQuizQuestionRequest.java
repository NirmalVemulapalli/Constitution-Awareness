package com.constitution.awareness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;


public class CreateQuizQuestionRequest {


    /*
     * =====================================
     * QUESTION
     * =====================================
     */

    @NotBlank(
            message = "Question is required"
    )
    @Size(
            max = 1000,
            message = "Question must not exceed 1000 characters"
    )
    private String question;


    /*
     * =====================================
     * OPTIONS
     * =====================================
     */

    @NotBlank(
            message = "Option A is required"
    )
    @Size(
            max = 1000,
            message = "Option A must not exceed 1000 characters"
    )
    private String optionA;


    @NotBlank(
            message = "Option B is required"
    )
    @Size(
            max = 1000,
            message = "Option B must not exceed 1000 characters"
    )
    private String optionB;


    @NotBlank(
            message = "Option C is required"
    )
    @Size(
            max = 1000,
            message = "Option C must not exceed 1000 characters"
    )
    private String optionC;


    @NotBlank(
            message = "Option D is required"
    )
    @Size(
            max = 1000,
            message = "Option D must not exceed 1000 characters"
    )
    private String optionD;


    /*
     * =====================================
     * CORRECT ANSWER
     *
     * Must be:
     * A, B, C, or D
     * =====================================
     */

    @NotBlank(
            message = "Correct answer is required"
    )
    @Pattern(
            regexp = "^[AaBbCcDd]$",
            message = "Correct answer must be A, B, C, or D"
    )
    private String correctAnswer;


    /*
     * =====================================
     * ARTICLE
     * =====================================
     */

    @NotNull(
            message = "Article ID is required"
    )
    @Positive(
            message = "Article ID must be positive"
    )
    private Long articleId;


    /*
     * =====================================
     * GETTERS AND SETTERS
     * =====================================
     */

    public String getQuestion() {

        return question;
    }


    public void setQuestion(

            String question

    ) {

        this.question =
                question;
    }


    public String getOptionA() {

        return optionA;
    }


    public void setOptionA(

            String optionA

    ) {

        this.optionA =
                optionA;
    }


    public String getOptionB() {

        return optionB;
    }


    public void setOptionB(

            String optionB

    ) {

        this.optionB =
                optionB;
    }


    public String getOptionC() {

        return optionC;
    }


    public void setOptionC(

            String optionC

    ) {

        this.optionC =
                optionC;
    }


    public String getOptionD() {

        return optionD;
    }


    public void setOptionD(

            String optionD

    ) {

        this.optionD =
                optionD;
    }


    public String getCorrectAnswer() {

        return correctAnswer;
    }


    public void setCorrectAnswer(

            String correctAnswer

    ) {

        this.correctAnswer =
                correctAnswer;
    }


    public Long getArticleId() {

        return articleId;
    }


    public void setArticleId(

            Long articleId

    ) {

        this.articleId =
                articleId;
    }
}

