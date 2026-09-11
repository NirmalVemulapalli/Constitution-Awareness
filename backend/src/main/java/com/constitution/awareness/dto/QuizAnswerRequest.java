package com.constitution.awareness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;


public class QuizAnswerRequest {


    /*
     * =====================================
     * QUESTION ID
     * =====================================
     */

    @NotNull(
            message = "Question ID is required"
    )
    @Positive(
            message = "Question ID must be positive"
    )
    private Long questionId;


    /*
     * =====================================
     * SELECTED ANSWER
     *
     * Must be:
     * A, B, C, or D
     * =====================================
     */

    @NotBlank(
            message = "Selected answer is required"
    )
    @Pattern(
            regexp = "^[AaBbCcDd]$",
            message = "Selected answer must be A, B, C, or D"
    )
    private String selectedAnswer;


    /*
     * =====================================
     * GETTERS AND SETTERS
     * =====================================
     */

    public Long getQuestionId() {

        return questionId;
    }


    public void setQuestionId(

            Long questionId

    ) {

        this.questionId =
                questionId;
    }


    public String getSelectedAnswer() {

        return selectedAnswer;
    }


    public void setSelectedAnswer(

            String selectedAnswer

    ) {

        this.selectedAnswer =
                selectedAnswer;
    }
}
