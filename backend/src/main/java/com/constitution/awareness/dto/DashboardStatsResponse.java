package com.constitution.awareness.dto;

public class DashboardStatsResponse {

    private long totalUsers;

    private long totalArticles;

    private long totalQuizQuestions;


    public DashboardStatsResponse(
            long totalUsers,
            long totalArticles,
            long totalQuizQuestions
    ) {

        this.totalUsers = totalUsers;
        this.totalArticles = totalArticles;
        this.totalQuizQuestions =
                totalQuizQuestions;
    }


    public long getTotalUsers() {
        return totalUsers;
    }


    public long getTotalArticles() {
        return totalArticles;
    }


    public long getTotalQuizQuestions() {
        return totalQuizQuestions;
    }
}