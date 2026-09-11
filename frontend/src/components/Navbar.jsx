import {
  Link,
  NavLink,
  useNavigate,
} from "react-router-dom";

import {
  useState,
  useRef,
  useEffect,
} from "react";

import {
  useAuth,
} from "../context/AuthContext";


function Navbar() {

  const {
    user,
    logout,
  } = useAuth();


  const navigate =
    useNavigate();


  const [profileOpen, setProfileOpen] =
    useState(false);


  const profileRef =
    useRef(null);


  /*
   * =====================================
   * CLOSE PROFILE DROPDOWN
   * WHEN CLICKING OUTSIDE
   * =====================================
   */

  useEffect(() => {

    const handleClickOutside =
      (event) => {

        if (

          profileRef.current

          &&

          !profileRef.current.contains(
            event.target
          )

        ) {

          setProfileOpen(false);

        }

      };


    document.addEventListener(

      "mousedown",

      handleClickOutside

    );


    return () => {

      document.removeEventListener(

        "mousedown",

        handleClickOutside

      );

    };

  }, []);


  /*
   * =====================================
   * LOGOUT
   * =====================================
   */

  const handleLogout = () => {

    setProfileOpen(false);

    logout();

    navigate("/");

  };


  /*
   * =====================================
   * GET USER INITIAL
   * =====================================
   */

  const userInitial =
    user?.name
      ? user.name
          .charAt(0)
          .toUpperCase()
      : user?.email
          ?.charAt(0)
          ?.toUpperCase();


  return (

    <nav className="navbar">

      <div className="nav-container">


        {/* =====================================
            BRAND
        ===================================== */}

        <Link
          to="/"
          className="brand"
        >

          <span className="brand-symbol">
            ⚖
          </span>

          <span>
            Constitution Awareness
          </span>

        </Link>


        {/* =====================================
            NAVIGATION LINKS
        ===================================== */}

        <div className="nav-links">


          {/* HOME */}

          <NavLink
            to="/"
            className={({ isActive }) =>
              isActive
                ? "nav-link active"
                : "nav-link"
            }
          >
            Home
          </NavLink>


          {/* EXPLORE CONSTITUTION */}

          <NavLink
            to="/articles"
            className={({ isActive }) =>
              isActive
                ? "nav-link active"
                : "nav-link"
            }
          >
            Explore Constitution
          </NavLink>


          {/* QUIZ */}

          <NavLink
            to="/quiz"
            className={({ isActive }) =>
              isActive
                ? "nav-link active"
                : "nav-link"
            }
          >
            Quiz
          </NavLink>


          {/* =====================================
              CITIZEN NAVIGATION
          ===================================== */}

          {user?.role === "CITIZEN" && (

            <NavLink
              to="/progress"
              className={({ isActive }) =>
                isActive
                  ? "nav-link active"
                  : "nav-link"
              }
            >
              Progress
            </NavLink>

          )}


          {/* =====================================
              ADMIN NAVIGATION
          ===================================== */}

          {user?.role === "ADMIN" && (

            <>

              <NavLink
                to="/admin/dashboard"
                className={({ isActive }) =>
                  isActive
                    ? "nav-link active"
                    : "nav-link"
                }
              >
                Admin Dashboard
              </NavLink>


              <NavLink
                to="/articles/manage"
                className={({ isActive }) =>
                  isActive
                    ? "nav-link active"
                    : "nav-link"
                }
              >
                Manage Articles
              </NavLink>

            </>

          )}


          {/* =====================================
              EDUCATOR NAVIGATION
          ===================================== */}

          {user?.role === "EDUCATOR" && (

            <>

              <NavLink
                to="/educator/dashboard"
                className={({ isActive }) =>
                  isActive
                    ? "nav-link active"
                    : "nav-link"
                }
              >
                Educator Dashboard
              </NavLink>


              <NavLink
                to="/articles/manage"
                className={({ isActive }) =>
                  isActive
                    ? "nav-link active"
                    : "nav-link"
                }
              >
                Manage Articles
              </NavLink>

            </>

          )}

        </div>


        {/* =====================================
            AUTHENTICATION SECTION
        ===================================== */}

        <div className="nav-auth">

          {!user ? (

            <>

              <Link
                to="/login"
                className="nav-login"
              >
                Login
              </Link>


              <Link
                to="/register"
                className="nav-register"
              >
                Get Started
              </Link>

            </>

          ) : (

            <div
              className="profile-container"
              ref={profileRef}
            >


              {/* PROFILE BUTTON */}

              <button
                className="profile-button"
                onClick={() =>
                  setProfileOpen(
                    !profileOpen
                  )
                }
              >

                <div className="profile-avatar">

                  {userInitial}

                </div>


                <span className="profile-arrow">

                  {profileOpen
                    ? "▲"
                    : "▼"}

                </span>

              </button>


              {/* PROFILE DROPDOWN */}

              {profileOpen && (

                <div className="profile-dropdown">


                  {/* USER INFORMATION */}

                  <div className="profile-dropdown-header">

                    <div className="profile-dropdown-avatar">

                      {userInitial}

                    </div>


                    <div className="profile-user-info">

                      <strong>

                        {user.name}

                      </strong>


                      <span>

                        {user.email}

                      </span>

                    </div>

                  </div>


                  {/* ROLE */}

                  <div className="profile-role">

                    <span className="role-label">

                      Account Role

                    </span>


                    <span className="role-badge">

                      {user.role}

                    </span>

                  </div>


                  <div className="profile-divider" />


                  {/* PROFILE */}

                  <button
                    className="profile-menu-item"
                    onClick={() => {

                      setProfileOpen(false);

                      navigate("/profile");

                    }}
                  >

                    <span className="menu-icon">

                      👤

                    </span>

                    My Profile

                  </button>


                  {/* LOGOUT */}

                  <button
                    className="profile-menu-item logout-menu-item"
                    onClick={handleLogout}
                  >

                    <span className="menu-icon">

                      ↪

                    </span>

                    Logout

                  </button>


                </div>

              )}

            </div>

          )}

        </div>

      </div>

    </nav>

  );

}


export default Navbar;