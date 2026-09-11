import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
} from "react-router-dom";

import Loading from "../components/Loading";

import {
  getLearningProgress,
} from "../services/quizService";


function Progress() {


  const navigate =
    useNavigate();


  const [progress, setProgress] =
    useState(null);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");


  /*
   * =====================================
   * LOAD LEARNING PROGRESS
   * =====================================
   */

  useEffect(() => {

    loadProgress();

  }, []);


  const loadProgress = async () => {

    const token =
      localStorage.getItem("token");


    if (!token) {

      setError(
        "Please login as a Citizen to view your learning progress."
      );

      setLoading(false);

      return;
    }


    try {

      setError("");

      const data =
        await getLearningProgress(
          token
        );


      setProgress(
        data
      );

    } catch (error) {

      console.error(error);


      setError(

        error?.response?.data?.message ||

        "Unable to load learning progress."

      );

    } finally {

      setLoading(false);
    }
  };


  /*
   * =====================================
   * CALCULATE ACCURACY
   * =====================================
   */

  const getAccuracy = () => {

    const totalQuestions =
      progress?.totalQuestionsAttempted ?? 0;

    const correctAnswers =
      progress?.totalCorrectAnswers ?? 0;


    if (totalQuestions === 0) {

      return 0;
    }


    return (

      (correctAnswers / totalQuestions) * 100

    ).toFixed(1);
  };


  /*
   * =====================================
   * PERFORMANCE MESSAGE
   * =====================================
   */

  const getPerformanceMessage = () => {

    const averageScore =
      progress?.averageScore ?? 0;


    if (
      averageScore === 0 &&
      (progress?.totalAttempts ?? 0) === 0
    ) {

      return {

        title:
          "Start Your Journey! 🇮🇳",

        message:
          "You have not attempted a quiz yet. Take your first quiz and begin tracking your constitutional knowledge.",

      };
    }


    if (averageScore < 40) {

      return {

        title:
          "Keep Learning! 📚",

        message:
          "Every attempt is a step forward. Explore more constitutional articles and try the quiz again.",

      };
    }


    if (averageScore < 70) {

      return {

        title:
          "Good Progress! 🌟",

        message:
          "You are building a solid understanding. Keep learning and aim for an even higher score.",

      };
    }


    if (averageScore < 90) {

      return {

        title:
          "Excellent Work! 🎉",

        message:
          "You have a strong understanding of constitutional concepts. Keep challenging yourself.",

      };
    }


    return {

      title:
        "Outstanding! 🏆",

      message:
        "Your constitutional knowledge is excellent. Keep maintaining your learning momentum!",

    };
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
   * ERROR
   * =====================================
   */

  if (error) {

    return (

      <div className="progress-page">

        <div className="progress-header">

          <span>
            YOUR LEARNING JOURNEY
          </span>

          <h1>
            Learning Progress
          </h1>

        </div>


        <div className="error-message">

          {error}

        </div>


        <button
          type="button"
          className="primary-button"
          onClick={loadProgress}
        >

          Try Again

        </button>

      </div>

    );
  }


  /*
   * =====================================
   * PERFORMANCE DATA
   * =====================================
   */

  const performance =
    getPerformanceMessage();


  const totalAttempts =
    progress?.totalAttempts ?? 0;

  const totalQuestionsAttempted =
    progress?.totalQuestionsAttempted ?? 0;

  const totalCorrectAnswers =
    progress?.totalCorrectAnswers ?? 0;

  const averageScore =
    progress?.averageScore ?? 0;


  /*
   * =====================================
   * PAGE
   * =====================================
   */

  return (

    <div className="progress-page">


      {/* =====================================
          HEADER
      ===================================== */}

      <div className="progress-header">

        <span>
          YOUR LEARNING JOURNEY
        </span>

        <h1>
          Learning Progress
        </h1>

        <p>
          Track your constitutional knowledge
          and quiz performance.
        </p>

      </div>


      {/* =====================================
          PROGRESS STATISTICS
      ===================================== */}

      <div className="progress-grid">


        <div className="progress-card">

          <span>
            Quiz Attempts
          </span>

          <h2>
            {totalAttempts}
          </h2>

        </div>


        <div className="progress-card">

          <span>
            Questions Attempted
          </span>

          <h2>
            {totalQuestionsAttempted}
          </h2>

        </div>


        <div className="progress-card">

          <span>
            Correct Answers
          </span>

          <h2>
            {totalCorrectAnswers}
          </h2>

        </div>


        <div className="progress-card">

          <span>
            Average Score
          </span>

          <h2>
            {Number(
              averageScore
            ).toFixed(1)}%
          </h2>

        </div>


      </div>


      {/* =====================================
          ACCURACY SECTION
      ===================================== */}

      {totalQuestionsAttempted > 0 && (

        <div className="progress-message">

          <h2>
            Overall Accuracy
          </h2>

          <div className="progress-accuracy">

            <div className="accuracy-bar">

              <div
                className="accuracy-fill"
                style={{

                  width:
                    `${getAccuracy()}%`,

                }}
              />

            </div>


            <strong>

              {getAccuracy()}%

            </strong>

          </div>

        </div>

      )}


      {/* =====================================
          PERFORMANCE MESSAGE
      ===================================== */}

      <div className="progress-message">

        <h2>
          {performance.title}
        </h2>

        <p>
          {performance.message}
        </p>


        <button
          type="button"
          className="primary-button"
          onClick={() =>
            navigate("/quiz")
          }
        >

          {totalAttempts === 0
            ? "Take Your First Quiz"
            : "Take Another Quiz"}

        </button>

      </div>


    </div>

  );
}


export default Progress;