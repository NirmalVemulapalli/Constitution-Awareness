package com.constitution.awareness.controller;

import com.constitution.awareness.dto.*;

import com.constitution.awareness.entity.QuizQuestion;

import com.constitution.awareness.service.QuizService;

import jakarta.validation.Valid;

import org.springframework.data.domain.Page;

import org.springframework.http.ResponseEntity;

import org.springframework.security.core.Authentication;

import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/quiz")
public class QuizController {


    /*
     * =====================================
     * DEPENDENCY
     * =====================================
     */

    private final QuizService quizService;


    /*
     * =====================================
     * CONSTRUCTOR
     * =====================================
     */

    public QuizController(

            QuizService quizService

    ) {

        this.quizService =
                quizService;
    }


    /*
     * =====================================
     * GET ACTIVE QUIZ QUESTIONS
     *
     * PUBLIC
     * =====================================
     */

    @GetMapping("/questions")
    public ResponseEntity<
            List<QuizQuestionResponse>
            > getQuestions() {

        return ResponseEntity.ok(

                quizService.getActiveQuestions()

        );
    }


    /*
     * =====================================
     * GET MANAGEABLE QUIZ QUESTIONS
     *
     * ADMIN + EDUCATOR
     * =====================================
     */

    @GetMapping("/manage/questions")
    public ResponseEntity<
            Page<QuizQuestionManagementResponse>
            > getManageableQuestions(

            @RequestParam(
                    defaultValue = "0"
            )
            int page,

            @RequestParam(
                    defaultValue = "10"
            )
            int size

    ) {

        return ResponseEntity.ok(

                quizService.getManageableQuestions(

                        page,

                        size

                )

        );
    }


    /*
     * =====================================
     * CREATE QUIZ QUESTION
     *
     * ADMIN + EDUCATOR
     * =====================================
     */

    @PostMapping("/questions")
    public ResponseEntity<QuizQuestion>
    createQuestion(

            @Valid
            @RequestBody
            CreateQuizQuestionRequest request

    ) {

        return ResponseEntity.ok(

                quizService.createQuestion(
                        request
                )

        );
    }


    /*
     * =====================================
     * UPDATE QUIZ QUESTION
     *
     * ADMIN + EDUCATOR
     * =====================================
     */

    @PutMapping("/questions/{id}")
    public ResponseEntity<QuizQuestion>
    updateQuestion(

            @PathVariable
            Long id,

            @Valid
            @RequestBody
            CreateQuizQuestionRequest request

    ) {

        return ResponseEntity.ok(

                quizService.updateQuestion(

                        id,

                        request

                )

        );
    }


    /*
     * =====================================
     * DELETE QUIZ QUESTION
     *
     * ADMIN + EDUCATOR
     * =====================================
     */

    @DeleteMapping("/questions/{id}")
    public ResponseEntity<Void>
    deleteQuestion(

            @PathVariable
            Long id

    ) {

        quizService.deleteQuestion(
                id
        );


        return ResponseEntity.noContent()
                .build();
    }


    /*
     * =====================================
     * GET QUIZ ATTEMPTS
     *
     * ADMIN + EDUCATOR
     * =====================================
     */

    @GetMapping("/manage/attempts")
    public ResponseEntity<
            Page<QuizAttemptManagementResponse>
            > getQuizAttempts(

            @RequestParam(
                    defaultValue = "0"
            )
            int page,

            @RequestParam(
                    defaultValue = "10"
            )
            int size

    ) {

        return ResponseEntity.ok(

                quizService.getQuizAttempts(

                        page,

                        size

                )

        );
    }


    /*
     * =====================================
     * SUBMIT QUIZ
     * =====================================
     */

    @PostMapping("/submit")
    public ResponseEntity<QuizResultResponse>
    submitQuiz(

            @Valid
            @RequestBody
            QuizSubmissionRequest request,

            Authentication authentication

    ) {

        return ResponseEntity.ok(

                quizService.submitQuiz(

                        request,

                        authentication

                )

        );
    }


    /*
     * =====================================
     * GET LEARNING PROGRESS
     * =====================================
     */

    @GetMapping("/progress")
    public ResponseEntity<LearningProgressResponse>
    getProgress(

            Authentication authentication

    ) {

        return ResponseEntity.ok(

                quizService.getLearningProgress(
                        authentication
                )

        );
    }
}