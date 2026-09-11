package com.constitution.awareness.entity;

import jakarta.persistence.*;


@Entity
@Table(
        name = "quiz_questions",
        indexes = {
                @Index(
                        name = "idx_quiz_question_article",
                        columnList = "article_id"
                ),
                @Index(
                        name = "idx_quiz_question_created_by",
                        columnList = "created_by"
                ),
                @Index(
                        name = "idx_quiz_question_active",
                        columnList = "active"
                )
        }
)
public class QuizQuestion {


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
     * QUESTION
     * =====================================
     */

    @Column(
            nullable = false,
            columnDefinition = "TEXT"
    )
    private String question;


    /*
     * =====================================
     * OPTIONS
     * =====================================
     */

    @Column(
            nullable = false,
            length = 1000
    )
    private String optionA;


    @Column(
            nullable = false,
            length = 1000
    )
    private String optionB;


    @Column(
            nullable = false,
            length = 1000
    )
    private String optionC;


    @Column(
            nullable = false,
            length = 1000
    )
    private String optionD;


    /*
     * =====================================
     * CORRECT ANSWER
     *
     * Expected:
     * A / B / C / D
     * =====================================
     */

    @Column(
            nullable = false,
            length = 1
    )
    private String correctAnswer;


    /*
     * =====================================
     * RELATED ARTICLE
     * =====================================
     */

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "article_id",
            nullable = false
    )
    private Article article;


    /*
     * =====================================
     * QUESTION CREATOR
     *
     * ADMIN or EDUCATOR
     * =====================================
     */

    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "created_by",
            nullable = false
    )
    private User createdBy;


    /*
     * =====================================
     * STATUS
     * =====================================
     */

    @Column(
            nullable = false
    )
    private boolean active = true;


    /*
     * =====================================
     * CONSTRUCTOR
     * =====================================
     */

    public QuizQuestion() {
    }


    public QuizQuestion(

            String question,

            String optionA,

            String optionB,

            String optionC,

            String optionD,

            String correctAnswer,

            Article article,

            User createdBy

    ) {

        this.question =
                question;

        this.optionA =
                optionA;

        this.optionB =
                optionB;

        this.optionC =
                optionC;

        this.optionD =
                optionD;

        this.correctAnswer =
                correctAnswer;

        this.article =
                article;

        this.createdBy =
                createdBy;

        this.active =
                true;
    }


    /*
     * =====================================
     * GETTERS
     * =====================================
     */

    public Long getId() {
        return id;
    }


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


    public Article getArticle() {
        return article;
    }


    public User getCreatedBy() {
        return createdBy;
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

        this.question =
                question;
    }


    public void setOptionA(
            String optionA
    ) {

        this.optionA =
                optionA;
    }


    public void setOptionB(
            String optionB
    ) {

        this.optionB =
                optionB;
    }


    public void setOptionC(
            String optionC
    ) {

        this.optionC =
                optionC;
    }


    public void setOptionD(
            String optionD
    ) {

        this.optionD =
                optionD;
    }


    public void setCorrectAnswer(
            String correctAnswer
    ) {

        this.correctAnswer =
                correctAnswer;
    }


    public void setArticle(
            Article article
    ) {

        this.article =
                article;
    }


    public void setCreatedBy(
            User createdBy
    ) {

        this.createdBy =
                createdBy;
    }


    public void setActive(
            boolean active
    ) {

        this.active =
                active;
    }
}