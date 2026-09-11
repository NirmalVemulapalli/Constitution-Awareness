package com.constitution.awareness.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


public class UpdateQuizQuestionRequest {


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
            max = 1000
    )
    private String optionA;


    @NotBlank(
            message = "Option B is required"
    )
    @Size(
            max = 1000
    )
    private String optionB;


    @NotBlank(
            message = "Option C is required"
    )
    @Size(
            max = 1000
    )
    private String optionC;


    @NotBlank(
            message = "Option D is required"
    )
    @Size(
            max = 1000
    )
    private String optionD;


    /*
     * =====================================
     * CORRECT ANSWER
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
     * ACTIVE STATUS
     * =====================================
     */

    private boolean active;


    /*
     * =====================================
     * GETTERS
     * =====================================
     */

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


    public boolean isActive() {

        return active;
    }


    /*
     * =====================================
     * SETTERS
     * =====================================
     */

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


    public void setActive(
            boolean active
    ) {

        this.active = active;
    }
}