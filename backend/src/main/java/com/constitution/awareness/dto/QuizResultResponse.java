package com.constitution.awareness.dto;


public class QuizResultResponse {


    private int score;

    private int totalQuestions;

    private double percentage;


    public QuizResultResponse(

            int score,

            int totalQuestions

    ) {

        this.score =
                score;

        this.totalQuestions =
                totalQuestions;


        if (totalQuestions == 0) {

            this.percentage = 0;

        } else {

            this.percentage =

                    (score * 100.0)

                            /

                    totalQuestions;
        }
    }


    public int getScore() {

        return score;
    }


    public int getTotalQuestions() {

        return totalQuestions;
    }


    public double getPercentage() {

        return percentage;
    }
}