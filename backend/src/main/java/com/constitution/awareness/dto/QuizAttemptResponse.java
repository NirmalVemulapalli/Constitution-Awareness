package com.constitution.awareness.dto;


import java.time.LocalDateTime;


public class QuizAttemptResponse {


    private Long id;

    private Long userId;

    private String userName;

    private String userEmail;

    private int score;

    private int totalQuestions;

    private double percentage;

    private LocalDateTime attemptedAt;


    public Long getId() {

        return id;
    }


    public void setId(
            Long id
    ) {

        this.id =
                id;
    }


    public Long getUserId() {

        return userId;
    }


    public void setUserId(
            Long userId
    ) {

        this.userId =
                userId;
    }


    public String getUserName() {

        return userName;
    }


    public void setUserName(
            String userName
    ) {

        this.userName =
                userName;
    }


    public String getUserEmail() {

        return userEmail;
    }


    public void setUserEmail(
            String userEmail
    ) {

        this.userEmail =
                userEmail;
    }


    public int getScore() {

        return score;
    }


    public void setScore(
            int score
    ) {

        this.score =
                score;
    }


    public int getTotalQuestions() {

        return totalQuestions;
    }


    public void setTotalQuestions(
            int totalQuestions
    ) {

        this.totalQuestions =
                totalQuestions;
    }


    public double getPercentage() {

        return percentage;
    }


    public void setPercentage(
            double percentage
    ) {

        this.percentage =
                percentage;
    }


    public LocalDateTime getAttemptedAt() {

        return attemptedAt;
    }


    public void setAttemptedAt(
            LocalDateTime attemptedAt
    ) {

        this.attemptedAt =
                attemptedAt;
    }
}