import api from "./api";


/*
 * =====================================
 * PUBLIC QUIZ APIs
 * =====================================
 */


/*
 * Get active quiz questions
 */

export const getQuizQuestions =
  async () => {

    const response =
      await api.get(
        "/quiz/questions"
      );

    return response.data;
  };


/*
 * Submit quiz
 */

export const submitQuiz =
  async (
    answers,
    token
  ) => {

    const response =
      await api.post(

        "/quiz/submit",

        {
          answers,
        },

        {
          headers: {

            Authorization:
              `Bearer ${token}`,

          },
        }

      );

    return response.data;
  };


/*
 * Get citizen learning progress
 */

export const getLearningProgress =
  async (
    token
  ) => {

    const response =
      await api.get(

        "/quiz/progress",

        {
          headers: {

            Authorization:
              `Bearer ${token}`,

          },
        }

      );

    return response.data;
  };


/*
 * =====================================
 * QUIZ MANAGEMENT APIs
 *
 * ADMIN + EDUCATOR
 * =====================================
 */


/*
 * Get manageable quiz questions
 */

export const getManageableQuizQuestions =
  async (

    page = 0,

    size = 10

  ) => {

    const response =
      await api.get(

        "/quiz/manage/questions",

        {
          params: {

            page,

            size,

          },
        }

      );

    return response.data;
  };


/*
 * Create quiz question
 */

export const createQuizQuestion =
  async (
    questionData
  ) => {

    const response =
      await api.post(

        "/quiz/questions",

        questionData

      );

    return response.data;
  };


/*
 * Update quiz question
 */

export const updateQuizQuestion =
  async (

    id,

    questionData

  ) => {

    const response =
      await api.put(

        `/quiz/questions/${id}`,

        questionData

      );

    return response.data;
  };


/*
 * Delete quiz question
 */

export const deleteQuizQuestion =
  async (
    id
  ) => {

    await api.delete(
      `/quiz/questions/${id}`
    );
  };


/*
 * =====================================
 * QUIZ ATTEMPTS
 * =====================================
 */


/*
 * NOTE:
 *
 * Keep this for now, but the backend
 * endpoint will be implemented separately.
 */

export const getQuizAttempts =
  async (

    page = 0,

    size = 10

  ) => {

    const response =
      await api.get(

        "/quiz/manage/attempts",

        {
          params: {

            page,

            size,

          },
        }

      );

    return response.data;
  };