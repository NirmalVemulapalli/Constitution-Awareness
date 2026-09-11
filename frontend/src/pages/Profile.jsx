import {
  useState,
} from "react";

import {
  useAuth,
} from "../context/AuthContext";

import {
  updateProfile,
  changePassword,
} from "../services/profileService";

import "./Profile.css";


function Profile() {

  const {

    user,

    updateUser,

  } = useAuth();


  /*
   * =====================================
   * EDIT PROFILE STATE
   * =====================================
   */

  const [

    isEditing,

    setIsEditing,

  ] = useState(false);


  const [

    name,

    setName,

  ] = useState(
    user?.name || ""
  );


  const [

    loading,

    setLoading,

  ] = useState(false);


  const [

    error,

    setError,

  ] = useState("");


  const [

    success,

    setSuccess,

  ] = useState("");


  /*
   * =====================================
   * CHANGE PASSWORD STATE
   * =====================================
   */

  const [

    currentPassword,

    setCurrentPassword,

  ] = useState("");


  const [

    newPassword,

    setNewPassword,

  ] = useState("");


  const [

    confirmPassword,

    setConfirmPassword,

  ] = useState("");


  const [

    passwordLoading,

    setPasswordLoading,

  ] = useState(false);


  const [

    passwordError,

    setPasswordError,

  ] = useState("");


  const [

    passwordSuccess,

    setPasswordSuccess,

  ] = useState("");


  /*
   * =====================================
   * PASSWORD VISIBILITY STATE
   * =====================================
   */

  const [

    showCurrentPassword,

    setShowCurrentPassword,

  ] = useState(false);


  const [

    showNewPassword,

    setShowNewPassword,

  ] = useState(false);


  const [

    showConfirmPassword,

    setShowConfirmPassword,

  ] = useState(false);


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
          .toUpperCase();


  /*
   * =====================================
   * FORMAT ROLE
   * =====================================
   */

  const formatRole =
    (role) => {

      if (!role) {

        return "User";

      }


      return (

        role.charAt(0)
          .toUpperCase()

        +

        role.slice(1)
          .toLowerCase()

      );

    };


  /*
   * =====================================
   * START EDITING
   * =====================================
   */

  const handleEdit = () => {

    setName(
      user?.name || ""
    );


    setError("");


    setSuccess("");


    setIsEditing(true);

  };


  /*
   * =====================================
   * CANCEL EDITING
   * =====================================
   */

  const handleCancel = () => {

    setName(
      user?.name || ""
    );


    setError("");


    setIsEditing(false);

  };


  /*
   * =====================================
   * SAVE PROFILE
   * =====================================
   */

  const handleSave =
    async () => {

      setError("");

      setSuccess("");


      if (

        !name.trim()

      ) {

        setError(
          "Name cannot be empty."
        );

        return;

      }


      if (

        name.trim().length < 2

      ) {

        setError(
          "Name must contain at least 2 characters."
        );

        return;

      }


      try {

        setLoading(true);


        const updatedProfile =

          await updateProfile({

            name:
              name.trim(),

          });


        /*
         * UPDATE GLOBAL AUTH USER
         */

        updateUser(
          updatedProfile
        );


        setSuccess(
          "Profile updated successfully."
        );


        setIsEditing(false);

      }

      catch (error) {

        setError(

          error?.response?.data?.message

          ||

          "Unable to update profile. Please try again."

        );

      }

      finally {

        setLoading(false);

      }

    };


  /*
   * =====================================
   * CHANGE PASSWORD
   * =====================================
   */

  const handleChangePassword =
    async (event) => {

      event.preventDefault();


      setPasswordError("");

      setPasswordSuccess("");


      /*
       * VALIDATE CURRENT PASSWORD
       */

      if (

        !currentPassword

      ) {

        setPasswordError(
          "Please enter your current password."
        );

        return;

      }


      /*
       * VALIDATE NEW PASSWORD
       */

      if (

        !newPassword

      ) {

        setPasswordError(
          "Please enter a new password."
        );

        return;

      }


      /*
       * VALIDATE PASSWORD LENGTH
       */

      if (

        newPassword.length < 6

      ) {

        setPasswordError(
          "New password must contain at least 6 characters."
        );

        return;

      }


      /*
       * VALIDATE PASSWORD CONFIRMATION
       */

      if (

        newPassword !== confirmPassword

      ) {

        setPasswordError(
          "New password and confirmation password do not match."
        );

        return;

      }


      /*
       * PREVENT SAME PASSWORD
       */

      if (

        currentPassword === newPassword

      ) {

        setPasswordError(
          "New password must be different from your current password."
        );

        return;

      }


      try {

        setPasswordLoading(true);


        await changePassword({

          currentPassword,

          newPassword,

        });


        /*
         * CLEAR PASSWORD FIELDS
         */

        setCurrentPassword("");

        setNewPassword("");

        setConfirmPassword("");


        /*
         * RESET PASSWORD VISIBILITY
         */

        setShowCurrentPassword(false);

        setShowNewPassword(false);

        setShowConfirmPassword(false);


        setPasswordSuccess(
          "Password changed successfully."
        );

      }

      catch (error) {

        setPasswordError(

          error?.response?.data?.message

          ||

          "Unable to change password. Please verify your current password and try again."

        );

      }

      finally {

        setPasswordLoading(false);

      }

    };


  return (

    <div className="profile-page">


      {/* =====================================
          PROFILE HERO
      ===================================== */}

      <section className="profile-hero">

        <div className="profile-hero-content">


          <div className="profile-large-avatar">

            {userInitial}

          </div>


          <div className="profile-hero-info">

            <p className="profile-welcome">

              Welcome back,

            </p>


            <h1>

              {user?.name || "User"}

            </h1>


            <p>

              Manage your account information
              and preferences.

            </p>

          </div>

        </div>

      </section>


      {/* =====================================
          PROFILE CONTENT
      ===================================== */}

      <div className="profile-content">


        {/* =====================================
            ACCOUNT INFORMATION
        ===================================== */}

        <section className="profile-card">

          <div className="profile-card-header profile-header-row">


            <div>

              <p className="profile-section-label">

                ACCOUNT

              </p>


              <h2>

                Personal Information

              </h2>


              <p>

                Your account details and
                registered information.

              </p>

            </div>


            {!isEditing && (

              <button
                className="edit-profile-button"
                onClick={handleEdit}
              >

                Edit Profile

              </button>

            )}

          </div>


          {/* =====================================
              SUCCESS MESSAGE
          ===================================== */}

          {success && (

            <div className="profile-success-message">

              {success}

            </div>

          )}


          {/* =====================================
              ERROR MESSAGE
          ===================================== */}

          {error && (

            <div className="profile-error-message">

              {error}

            </div>

          )}


          <div className="profile-info-list">


            {/* =====================================
                FULL NAME
            ===================================== */}

            <div className="profile-info-item">

              <div className="profile-info-icon">

                👤

              </div>


              <div className="profile-info-details">

                <span>

                  Full Name

                </span>


                {isEditing ? (

                  <input
                    type="text"
                    className="profile-name-input"
                    value={name}
                    onChange={(event) => {

                      setName(
                        event.target.value
                      );

                    }}
                    maxLength="100"
                    autoFocus
                  />

                ) : (

                  <strong>

                    {user?.name || "Not Available"}

                  </strong>

                )}

              </div>

            </div>


            {/* =====================================
                EMAIL
            ===================================== */}

            <div className="profile-info-item">

              <div className="profile-info-icon">

                ✉

              </div>


              <div className="profile-info-details">

                <span>

                  Email Address

                </span>


                <strong>

                  {user?.email || "Not Available"}

                </strong>

              </div>

            </div>


            {/* =====================================
                ROLE
            ===================================== */}

            <div className="profile-info-item">

              <div className="profile-info-icon">

                🛡

              </div>


              <div className="profile-info-details">

                <span>

                  Account Role

                </span>


                <strong>

                  {formatRole(user?.role)}

                </strong>

              </div>

            </div>

          </div>


          {/* =====================================
              EDIT ACTIONS
          ===================================== */}

          {isEditing && (

            <div className="profile-edit-actions">

              <button
                className="profile-cancel-button"
                onClick={handleCancel}
                disabled={loading}
              >

                Cancel

              </button>


              <button
                className="profile-save-button"
                onClick={handleSave}
                disabled={loading}
              >

                {loading
                  ? "Saving..."
                  : "Save Changes"}

              </button>

            </div>

          )}

        </section>


        {/* =====================================
            SECURITY
        ===================================== */}

        <section className="profile-card security-card">


          <div className="profile-card-header">

            <div>

              <p className="profile-section-label">

                SECURITY

              </p>


              <h2>

                Change Password

              </h2>


              <p>

                Keep your account secure by
                using a strong password.

              </p>

            </div>

          </div>


          {/* =====================================
              PASSWORD SUCCESS MESSAGE
          ===================================== */}

          {passwordSuccess && (

            <div className="profile-success-message">

              {passwordSuccess}

            </div>

          )}


          {/* =====================================
              PASSWORD ERROR MESSAGE
          ===================================== */}

          {passwordError && (

            <div className="profile-error-message">

              {passwordError}

            </div>

          )}


          <form
            className="change-password-form"
            onSubmit={handleChangePassword}
          >


            {/* =====================================
                CURRENT PASSWORD
            ===================================== */}

            <div className="password-form-group">

              <label>

                Current Password

              </label>


              <div className="password-input-wrapper">

                <input
                  type={
                    showCurrentPassword
                      ? "text"
                      : "password"
                  }
                  value={currentPassword}
                  onChange={(event) => {

                    setCurrentPassword(
                      event.target.value
                    );

                  }}
                  placeholder="Enter current password"
                  autoComplete="current-password"
                  disabled={passwordLoading}
                />


                <button
                  type="button"
                  className="password-visibility-button"
                  onClick={() => {

                    setShowCurrentPassword(
                      !showCurrentPassword
                    );

                  }}
                  disabled={passwordLoading}
                  aria-label={
                    showCurrentPassword
                      ? "Hide current password"
                      : "Show current password"
                  }
                >

                  {showCurrentPassword
                    ? "Hide"
                    : "Show"}

                </button>

              </div>

            </div>


            {/* =====================================
                NEW PASSWORD
            ===================================== */}

            <div className="password-form-group">

              <label>

                New Password

              </label>


              <div className="password-input-wrapper">

                <input
                  type={
                    showNewPassword
                      ? "text"
                      : "password"
                  }
                  value={newPassword}
                  onChange={(event) => {

                    setNewPassword(
                      event.target.value
                    );

                  }}
                  placeholder="Enter new password"
                  autoComplete="new-password"
                  disabled={passwordLoading}
                />


                <button
                  type="button"
                  className="password-visibility-button"
                  onClick={() => {

                    setShowNewPassword(
                      !showNewPassword
                    );

                  }}
                  disabled={passwordLoading}
                  aria-label={
                    showNewPassword
                      ? "Hide new password"
                      : "Show new password"
                  }
                >

                  {showNewPassword
                    ? "Hide"
                    : "Show"}

                </button>

              </div>


              <small>

                Password must contain at least
                6 characters.

              </small>

            </div>


            {/* =====================================
                CONFIRM NEW PASSWORD
            ===================================== */}

            <div className="password-form-group">

              <label>

                Confirm New Password

              </label>


              <div className="password-input-wrapper">

                <input
                  type={
                    showConfirmPassword
                      ? "text"
                      : "password"
                  }
                  value={confirmPassword}
                  onChange={(event) => {

                    setConfirmPassword(
                      event.target.value
                    );

                  }}
                  placeholder="Confirm new password"
                  autoComplete="new-password"
                  disabled={passwordLoading}
                />


                <button
                  type="button"
                  className="password-visibility-button"
                  onClick={() => {

                    setShowConfirmPassword(
                      !showConfirmPassword
                    );

                  }}
                  disabled={passwordLoading}
                  aria-label={
                    showConfirmPassword
                      ? "Hide confirmation password"
                      : "Show confirmation password"
                  }
                >

                  {showConfirmPassword
                    ? "Hide"
                    : "Show"}

                </button>

              </div>

            </div>


            {/* =====================================
                PASSWORD ACTION
            ===================================== */}

            <div className="change-password-actions">

              <button
                type="submit"
                className="change-password-button"
                disabled={passwordLoading}
              >

                {passwordLoading
                  ? "Changing Password..."
                  : "Change Password"}

              </button>

            </div>

          </form>

        </section>


        {/* =====================================
            ACCOUNT STATUS
        ===================================== */}

        <section className="profile-card profile-status-card">

          <div className="profile-card-header">

            <div>

              <p className="profile-section-label">

                ACCOUNT STATUS

              </p>


              <h2>

                Your Account

              </h2>


              <p>

                Overview of your account access.

              </p>

            </div>

          </div>


          <div className="account-status-box">

            <div className="status-indicator">

              <span className="status-dot">

              </span>

              Account Active

            </div>


            <p>

              Your account is active and you
              have access to all features
              available for your role.

            </p>

          </div>

        </section>

      </div>

    </div>

  );

}


export default Profile;