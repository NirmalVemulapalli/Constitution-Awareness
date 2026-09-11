import {
  useEffect,
  useState,
} from "react";

import {
  getUsers,
  searchUsers,
  updateUser,
  deleteUser,
} from "../services/userService";


function Users() {


  const [users, setUsers] =
    useState([]);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");

  const [success, setSuccess] =
    useState("");

  const [searchKeyword, setSearchKeyword] =
    useState("");

  const [page, setPage] =
    useState(0);

  const [totalPages, setTotalPages] =
    useState(0);

  const [editingUser, setEditingUser] =
    useState(null);

  const [name, setName] =
    useState("");

  const [role, setRole] =
    useState("");

  const [active, setActive] =
    useState(true);


  const loadUsers = async (
    pageNumber = page
  ) => {

    try {

      setLoading(true);

      setError("");


      let data;


      if (searchKeyword.trim()) {

        data =
          await searchUsers(
            searchKeyword,
            pageNumber
          );

      } else {

        data =
          await getUsers(
            pageNumber
          );
      }


      setUsers(
        data.content || []
      );

      setTotalPages(
        data.totalPages || 0
      );

      setPage(
        data.number || 0
      );

    } catch (error) {

      console.error(error);

      setError(
        "Unable to load users."
      );

    } finally {

      setLoading(false);
    }
  };


  useEffect(() => {

    loadUsers(0);

  }, []);


  const handleSearch = (
    event
  ) => {

    event.preventDefault();

    loadUsers(0);
  };


  const handleClearSearch = () => {

    setSearchKeyword("");

    setTimeout(() => {

      loadUsers(0);

    }, 0);
  };


  const startEdit = (
    user
  ) => {

    setSuccess("");

    setError("");

    setEditingUser(user);

    setName(user.name);

    setRole(user.role);

    setActive(user.active);

    window.scrollTo({

      top: 0,

      behavior: "smooth",

    });
  };


  const cancelEdit = () => {

    setEditingUser(null);

    setName("");

    setRole("");

    setActive(true);
  };


  const handleUpdate = async (
    event
  ) => {

    event.preventDefault();

    if (!editingUser) {

      return;
    }


    try {

      setError("");

      setSuccess("");


      const userData = {

        name: name.trim(),

        role,

        active,

      };


      await updateUser(
        editingUser.id,
        userData
      );


      setSuccess(
        "User updated successfully."
      );


      cancelEdit();


      await loadUsers(page);

    } catch (error) {

      console.error(error);

      setError(

        error?.response?.data?.message ||

        "Unable to update user."

      );

    }
  };


  const handleDelete = async (
    id
  ) => {

    const confirmed =
      window.confirm(
        "Are you sure you want to delete this user?"
      );


    if (!confirmed) {

      return;
    }


    try {

      setError("");

      setSuccess("");


      await deleteUser(id);


      setSuccess(
        "User deleted successfully."
      );


      await loadUsers(page);

    } catch (error) {

      console.error(error);

      setError(

        error?.response?.data?.message ||

        "Unable to delete user."

      );

    }
  };


  return (

    <div className="manage-articles-page">


      {/* HEADER */}

      <div className="manage-header">

        <div>

          <span>
            ADMINISTRATION
          </span>

          <h1>
            User Management
          </h1>

          <p>
            Manage platform users, roles,
            and account status.
          </p>

        </div>

      </div>


      {/* EDIT USER */}

      {editingUser && (

        <div className="article-form">

          <h2>
            Edit User
          </h2>


          <form
            onSubmit={handleUpdate}
          >

            <div className="form-group">

              <label>
                Name
              </label>

              <input
                type="text"
                value={name}
                onChange={(event) =>
                  setName(
                    event.target.value
                  )
                }
              />

            </div>


            <div className="form-group">

              <label>
                Role
              </label>

              <select
                value={role}
                onChange={(event) =>
                  setRole(
                    event.target.value
                  )
                }
              >

                <option value="CITIZEN">
                  Citizen
                </option>

                <option value="EDUCATOR">
                  Educator
                </option>

                <option value="ADMIN">
                  Admin
                </option>

              </select>

            </div>


            <div className="form-checkbox">

              <input
                type="checkbox"
                id="active"
                checked={active}
                onChange={(event) =>
                  setActive(
                    event.target.checked
                  )
                }
              />

              <label htmlFor="active">
                Active Account
              </label>

            </div>


            <div className="form-actions">

              <button
                type="button"
                className="cancel-button"
                onClick={cancelEdit}
              >
                Cancel
              </button>


              <button
                type="submit"
                className="save-button"
              >
                Update User
              </button>

            </div>

          </form>

        </div>

      )}


      {/* SEARCH */}

      <form
        className="manage-search"
        onSubmit={handleSearch}
      >

        <input
          type="text"
          placeholder="Search by name or email..."
          value={searchKeyword}
          onChange={(event) =>
            setSearchKeyword(
              event.target.value
            )
          }
        />


        <button type="submit">

          Search

        </button>


        {searchKeyword && (

          <button
            type="button"
            onClick={handleClearSearch}
          >

            Clear

          </button>

        )}

      </form>


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


      {/* LOADING */}

      {loading ? (

        <p>
          Loading users...
        </p>

      ) : (


        /* USERS TABLE */

        <div className="articles-management-table">

          <table>

            <thead>

              <tr>

                <th>
                  Name
                </th>

                <th>
                  Email
                </th>

                <th>
                  Role
                </th>

                <th>
                  Status
                </th>

                <th>
                  Actions
                </th>

              </tr>

            </thead>


            <tbody>

              {users.length === 0 ? (

                <tr>

                  <td
                    colSpan="5"
                  >

                    No users found.

                  </td>

                </tr>

              ) : (

                users.map((user) => (

                  <tr
                    key={user.id}
                  >

                    <td>

                      <strong>
                        {user.name}
                      </strong>

                    </td>


                    <td>

                      {user.email}

                    </td>


                    <td>

                      <span>

                        {user.role}

                      </span>

                    </td>


                    <td>

                      <span
                        className={
                          `status ${
                            user.active
                              ? "published"
                              : "draft"
                          }`
                        }
                      >

                        {user.active
                          ? "Active"
                          : "Inactive"}

                      </span>

                    </td>


                    <td>

                      <div className="article-actions">

                        <button
                          type="button"
                          onClick={() =>
                            startEdit(user)
                          }
                        >

                          Edit

                        </button>


                        <button
                          type="button"
                          className="delete-button"
                          onClick={() =>
                            handleDelete(
                              user.id
                            )
                          }
                        >

                          Delete

                        </button>

                      </div>

                    </td>

                  </tr>

                ))

              )}

            </tbody>

          </table>

        </div>

      )}


      {/* PAGINATION */}

      {totalPages > 1 && (

        <div className="management-pagination">

          <button
            type="button"
            disabled={page === 0}
            onClick={() =>
              loadUsers(
                page - 1
              )
            }
          >

            Previous

          </button>


          <span>

            Page {page + 1}
            {" "}
            of
            {" "}
            {totalPages}

          </span>


          <button
            type="button"
            disabled={
              page >= totalPages - 1
            }
            onClick={() =>
              loadUsers(
                page + 1
              )
            }
          >

            Next

          </button>

        </div>

      )}

    </div>
  );
}


export default Users;