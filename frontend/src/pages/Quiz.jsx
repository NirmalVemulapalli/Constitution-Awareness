import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
} from "react-router-dom";

import {
  useAuth,
} from "../context/AuthContext";

import Loading from "../components/Loading";

import {
  getQuizQuestions,
  submitQuiz,
} from "../services/quizService";


function Quiz() {


  const {
    user,
  } = useAuth();


  const navigate =
    useNavigate();


  /*
   * =====================================
   * QUIZ STATE
   * =====================================
   */

  const [questions, setQuestions] =
    useState([]);

  const [answers, setAnswers] =
    useState({});

  const [
    currentQuestionIndex,
    setCurrentQuestionIndex,
  ] =
    useState(0);

  const [loading, setLoading] =
    useState(true);

  const [submitting, setSubmitting] =
    useState(false);

  const [result, setResult] =
    useState(null);

  const [error, setError] =
    useState("");


  /*
   * =====================================
   * LOAD QUESTIONS
   * =====================================
   */

  useEffect(() => {

    loadQuestions();

  }, []);


  const loadQuestions = async () => {

    try {

      setLoading(true);

      setError("");

      setCurrentQuestionIndex(0);

      setAnswers({});


      const data =
        await getQuizQuestions();


      setQuestions(

        Array.isArray(data)
          ? data
          : []

      );


    } catch (error) {

      console.error(error);


      setError(
        "Unable to load quiz questions."
      );


    } finally {

      setLoading(false);

    }
  };


  /*
   * =====================================
   * SELECT ANSWER
   * =====================================
   */

  const selectAnswer = (
    questionId,
    answer
  ) => {

    setAnswers(
      (previousAnswers) => ({

        ...previousAnswers,

        [questionId]: answer,

      })
    );
  };


  /*
   * =====================================
   * NEXT QUESTION
   * =====================================
   */

  const handleNext = () => {

    const currentQuestion =
      questions[currentQuestionIndex];


    if (
      !currentQuestion ||
      !answers[currentQuestion.id]
    ) {

      alert(
        "Please select an answer before continuing."
      );

      return;
    }


    setCurrentQuestionIndex(
      (previousIndex) => {

        if (
          previousIndex <
          questions.length - 1
        ) {

          return previousIndex + 1;
        }

        return previousIndex;

      }
    );


    window.scrollTo({

      top: 0,

      behavior: "smooth",

    });
  };


  /*
   * =====================================
   * PREVIOUS QUESTION
   * =====================================
   */

  const handlePrevious = () => {

    setCurrentQuestionIndex(
      (previousIndex) => {

        if (previousIndex > 0) {

          return previousIndex - 1;
        }

        return previousIndex;

      }
    );


    window.scrollTo({

      top: 0,

      behavior: "smooth",

    });
  };


  /*
   * =====================================
   * SUBMIT QUIZ
   * =====================================
   */

  const handleSubmit = async () => {

    if (
      Object.keys(answers).length
      !== questions.length
    ) {

      alert(
        "Please answer all questions before submitting."
      );

      return;
    }


    /*
     * AUTHENTICATION CHECK
     */

    if (!user) {

      alert(
        "Please login to submit the quiz."
      );

      navigate("/login");

      return;
    }


    /*
     * ROLE CHECK
     */

    if (
      user.role !== "CITIZEN"
    ) {

      alert(
        "Only Citizens can submit quizzes."
      );

      return;
    }


    const token =
      localStorage.getItem("token");


    const formattedAnswers =
      Object.entries(answers).map(
        ([
          questionId,
          selectedAnswer,
        ]) => ({

          questionId:
            Number(questionId),

          selectedAnswer,

        })
      );


    try {

      setSubmitting(true);

      setError("");


      const data =
        await submitQuiz(
          formattedAnswers,
          token
        );


      setResult(data);


      window.scrollTo({

        top: 0,

        behavior: "smooth",

      });


    } catch (error) {

      console.error(error);


      setError(

        error?.response?.data?.message ||

        "Unable to submit quiz. Please ensure you are logged in as a Citizen."

      );


    } finally {

      setSubmitting(false);

    }
  };


  /*
   * =====================================
   * RETRY QUIZ
   * =====================================
   */

  const handleTryAgain = () => {

    setAnswers({});

    setResult(null);

    setError("");

    setCurrentQuestionIndex(0);


    window.scrollTo({

      top: 0,

      behavior: "smooth",

    });
  };


  /*
   * =====================================
   * LOADING
   * =====================================
   */

  if (loading) {

    return <Loading />;

  }


  /*
   * =====================================
   * RESULT SCREEN
   * =====================================
   */

  if (result) {

    const percentage =
      Number(
        result.percentage ?? 0
      );


    return (

      <div className="quiz-page">

        <div className="quiz-result">


          <div className="result-icon">

            {percentage >= 70
              ? "🎉"
              : percentage >= 40
                ? "👏"
                : "📚"}

          </div>


          <span>
            QUIZ COMPLETED
          </span>


          <h1>

            {percentage >= 70
              ? "Excellent Work!"
              : percentage >= 40
                ? "Good Effort!"
                : "Keep Learning!"}

          </h1>


          <div className="score-circle">

            {percentage.toFixed(0)}%

          </div>


          <h2>

            You scored{" "}

            {result.score ?? 0}

            {" "}out of{" "}

            {result.totalQuestions ?? 0}

          </h2>


          <p>

            {percentage >= 70
              ? "You have demonstrated a strong understanding of constitutional concepts."
              : percentage >= 40
                ? "You are making good progress. Keep exploring and strengthening your knowledge."
                : "Understanding the Constitution takes time. Read more articles and try again!"}

          </p>


          <div className="quiz-result-actions">


            <button
              type="button"
              className="primary-button"
              onClick={handleTryAgain}
            >

              Try Again

            </button>


            <button
              type="button"
              className="secondary-button"
              onClick={() =>
                navigate("/progress")
              }
            >

              View Progress

            </button>


          </div>


        </div>

      </div>

    );
  }


  /*
   * =====================================
   * EMPTY STATE
   * =====================================
   */

  if (questions.length === 0) {

    return (

      <div className="quiz-page">

        <div className="quiz-result">


          <div className="result-icon">

            📝

          </div>


          <h1>
            No Quiz Questions Available
          </h1>


          <p>

            Quiz questions have not been added yet.
            Please check back later.

          </p>


          <button
            type="button"
            className="primary-button"
            onClick={loadQuestions}
          >

            Try Again

          </button>


        </div>

      </div>

    );
  }


  /*
   * =====================================
   * CURRENT QUESTION
   * =====================================
   */

  const currentQuestion =
    questions[currentQuestionIndex];


  /*
   * SAFETY CHECK
   */

  if (!currentQuestion) {

    return <Loading />;

  }


  /*
   * =====================================
   * PROGRESS PERCENTAGE
   * =====================================
   */

  const progressPercentage =

    (
      (currentQuestionIndex + 1)
      /
      questions.length
    )
    * 100;


  /*
   * =====================================
   * QUESTION OPTIONS
   * =====================================
   */

  const options = [

    {
      key: "A",
      value: currentQuestion.optionA,
    },

    {
      key: "B",
      value: currentQuestion.optionB,
    },

    {
      key: "C",
      value: currentQuestion.optionC,
    },

    {
      key: "D",
      value: currentQuestion.optionD,
    },

  ];


  /*
   * =====================================
   * QUIZ PAGE
   * =====================================
   */

  return (

    <div className="quiz-page">


      {/* =====================================
          HEADER
      ===================================== */}

      <div className="quiz-header">

        <span>
          KNOW YOUR CONSTITUTION
        </span>

        <h1>
          Constitution Quiz
        </h1>

        <p>
          Test your knowledge of the Constitution of India.
        </p>

      </div>


      {/* =====================================
          ERROR MESSAGE
      ===================================== */}

      {error && (

        <div className="error-message">

          {error}

        </div>

      )}


      {/* =====================================
          QUIZ PROGRESS BAR ONLY
      ===================================== */}

      <div className="quiz-progress-section">

        <div className="quiz-progress-bar">

          <div
            className="quiz-progress-fill"
            style={{

              width:
                `${progressPercentage}%`,

            }}
          />

        </div>

      </div>


      {/* =====================================
          QUESTION CARD
      ===================================== */}

      <div className="quiz-container">


        <div
          className="quiz-question-card"
          key={currentQuestion.id}
        >


          <h3>

            Question{" "}

            {currentQuestionIndex + 1}

          </h3>


          <h2>

            {currentQuestion.question}

          </h2>


          {/* =====================================
              OPTIONS
          ===================================== */}

          <div className="quiz-options">

            {options.map(
              (option) => (

                <button
                  type="button"
                  key={option.key}
                  className={

                    answers[currentQuestion.id]
                    === option.key

                      ? "quiz-option selected"

                      : "quiz-option"

                  }
                  onClick={() =>
                    selectAnswer(
                      currentQuestion.id,
                      option.key
                    )
                  }
                >


                  <span>

                    {option.key}

                  </span>


                  {option.value}


                </button>

              )
            )}

          </div>


        </div>


      </div>


      {/* =====================================
          NAVIGATION
      ===================================== */}

      <div className="quiz-navigation">


        <button
          type="button"
          className="secondary-button"
          disabled={
            currentQuestionIndex === 0
          }
          onClick={handlePrevious}
        >

          ← Previous

        </button>


        {currentQuestionIndex <
        questions.length - 1 ? (

          <button
            type="button"
            className="primary-button"
            onClick={handleNext}
          >

            Next →

          </button>

        ) : (

          <button
            type="button"
            className="primary-button"
            disabled={submitting}
            onClick={handleSubmit}
          >

            {submitting
              ? "Submitting..."
              : "Submit Quiz"}

          </button>

        )}


      </div>


    </div>

  );
}


export default Quiz;