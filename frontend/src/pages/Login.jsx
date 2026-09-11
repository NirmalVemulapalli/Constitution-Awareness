import {
  useState,
} from "react";

import {
  Link,
  useNavigate,
} from "react-router-dom";

import {
  loginUser,
} from "../services/authService";

import {
  useAuth,
} from "../context/AuthContext";


function Login() {

  const navigate =
    useNavigate();

  const {
    login,
  } = useAuth();


  const [email, setEmail] =
    useState("");

  const [password, setPassword] =
    useState("");

  const [error, setError] =
    useState("");

  const [loading, setLoading] =
    useState(false);


  const handleSubmit =
    async (event) => {

      event.preventDefault();

      setError("");

      setLoading(true);


      try {

        const data =
          await loginUser(
            email,
            password
          );


        const token =
          data.token;


const userData = {

  userId:
    data.userId,

  name:
    data.name,

  email:
    data.email,

  role:
    data.role,

};


        login(
          userData,
          token
        );


        // Role-based redirect

        if (data.role === "ADMIN") {

          navigate(
            "/admin/dashboard"
          );

        } else if (
          data.role === "EDUCATOR"
        ) {

          navigate(
            "/educator/dashboard"
          );

        } else {

          navigate("/");
        }


      } catch (error) {

        console.error(error);

        setError(
          error.response?.data?.message
          ||
          "Invalid email or password."
        );

      } finally {

        setLoading(false);
      }
    };


  return (

    <div className="auth-page">

      <div className="auth-card">

        <div className="auth-header">

          <span>
            KNOW YOUR RIGHTS
          </span>

          <h1>
            Welcome Back
          </h1>

          <p>
            Login to continue your constitutional learning journey.
          </p>

        </div>


        {error && (

          <div className="auth-error">

            {error}

          </div>

        )}


        <form
          onSubmit={handleSubmit}
        >

          <div className="form-group">

            <label>
              Email Address
            </label>

            <input

              type="email"

              placeholder="Enter your email"

              value={email}

              onChange={(event) =>
                setEmail(
                  event.target.value
                )
              }

              required

            />

          </div>


          <div className="form-group">

            <label>
              Password
            </label>

            <input

              type="password"

              placeholder="Enter your password"

              value={password}

              onChange={(event) =>
                setPassword(
                  event.target.value
                )
              }

              required

            />

          </div>


          <button

            type="submit"

            className="primary-button auth-button"

            disabled={loading}

          >

            {loading
              ? "Logging in..."
              : "Login"}

          </button>

        </form>


        <p className="auth-switch">

          Don't have an account?

          {" "}

          <Link to="/register">

            Create Account

          </Link>

        </p>

      </div>

    </div>

  );
}


export default Login;