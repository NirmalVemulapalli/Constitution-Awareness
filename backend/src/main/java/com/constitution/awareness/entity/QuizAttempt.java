package com.constitution.awareness.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table(
        name = "quiz_attempts",
        indexes = {
                @Index(
                        name = "idx_quiz_attempt_user",
                        columnList = "user_id"
                ),
                @Index(
                        name = "idx_quiz_attempt_attempted_at",
                        columnList = "attemptedAt"
                )
        }
)
public class QuizAttempt {


    /*
     * =====================================
     * PRIMARY KEY
     * =====================================
     */

    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    private Long id;


    /*
     * =====================================
     * USER
     * =====================================
     */

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User user;


    /*
     * =====================================
     * QUIZ RESULT
     * =====================================
     */

    @Column(
            nullable = false
    )
    private int score;


    @Column(
            nullable = false
    )
    private int totalQuestions;


    /*
     * =====================================
     * ATTEMPT TIME
     * =====================================
     */

    @Column(
            nullable = false
    )
    private LocalDateTime attemptedAt;


    /*
     * =====================================
     * CONSTRUCTORS
     * =====================================
     */

    public QuizAttempt() {
    }


    public QuizAttempt(

            User user,

            int score,

            int totalQuestions

    ) {

        this.user =
                user;

        this.score =
                score;

        this.totalQuestions =
                totalQuestions;

        this.attemptedAt =
                LocalDateTime.now();
    }


    /*
     * =====================================
     * GETTERS
     * =====================================
     */

    public Long getId() {
        return id;
    }


    public User getUser() {
        return user;
    }


    public int getScore() {
        return score;
    }


    public int getTotalQuestions() {
        return totalQuestions;
    }


    public LocalDateTime getAttemptedAt() {
        return attemptedAt;
    }
}