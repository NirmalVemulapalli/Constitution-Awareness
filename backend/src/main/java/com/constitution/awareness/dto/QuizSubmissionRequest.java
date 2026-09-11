package com.constitution.awareness.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;


public class QuizSubmissionRequest {


    @NotEmpty(
            message = "At least one answer is required"
    )
    private List<@Valid QuizAnswerRequest> answers;


    public List<QuizAnswerRequest>
    getAnswers() {

        return answers;
    }


    public void setAnswers(

            List<QuizAnswerRequest> answers

    ) {

        this.answers =
                answers;
    }
}