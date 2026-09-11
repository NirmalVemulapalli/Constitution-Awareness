package com.constitution.awareness.service;

import com.constitution.awareness.dto.DashboardStatsResponse;

import com.constitution.awareness.repository.UserRepository;
import com.constitution.awareness.repository.ArticleRepository;
import com.constitution.awareness.repository.QuizQuestionRepository;

import org.springframework.stereotype.Service;


@Service
public class DashboardService {

    private final UserRepository userRepository;

    private final ArticleRepository articleRepository;

    private final QuizQuestionRepository
            quizQuestionRepository;


    public DashboardService(

            UserRepository userRepository,

            ArticleRepository articleRepository,

            QuizQuestionRepository
                    quizQuestionRepository

    ) {

        this.userRepository =
                userRepository;

        this.articleRepository =
                articleRepository;

        this.quizQuestionRepository =
                quizQuestionRepository;
    }


    public DashboardStatsResponse
    getStats() {

        long totalUsers =
                userRepository.count();

        long totalArticles =
                articleRepository.count();

        long totalQuizQuestions =
                quizQuestionRepository.count();


        return new DashboardStatsResponse(

                totalUsers,

                totalArticles,

                totalQuizQuestions
        );
    }
}