import {
  useState,
} from "react";

import {
  Link,
  useNavigate,
} from "react-router-dom";

import {
  registerUser,
} from "../services/authService";


function Register() {

  const navigate =
    useNavigate();


  const [name, setName] =
    useState("");

  const [email, setEmail] =
    useState("");

  const [password, setPassword] =
    useState("");

  const [confirmPassword,
    setConfirmPassword] =
    useState("");

  const [error, setError] =
    useState("");

  const [success, setSuccess] =
    useState("");

  const [loading, setLoading] =
    useState(false);


  const handleSubmit =
    async (event) => {

      event.preventDefault();

      setError("");

      setSuccess("");


      if (
        password !== confirmPassword
      ) {

        setError(
          "Passwords do not match."
        );

        return;
      }


      setLoading(true);


      try {

        await registerUser(

          name,

          email,

          password,

          "CITIZEN"

        );


        setSuccess(
          "Account created successfully! Redirecting to login..."
        );


        setTimeout(() => {

          navigate("/login");

        }, 1500);


      } catch (error) {

        console.error(error);

        setError(

          error.response?.data?.message

          ||

          "Unable to create account. Please try again."

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
            JOIN THE PLATFORM
          </span>

          <h1>
            Create Account
          </h1>

          <p>
            Start learning about the Constitution of India.
          </p>

        </div>


        {error && (

          <div className="auth-error">

            {error}

          </div>

        )}


        {success && (

          <div className="auth-success">

            {success}

          </div>

        )}


        <form
          onSubmit={handleSubmit}
        >

          <div className="form-group">

            <label>
              Full Name
            </label>

            <input

              type="text"

              placeholder="Enter your name"

              value={name}

              onChange={(event) =>
                setName(
                  event.target.value
                )
              }

              required

            />

          </div>


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

              placeholder="Create a password"

              value={password}

              onChange={(event) =>
                setPassword(
                  event.target.value
                )
              }

              required

            />

          </div>


          <div className="form-group">

            <label>
              Confirm Password
            </label>

            <input

              type="password"

              placeholder="Confirm your password"

              value={confirmPassword}

              onChange={(event) =>
                setConfirmPassword(
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
              ? "Creating Account..."
              : "Create Account"}

          </button>

        </form>


        <p className="auth-switch">

          Already have an account?

          {" "}

          <Link to="/login">

            Login

          </Link>

        </p>

      </div>

    </div>

  );
}


export default Register;