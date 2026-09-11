import axios from "axios";


const API_URL =
  "http://localhost:8080/api/profile";


/*
 * =====================================
 * GET CURRENT PROFILE
 * =====================================
 */

export const getProfile =
  async () => {

    const token =
      localStorage.getItem(
        "token"
      );


    const response =
      await axios.get(

        API_URL,

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
 * UPDATE PROFILE
 * =====================================
 */

export const updateProfile =
  async (

    profileData

  ) => {

    const token =
      localStorage.getItem(
        "token"
      );


    const response =
      await axios.put(

        API_URL,

        profileData,

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
 * CHANGE PASSWORD
 * =====================================
 */

export const changePassword =
  async (

    passwordData

  ) => {

    const token =
      localStorage.getItem(
        "token"
      );


    await axios.put(

      `${API_URL}/change-password`,

      passwordData,

      {
        headers: {

          Authorization:
            `Bearer ${token}`,

        },
      }

    );

  };