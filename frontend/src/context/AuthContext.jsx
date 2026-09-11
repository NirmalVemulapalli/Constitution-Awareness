import {

  createContext,
  useContext,
  useEffect,
  useState,
  useRef,

} from "react";


const AuthContext =
  createContext();


export function AuthProvider({

  children,

}) {


  const [user, setUser] =
    useState(null);


  const [loading, setLoading] =
    useState(true);


  /*
   * =====================================
   * AUTO LOGOUT TIMER
   * =====================================
   */

  const logoutTimerRef =
    useRef(null);


  /*
   * =====================================
   * CLEAR AUTH DATA
   * =====================================
   */

  const clearAuthData = () => {


    localStorage.removeItem(
      "token"
    );


    localStorage.removeItem(
      "user"
    );


    setUser(null);


    /*
     * CLEAR EXISTING TIMER
     */

    if (

      logoutTimerRef.current

    ) {

      clearTimeout(
        logoutTimerRef.current
      );


      logoutTimerRef.current =
        null;

    }

  };


  /*
   * =====================================
   * GET TOKEN EXPIRATION
   * =====================================
   */

  const getTokenExpiration =
    (token) => {

      try {


        /*
         * JWT FORMAT:
         *
         * HEADER.PAYLOAD.SIGNATURE
         */

        const payload =
          token.split(".")[1];


        /*
         * CONVERT BASE64 URL
         * TO NORMAL BASE64
         */

        const base64 =
          payload
            .replace(
              /-/g,
              "+"
            )
            .replace(
              /_/g,
              "/"
            );


        const decodedPayload =
          JSON.parse(

            decodeURIComponent(

              window
                .atob(base64)
                .split("")
                .map(

                  (character) =>

                    "%"

                    +

                    (
                      "00"

                      +

                      character
                        .charCodeAt(0)
                        .toString(16)

                    )
                      .slice(-2)

                )
                .join("")

            )

          );


        /*
         * JWT exp IS IN SECONDS
         * CONVERT TO MILLISECONDS
         */

        if (

          !decodedPayload.exp

        ) {

          return null;

        }


        return (
          decodedPayload.exp
          *
          1000
        );

      }

      catch (error) {

        return null;

      }

    };


  /*
   * =====================================
   * START AUTO LOGOUT TIMER
   * =====================================
   */

  const startLogoutTimer =
    (token) => {


      /*
       * CLEAR PREVIOUS TIMER
       */

      if (

        logoutTimerRef.current

      ) {

        clearTimeout(
          logoutTimerRef.current
        );

      }


      const expirationTime =
        getTokenExpiration(
          token
        );


      /*
       * TOKEN HAS NO EXPIRATION
       */

      if (

        !expirationTime

      ) {

        return;

      }


      const remainingTime =

        expirationTime
        -
        Date.now();


      /*
       * TOKEN ALREADY EXPIRED
       */

      if (

        remainingTime <= 0

      ) {

        clearAuthData();

        return;

      }


      /*
       * AUTO LOGOUT WHEN TOKEN EXPIRES
       */

      logoutTimerRef.current =

        setTimeout(

          () => {

            clearAuthData();

          },

          remainingTime

        );

    };


  /*
   * =====================================
   * RESTORE USER SESSION
   * =====================================
   */

  useEffect(() => {


    const storedUser =
      localStorage.getItem(
        "user"
      );


    const storedToken =
      localStorage.getItem(
        "token"
      );


    if (

      storedUser

      &&

      storedToken

    ) {

      try {


        /*
         * CHECK TOKEN EXPIRATION
         */

        const expirationTime =
          getTokenExpiration(
            storedToken
          );


        /*
         * INVALID TOKEN
         */

        if (

          !expirationTime

        ) {

          clearAuthData();

        }


        /*
         * EXPIRED TOKEN
         */

        else if (

          expirationTime <= Date.now()

        ) {

          clearAuthData();

        }


        /*
         * VALID TOKEN
         */

        else {

          setUser(

            JSON.parse(
              storedUser
            )

          );


          /*
           * START AUTO LOGOUT TIMER
           */

          startLogoutTimer(
            storedToken
          );

        }

      }

      catch (error) {

        clearAuthData();

      }

    }


    setLoading(false);


    /*
     * CLEANUP TIMER
     */

    return () => {

      if (

        logoutTimerRef.current

      ) {

        clearTimeout(
          logoutTimerRef.current
        );

      }

    };


  }, []);


  /*
   * =====================================
   * LOGIN
   * =====================================
   */

  const login = (

    userData,

    token

  ) => {


    localStorage.setItem(

      "token",

      token

    );


    localStorage.setItem(

      "user",

      JSON.stringify(
        userData
      )

    );


    setUser(
      userData
    );


    /*
     * START AUTO LOGOUT TIMER
     */

    startLogoutTimer(
      token
    );

  };


  /*
   * =====================================
   * UPDATE CURRENT USER
   * =====================================
   */

  const updateUser = (

    updatedUserData

  ) => {


    const updatedUser = {

      ...user,

      ...updatedUserData,

    };


    localStorage.setItem(

      "user",

      JSON.stringify(
        updatedUser
      )

    );


    setUser(
      updatedUser
    );

  };


  /*
   * =====================================
   * LOGOUT
   * =====================================
   */

  const logout = () => {

    clearAuthData();

  };


  /*
   * =====================================
   * CONTEXT VALUE
   * =====================================
   */

  const value = {

    user,

    loading,

    login,

    updateUser,

    logout,

    isAuthenticated:
      !!user,

  };


  return (

    <AuthContext.Provider
      value={value}
    >

      {children}

    </AuthContext.Provider>

  );

}


export function useAuth() {

  return useContext(
    AuthContext
  );

}