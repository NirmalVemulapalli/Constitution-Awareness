package com.constitution.awareness.dto;

public class LearningProgressResponse {

    private int totalAttempts;

    private int totalQuestionsAttempted;

    private int totalCorrectAnswers;

    private double averageScore;


    public LearningProgressResponse(
            int totalAttempts,
            int totalQuestionsAttempted,
            int totalCorrectAnswers,
            double averageScore
    ) {

        this.totalAttempts =
                totalAttempts;

        this.totalQuestionsAttempted =
                totalQuestionsAttempted;

        this.totalCorrectAnswers =
                totalCorrectAnswers;

        this.averageScore =
                averageScore;
    }


    public int getTotalAttempts() {
        return totalAttempts;
    }

    public int getTotalQuestionsAttempted() {
        return totalQuestionsAttempted;
    }

    public int getTotalCorrectAnswers() {
        return totalCorrectAnswers;
    }

    public double getAverageScore() {
        return averageScore;
    }
}