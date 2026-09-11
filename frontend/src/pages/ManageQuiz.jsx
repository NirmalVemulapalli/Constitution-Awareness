import {
  useEffect,
  useState,
} from "react";

import {
  getManageableQuizQuestions,
  createQuizQuestion,
  updateQuizQuestion,
  deleteQuizQuestion,
  getQuizAttempts,
} from "../services/quizService";


function ManageQuiz() {


  /*
   * =====================================
   * ACTIVE TAB
   * =====================================
   */

  const [activeTab, setActiveTab] =
    useState("questions");


  /*
   * =====================================
   * QUESTIONS
   * =====================================
   */

  const [questions, setQuestions] =
    useState([]);

  const [questionsLoading, setQuestionsLoading] =
    useState(true);

  const [questionPage, setQuestionPage] =
    useState(0);

  const [questionTotalPages, setQuestionTotalPages] =
    useState(0);


  /*
   * =====================================
   * ATTEMPTS
   * =====================================
   */

  const [attempts, setAttempts] =
    useState([]);

  const [attemptsLoading, setAttemptsLoading] =
    useState(false);

  const [attemptPage, setAttemptPage] =
    useState(0);

  const [attemptTotalPages, setAttemptTotalPages] =
    useState(0);


  /*
   * =====================================
   * FORM
   * =====================================
   */

  const [editingQuestion, setEditingQuestion] =
    useState(null);

  const [articleId, setArticleId] =
    useState("");

  const [question, setQuestion] =
    useState("");

  const [optionA, setOptionA] =
    useState("");

  const [optionB, setOptionB] =
    useState("");

  const [optionC, setOptionC] =
    useState("");

  const [optionD, setOptionD] =
    useState("");

  const [correctAnswer, setCorrectAnswer] =
    useState("A");


  /*
   * =====================================
   * MESSAGES
   * =====================================
   */

  const [error, setError] =
    useState("");

  const [success, setSuccess] =
    useState("");


  /*
   * =====================================
   * LOAD QUESTIONS
   * =====================================
   */

  const loadQuestions = async (
    pageNumber = 0
  ) => {

    try {

      setQuestionsLoading(true);

      setError("");


      const data =
        await getManageableQuizQuestions(
          pageNumber
        );


      console.log(
        "Quiz Questions Response:",
        data
      );


      setQuestions(
        data?.content || []
      );


      setQuestionPage(
        data?.number ?? 0
      );


      setQuestionTotalPages(
        data?.totalPages ?? 0
      );

    } catch (error) {

      console.error(
        "Unable to load quiz questions:",
        error
      );


      const message =

        error?.response?.data?.message ||

        error?.response?.data ||

        "Unable to load quiz questions.";


      setError(
        typeof message === "string"
          ? message
          : "Unable to load quiz questions."
      );

    } finally {

      setQuestionsLoading(false);
    }
  };


  /*
   * =====================================
   * LOAD ATTEMPTS
   * =====================================
   */

  const loadAttempts = async (
    pageNumber = 0
  ) => {

    try {

      setAttemptsLoading(true);

      setError("");


      const data =
        await getQuizAttempts(
          pageNumber
        );


      console.log(
        "Quiz Attempts Response:",
        data
      );


      setAttempts(
        data?.content || []
      );


      setAttemptPage(
        data?.number ?? 0
      );


      setAttemptTotalPages(
        data?.totalPages ?? 0
      );

    } catch (error) {

      console.error(
        "Unable to load quiz attempts:",
        error
      );


      const message =

        error?.response?.data?.message ||

        error?.response?.data ||

        "Unable to load quiz attempts.";


      setError(
        typeof message === "string"
          ? message
          : "Unable to load quiz attempts."
      );

    } finally {

      setAttemptsLoading(false);
    }
  };


  /*
   * =====================================
   * INITIAL LOAD
   * =====================================
   */

  useEffect(() => {

    loadQuestions(0);

  }, []);


  /*
   * =====================================
   * TAB CHANGE
   * =====================================
   */

  const handleTabChange = (
    tab
  ) => {

    setActiveTab(tab);

    setError("");

    setSuccess("");


    if (tab === "questions") {

      loadQuestions(0);

    }


    if (tab === "attempts") {

      loadAttempts(0);
    }
  };


  /*
   * =====================================
   * RESET FORM
   * =====================================
   */

  const resetForm = () => {

    setEditingQuestion(null);

    setArticleId("");

    setQuestion("");

    setOptionA("");

    setOptionB("");

    setOptionC("");

    setOptionD("");

    setCorrectAnswer("A");
  };


  /*
   * =====================================
   * START EDIT
   * =====================================
   */

  const startEdit = (
    quizQuestion
  ) => {

    setError("");

    setSuccess("");

    setEditingQuestion(
      quizQuestion
    );


    setArticleId(
      quizQuestion.articleId || ""
    );


    setQuestion(
      quizQuestion.question || ""
    );


    setOptionA(
      quizQuestion.optionA || ""
    );


    setOptionB(
      quizQuestion.optionB || ""
    );


    setOptionC(
      quizQuestion.optionC || ""
    );


    setOptionD(
      quizQuestion.optionD || ""
    );


    setCorrectAnswer(
      quizQuestion.correctAnswer || "A"
    );


    window.scrollTo({

      top: 0,

      behavior: "smooth",

    });
  };


  /*
   * =====================================
   * SUBMIT QUESTION
   * =====================================
   */

  const handleSubmit = async (
    event
  ) => {

    event.preventDefault();


    try {

      setError("");

      setSuccess("");


      const questionData = {

        articleId:
          Number(articleId),

        question,

        optionA,

        optionB,

        optionC,

        optionD,

        correctAnswer,

      };


      if (editingQuestion) {

        await updateQuizQuestion(

          editingQuestion.id,

          questionData

        );


        setSuccess(
          "Quiz question updated successfully."
        );

      } else {

        await createQuizQuestion(
          questionData
        );


        setSuccess(
          "Quiz question created successfully."
        );
      }


      resetForm();


      await loadQuestions(
        questionPage
      );

    } catch (error) {

      console.error(
        "Unable to save quiz question:",
        error
      );


      const message =

        error?.response?.data?.message ||

        error?.response?.data ||

        "Unable to save quiz question.";


      setError(
        typeof message === "string"
          ? message
          : "Unable to save quiz question."
      );

    }
  };


  /*
   * =====================================
   * DELETE QUESTION
   * =====================================
   */

  const handleDelete = async (
    id
  ) => {

    const confirmed =
      window.confirm(
        "Are you sure you want to delete this quiz question?"
      );


    if (!confirmed) {

      return;
    }


    try {

      setError("");

      setSuccess("");


      await deleteQuizQuestion(
        id
      );


      setSuccess(
        "Quiz question deleted successfully."
      );


      await loadQuestions(
        questionPage
      );

    } catch (error) {

      console.error(
        "Unable to delete quiz question:",
        error
      );


      const message =

        error?.response?.data?.message ||

        error?.response?.data ||

        "Unable to delete quiz question.";


      setError(
        typeof message === "string"
          ? message
          : "Unable to delete quiz question."
      );

    }
  };


  /*
   * =====================================
   * CALCULATE PERCENTAGE
   * =====================================
   */

  const getPercentage = (
    score,
    totalQuestions
  ) => {

    if (!totalQuestions) {

      return 0;
    }


    return (

      (score / totalQuestions) * 100

    ).toFixed(0);
  };


  return (

    <div className="manage-articles-page">


      {/* HEADER */}

      <div className="manage-header">

        <div>

          <span>
            QUIZ MANAGEMENT
          </span>

          <h1>
            Manage Quiz
          </h1>

          <p>
            Create quiz questions and monitor
            citizen quiz performance.
          </p>

        </div>

      </div>


      {/* TABS */}

      <div className="quiz-management-tabs">

        <button
          type="button"
          className={
            activeTab === "questions"
              ? "active"
              : ""
          }
          onClick={() =>
            handleTabChange("questions")
          }
        >

          📝 Quiz Questions

        </button>


        <button
          type="button"
          className={
            activeTab === "attempts"
              ? "active"
              : ""
          }
          onClick={() =>
            handleTabChange("attempts")
          }
        >

          📊 Quiz Attempts

        </button>

      </div>


      {/* MESSAGES */}

      {error && (

        <div className="form-error">

          {error}

        </div>

      )}


      {success && (

        <div className="form-success">

          {success}

        </div>

      )}


      {/* QUESTIONS TAB */}

      {activeTab === "questions" && (

        <>


          {/* CREATE / EDIT FORM */}

          <div className="article-form">

            <h2>

              {editingQuestion
                ? "Edit Quiz Question"
                : "Create Quiz Question"}

            </h2>


            <form
              onSubmit={handleSubmit}
            >


              <div className="form-group">

                <label>
                  Article ID
                </label>

                <input
                  type="number"
                  min="1"
                  value={articleId}
                  placeholder="Enter related article ID"
                  onChange={(event) =>
                    setArticleId(
                      event.target.value
                    )
                  }
                  required
                />

              </div>


              <div className="form-group">

                <label>
                  Question
                </label>

                <textarea
                  rows="4"
                  value={question}
                  placeholder="Enter quiz question"
                  onChange={(event) =>
                    setQuestion(
                      event.target.value
                    )
                  }
                  required
                />

              </div>


              <div className="form-group">

                <label>
                  Option A
                </label>

                <input
                  type="text"
                  value={optionA}
                  onChange={(event) =>
                    setOptionA(
                      event.target.value
                    )
                  }
                  required
                />

              </div>


              <div className="form-group">

                <label>
                  Option B
                </label>

                <input
                  type="text"
                  value={optionB}
                  onChange={(event) =>
                    setOptionB(
                      event.target.value
                    )
                  }
                  required
                />

              </div>


              <div className="form-group">

                <label>
                  Option C
                </label>

                <input
                  type="text"
                  value={optionC}
                  onChange={(event) =>
                    setOptionC(
                      event.target.value
                    )
                  }
                  required
                />

              </div>


              <div className="form-group">

                <label>
                  Option D
                </label>

                <input
                  type="text"
                  value={optionD}
                  onChange={(event) =>
                    setOptionD(
                      event.target.value
                    )
                  }
                  required
                />

              </div>


              <div className="form-group">

                <label>
                  Correct Answer
                </label>

                <select
                  value={correctAnswer}
                  onChange={(event) =>
                    setCorrectAnswer(
                      event.target.value
                    )
                  }
                >

                  <option value="A">
                    Option A
                  </option>

                  <option value="B">
                    Option B
                  </option>

                  <option value="C">
                    Option C
                  </option>

                  <option value="D">
                    Option D
                  </option>

                </select>

              </div>


              <div className="form-actions">


                {editingQuestion && (

                  <button
                    type="button"
                    className="cancel-button"
                    onClick={resetForm}
                  >

                    Cancel

                  </button>

                )}


                <button
                  type="submit"
                  className="save-button"
                >

                  {editingQuestion
                    ? "Update Question"
                    : "Create Question"}

                </button>

              </div>

            </form>

          </div>


          {/* QUESTIONS TABLE */}

          <div className="articles-management-table quiz-questions-table">

            {questionsLoading ? (

              <p className="table-message">
                Loading quiz questions...
              </p>

            ) : (

              <table>

                <thead>

                  <tr>

                    <th>
                      Question
                    </th>

                    <th>
                      Correct Answer
                    </th>

                    <th>
                      Actions
                    </th>

                  </tr>

                </thead>


                <tbody>

                  {questions.length === 0 ? (

                    <tr>

                      <td colSpan="3">

                        No quiz questions found.

                      </td>

                    </tr>

                  ) : (

                    questions.map(
                      (quizQuestion) => (

                        <tr
                          key={quizQuestion.id}
                        >

                          <td>

                            {quizQuestion.question}

                          </td>


                          <td>

                            <span className="correct-answer-badge">

                              Option {
                                quizQuestion.correctAnswer
                              }

                            </span>

                          </td>


                          <td>

                            <div className="article-actions">


                              <button
                                type="button"
                                onClick={() =>
                                  startEdit(
                                    quizQuestion
                                  )
                                }
                              >

                                Edit

                              </button>


                              <button
                                type="button"
                                className="delete-button"
                                onClick={() =>
                                  handleDelete(
                                    quizQuestion.id
                                  )
                                }
                              >

                                Delete

                              </button>


                            </div>

                          </td>

                        </tr>

                      )
                    )

                  )}

                </tbody>

              </table>

            )}

          </div>


          {/* QUESTION PAGINATION */}

          {questionTotalPages > 1 && (

            <div className="management-pagination">

              <button
                type="button"
                disabled={
                  questionPage === 0
                }
                onClick={() =>
                  loadQuestions(
                    questionPage - 1
                  )
                }
              >

                Previous

              </button>


              <span>

                Page {questionPage + 1}
                {" of "}
                {questionTotalPages}

              </span>


              <button
                type="button"
                disabled={
                  questionPage >=
                  questionTotalPages - 1
                }
                onClick={() =>
                  loadQuestions(
                    questionPage + 1
                  )
                }
              >

                Next

              </button>

            </div>

          )}

        </>

      )}


      {/* ATTEMPTS TAB */}

      {activeTab === "attempts" && (

        <div className="articles-management-table quiz-attempts-table">

          {attemptsLoading ? (

            <p className="table-message">
              Loading quiz attempts...
            </p>

          ) : (

            <table>

              <thead>

                <tr>

                  <th>
                    Citizen
                  </th>

                  <th>
                    Email
                  </th>

                  <th>
                    Score
                  </th>

                  <th>
                    Percentage
                  </th>

                  <th>
                    Attempted At
                  </th>

                </tr>

              </thead>


              <tbody>

                {attempts.length === 0 ? (

                  <tr>

                    <td colSpan="5">

                      No quiz attempts found.

                    </td>

                  </tr>

                ) : (

                  attempts.map(
                    (attempt) => (

                      <tr
                        key={attempt.id}
                      >

                        <td>

                          <strong>

                            {attempt.userName}

                          </strong>

                        </td>


                        <td>

                          {attempt.userEmail}

                        </td>


                        <td>

                          {attempt.score}
                          {" / "}
                          {attempt.totalQuestions}

                        </td>


                        <td>

                          <span className="percentage-badge">

                            {getPercentage(
                              attempt.score,
                              attempt.totalQuestions
                            )}%

                          </span>

                        </td>


                        <td>

                          {attempt.attemptedAt
                            ? new Date(
                                attempt.attemptedAt
                              ).toLocaleString()
                            : "-"}

                        </td>

                      </tr>

                    )
                  )

                )}

              </tbody>

            </table>

          )}


          {/* ATTEMPT PAGINATION */}

          {attemptTotalPages > 1 && (

            <div className="management-pagination">

              <button
                type="button"
                disabled={
                  attemptPage === 0
                }
                onClick={() =>
                  loadAttempts(
                    attemptPage - 1
                  )
                }
              >

                Previous

              </button>


              <span>

                Page {attemptPage + 1}
                {" of "}
                {attemptTotalPages}

              </span>


              <button
                type="button"
                disabled={
                  attemptPage >=
                  attemptTotalPages - 1
                }
                onClick={() =>
                  loadAttempts(
                    attemptPage + 1
                  )
                }
              >

                Next

              </button>

            </div>

          )}

        </div>

      )}

    </div>

  );
}


export default ManageQuiz;