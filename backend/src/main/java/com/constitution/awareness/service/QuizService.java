package com.constitution.awareness.service;

import com.constitution.awareness.dto.*;

import com.constitution.awareness.entity.Article;
import com.constitution.awareness.entity.QuizAttempt;
import com.constitution.awareness.entity.QuizQuestion;
import com.constitution.awareness.entity.Role;
import com.constitution.awareness.entity.User;

import com.constitution.awareness.exception.ResourceNotFoundException;

import com.constitution.awareness.repository.ArticleRepository;
import com.constitution.awareness.repository.QuizAttemptRepository;
import com.constitution.awareness.repository.QuizQuestionRepository;
import com.constitution.awareness.repository.UserRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class QuizService {


    /*
     * =====================================
     * DEPENDENCIES
     * =====================================
     */

    private final QuizQuestionRepository
            quizQuestionRepository;

    private final QuizAttemptRepository
            quizAttemptRepository;

    private final ArticleRepository
            articleRepository;

    private final UserRepository
            userRepository;


    /*
     * =====================================
     * CONSTRUCTOR
     * =====================================
     */

    public QuizService(

            QuizQuestionRepository
                    quizQuestionRepository,

            QuizAttemptRepository
                    quizAttemptRepository,

            ArticleRepository
                    articleRepository,

            UserRepository
                    userRepository

    ) {

        this.quizQuestionRepository =
                quizQuestionRepository;

        this.quizAttemptRepository =
                quizAttemptRepository;

        this.articleRepository =
                articleRepository;

        this.userRepository =
                userRepository;
    }


    /*
     * =====================================
     * GET ACTIVE QUIZ QUESTIONS
     *
     * PUBLIC
     * =====================================
     */

    public List<QuizQuestionResponse>
    getActiveQuestions() {

        return quizQuestionRepository

                .findByActiveTrue()

                .stream()

                .map(
                        this::mapToResponse
                )

                .collect(
                        Collectors.toList()
                );
    }


    /*
     * =====================================
     * GET MANAGEABLE QUIZ QUESTIONS
     *
     * ADMIN:
     * Can see all questions.
     *
     * EDUCATOR:
     * Can see only questions created by them.
     * =====================================
     */

    public Page<QuizQuestionManagementResponse>
    getManageableQuestions(

            int page,

            int size

    ) {

        User currentUser =
                getCurrentUser();


        Pageable pageable =

                PageRequest.of(
                        page,
                        size
                );


        Page<QuizQuestion> questions;


        /*
         * ADMIN
         *
         * Can see all quiz questions.
         */

        if (

                currentUser.getRole()
                        == Role.ADMIN

        ) {

            questions =

                    quizQuestionRepository.findAll(
                            pageable
                    );

        }


        /*
         * EDUCATOR
         *
         * Can see only own questions.
         */

        else if (

                currentUser.getRole()
                        == Role.EDUCATOR

        ) {

            questions =

                    quizQuestionRepository
                            .findByCreatedById(

                                    currentUser.getId(),

                                    pageable

                            );

        }


        else {

            throw new AccessDeniedException(

                    "You are not allowed to manage quiz questions"

            );
        }


        return questions.map(

                this::mapToManagementResponse

        );
    }


    /*
     * =====================================
     * CREATE QUIZ QUESTION
     *
     * ADMIN:
     * Can create for any article.
     *
     * EDUCATOR:
     * Can create only for own article.
     * =====================================
     */

    public QuizQuestion
    createQuestion(

            CreateQuizQuestionRequest request

    ) {

        User currentUser =
                getCurrentUser();


        Article article =

                articleRepository

                        .findById(
                                request.getArticleId()
                        )

                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Article not found"
                                        )
                        );


        /*
         * Validate ownership
         */

        validateArticleOwnership(

                article,

                currentUser

        );


        QuizQuestion question =
                new QuizQuestion();


        question.setQuestion(
                request.getQuestion()
        );


        question.setOptionA(
                request.getOptionA()
        );


        question.setOptionB(
                request.getOptionB()
        );


        question.setOptionC(
                request.getOptionC()
        );


        question.setOptionD(
                request.getOptionD()
        );


        question.setCorrectAnswer(

                request.getCorrectAnswer()
                        .toUpperCase()

        );


        question.setArticle(
                article
        );


        /*
         * IMPORTANT
         *
         * Set question creator.
         */

        question.setCreatedBy(
                currentUser
        );


        question.setActive(
                true
        );


        return quizQuestionRepository.save(
                question
        );
    }


    /*
     * =====================================
     * UPDATE QUIZ QUESTION
     *
     * ADMIN:
     * Can update any question.
     *
     * EDUCATOR:
     * Can update only own questions.
     * =====================================
     */

    public QuizQuestion
    updateQuestion(

            Long questionId,

            CreateQuizQuestionRequest request

    ) {

        User currentUser =
                getCurrentUser();


        QuizQuestion question =

                quizQuestionRepository

                        .findById(
                                questionId
                        )

                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Quiz question not found"
                                        )
                        );


        /*
         * ADMIN
         * Can update any question.
         */

        if (

                currentUser.getRole()
                        != Role.ADMIN

        ) {


            /*
             * EDUCATOR
             * Can update only own question.
             */

            if (

                    question.getCreatedBy() == null

                            ||

                    !question

                            .getCreatedBy()

                            .getId()

                            .equals(
                                    currentUser.getId()
                            )

            ) {

                throw new AccessDeniedException(

                        "You can only update your own quiz questions"

                );
            }
        }


        /*
         * Find article
         */

        Article article =

                articleRepository

                        .findById(
                                request.getArticleId()
                        )

                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Article not found"
                                        )
                        );


        /*
         * Validate article ownership
         */

        validateArticleOwnership(

                article,

                currentUser

        );


        question.setQuestion(
                request.getQuestion()
        );


        question.setOptionA(
                request.getOptionA()
        );


        question.setOptionB(
                request.getOptionB()
        );


        question.setOptionC(
                request.getOptionC()
        );


        question.setOptionD(
                request.getOptionD()
        );


        question.setCorrectAnswer(

                request.getCorrectAnswer()
                        .toUpperCase()

        );


        question.setArticle(
                article
        );


        return quizQuestionRepository.save(
                question
        );
    }


    /*
     * =====================================
     * DELETE QUIZ QUESTION
     *
     * ADMIN:
     * Can delete any question.
     *
     * EDUCATOR:
     * Can delete only own question.
     * =====================================
     */

    public void deleteQuestion(

            Long questionId

    ) {

        User currentUser =
                getCurrentUser();


        QuizQuestion question =

                quizQuestionRepository

                        .findById(
                                questionId
                        )

                        .orElseThrow(
                                () ->
                                        new ResourceNotFoundException(
                                                "Quiz question not found"
                                        )
                        );


        /*
         * ADMIN
         */

        if (

                currentUser.getRole()
                        == Role.ADMIN

        ) {

            quizQuestionRepository.delete(
                    question
            );

            return;
        }


        /*
         * EDUCATOR
         */

        if (

                currentUser.getRole()
                        == Role.EDUCATOR

        ) {

            if (

                    question.getCreatedBy() != null

                            &&

                    question

                            .getCreatedBy()

                            .getId()

                            .equals(
                                    currentUser.getId()
                            )

            ) {

                quizQuestionRepository.delete(
                        question
                );

                return;
            }
        }


        throw new AccessDeniedException(

                "You are not allowed to delete this quiz question"

        );
    }


    /*
     * =====================================
     * GET QUIZ ATTEMPTS
     *
     * ADMIN:
     * Can see all attempts.
     *
     * EDUCATOR:
     * Can see attempts for now.
     *
     * NOTE:
     * Since QuizAttempt currently does not
     * store Question/Article relationship,
     * filtering attempts by educator article
     * is not technically possible yet.
     *
     * Therefore both ADMIN and EDUCATOR
     * can view attempts.
     * =====================================
     */

    public Page<QuizAttemptManagementResponse>
    getQuizAttempts(

            int page,

            int size

    ) {

        User currentUser =
                getCurrentUser();


        if (

                currentUser.getRole()
                        != Role.ADMIN

                        &&

                currentUser.getRole()
                        != Role.EDUCATOR

        ) {

            throw new AccessDeniedException(

                    "You are not allowed to view quiz attempts"

            );
        }


        Pageable pageable =

                PageRequest.of(

                        page,

                        size

                );


        Page<QuizAttempt> attempts =

                quizAttemptRepository

                        .findAllByOrderByAttemptedAtDesc(
                                pageable
                        );


        return attempts.map(

                this::mapToAttemptManagementResponse

        );
    }


    /*
     * =====================================
     * SUBMIT QUIZ
     * =====================================
     */

    public QuizResultResponse
    submitQuiz(

            QuizSubmissionRequest request,

            Authentication authentication

    ) {

        User user =
                getUserFromAuthentication(
                        authentication
                );


        int score = 0;


        int totalQuestions =
                request

                        .getAnswers()

                        .size();


        for (

                QuizAnswerRequest answer

                        : request.getAnswers()

        ) {


            QuizQuestion question =

                    quizQuestionRepository

                            .findById(
                                    answer.getQuestionId()
                            )

                            .orElseThrow(
                                    () ->
                                            new ResourceNotFoundException(
                                                    "Question not found"
                                            )
                            );


            /*
             * Prevent inactive questions
             * from being submitted.
             */

            if (!question.isActive()) {

                throw new ResourceNotFoundException(
                        "Question not found"
                );
            }


            if (

                    question

                            .getCorrectAnswer()

                            .equalsIgnoreCase(

                                    answer
                                            .getSelectedAnswer()

                            )

            ) {

                score++;
            }
        }


        QuizAttempt attempt =

                new QuizAttempt(

                        user,

                        score,

                        totalQuestions

                );


        quizAttemptRepository.save(
                attempt
        );


        return new QuizResultResponse(

                score,

                totalQuestions

        );
    }


    /*
     * =====================================
     * GET LEARNING PROGRESS
     * =====================================
     */

    public LearningProgressResponse
    getLearningProgress(

            Authentication authentication

    ) {

        User user =
                getUserFromAuthentication(
                        authentication
                );


        List<QuizAttempt> attempts =

                quizAttemptRepository

                        .findByUserIdOrderByAttemptedAtDesc(

                                user.getId()

                        );


        int totalAttempts =
                attempts.size();


        int totalQuestionsAttempted =

                attempts

                        .stream()

                        .mapToInt(
                                QuizAttempt::getTotalQuestions
                        )

                        .sum();


        int totalCorrectAnswers =

                attempts

                        .stream()

                        .mapToInt(
                                QuizAttempt::getScore
                        )

                        .sum();


        double averageScore = 0;


        if (totalQuestionsAttempted > 0) {

            averageScore =

                    (

                            totalCorrectAnswers
                                    * 100.0

                    )

                            /

                    totalQuestionsAttempted;
        }


        return new LearningProgressResponse(

                totalAttempts,

                totalQuestionsAttempted,

                totalCorrectAnswers,

                averageScore

        );
    }


    /*
     * =====================================
     * GET CURRENT USER
     * =====================================
     */

    private User getCurrentUser() {

        Authentication authentication =

                SecurityContextHolder

                        .getContext()

                        .getAuthentication();


        if (

                authentication == null

                        ||

                !authentication.isAuthenticated()

                        ||

                "anonymousUser".equals(
                        authentication.getPrincipal()
                )

        ) {

            throw new AccessDeniedException(
                    "User is not authenticated"
            );
        }


        return getUserFromAuthentication(
                authentication
        );
    }


    /*
     * =====================================
     * GET USER FROM AUTHENTICATION
     * =====================================
     */

    private User getUserFromAuthentication(

            Authentication authentication

    ) {

        if (

                authentication == null

                        ||

                !authentication.isAuthenticated()

        ) {

            throw new AccessDeniedException(
                    "User is not authenticated"
            );
        }


        String email =
                authentication.getName();


        return userRepository

                .findByEmail(
                        email
                )

                .orElseThrow(
                        () ->
                                new ResourceNotFoundException(
                                        "User not found"
                                )
                );
    }


    /*
     * =====================================
     * VALIDATE ARTICLE OWNERSHIP
     * =====================================
     */

    private void validateArticleOwnership(

            Article article,

            User currentUser

    ) {


        /*
         * ADMIN
         */

        if (

                currentUser.getRole()
                        == Role.ADMIN

        ) {

            return;
        }


        /*
         * Only EDUCATOR
         */

        if (

                currentUser.getRole()
                        != Role.EDUCATOR

        ) {

            throw new AccessDeniedException(

                    "You are not allowed to manage quiz questions"

            );
        }


        /*
         * EDUCATOR
         *
         * Can manage only own articles.
         */

        if (

                article.getCreatedBy() == null

                        ||

                !article

                        .getCreatedBy()

                        .getId()

                        .equals(
                                currentUser.getId()
                        )

        ) {

            throw new AccessDeniedException(

                    "You can only create quiz questions for articles created by you."

            );
        }
    }


    /*
     * =====================================
     * MAP PUBLIC RESPONSE
     * =====================================
     */

    private QuizQuestionResponse
    mapToResponse(

            QuizQuestion question

    ) {

        return new QuizQuestionResponse(

                question.getId(),

                question.getQuestion(),

                question.getOptionA(),

                question.getOptionB(),

                question.getOptionC(),

                question.getOptionD()

        );
    }


    /*
     * =====================================
     * MAP MANAGEMENT RESPONSE
     * =====================================
     */

    private QuizQuestionManagementResponse
    mapToManagementResponse(

            QuizQuestion question

    ) {

        QuizQuestionManagementResponse response =

                new QuizQuestionManagementResponse();


        response.setId(
                question.getId()
        );


        response.setQuestion(
                question.getQuestion()
        );


        response.setOptionA(
                question.getOptionA()
        );


        response.setOptionB(
                question.getOptionB()
        );


        response.setOptionC(
                question.getOptionC()
        );


        response.setOptionD(
                question.getOptionD()
        );


        response.setCorrectAnswer(
                question.getCorrectAnswer()
        );


        response.setActive(
                question.isActive()
        );


        /*
         * ARTICLE
         */

        if (

                question.getArticle()
                        != null

        ) {

            response.setArticleId(

                    question

                            .getArticle()

                            .getId()

            );


            response.setArticleTitle(

                    question

                            .getArticle()

                            .getTitle()

            );
        }


        /*
         * CREATOR
         */

        if (

                question.getCreatedBy()
                        != null

        ) {

            response.setCreatedById(

                    question

                            .getCreatedBy()

                            .getId()

            );


            response.setCreatedByName(

                    question

                            .getCreatedBy()

                            .getName()

            );


            response.setCreatedByRole(

                    question

                            .getCreatedBy()

                            .getRole()

                            .name()

            );
        }


        return response;
    }


    /*
     * =====================================
     * MAP ATTEMPT RESPONSE
     * =====================================
     */

    private QuizAttemptManagementResponse
    mapToAttemptManagementResponse(

            QuizAttempt attempt

    ) {

        QuizAttemptManagementResponse response =

                new QuizAttemptManagementResponse();


        response.setId(
                attempt.getId()
        );


        response.setScore(
                attempt.getScore()
        );


        response.setTotalQuestions(
                attempt.getTotalQuestions()
        );


        response.setAttemptedAt(
                attempt.getAttemptedAt()
        );


        /*
         * Percentage
         */

        if (

                attempt.getTotalQuestions()
                        > 0

        ) {

            response.setPercentage(

                    attempt.getScore()

                            * 100.0

                            /

                    attempt.getTotalQuestions()

            );

        } else {

            response.setPercentage(
                    0
            );
        }


        /*
         * USER
         */

        if (

                attempt.getUser()
                        != null

        ) {

            response.setUserId(

                    attempt

                            .getUser()

                            .getId()

            );


            response.setUserName(

                    attempt

                            .getUser()

                            .getName()

            );


            response.setUserEmail(

                    attempt

                            .getUser()

                            .getEmail()

            );
        }


        return response;
    }
}