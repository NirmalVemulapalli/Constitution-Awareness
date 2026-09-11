import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
} from "react-router-dom";

import {
  getManageableArticles,
  searchManageableArticles,
  updateArticlePublishStatus,
  deleteArticle,
} from "../services/articleService";

import {
  useAuth,
} from "../context/AuthContext";


function ManageArticles() {

  const {
    user,
  } = useAuth();


  const navigate =
    useNavigate();


  const [articles, setArticles] =
    useState([]);


  const [loading, setLoading] =
    useState(true);


  const [error, setError] =
    useState("");


  const [searchKeyword, setSearchKeyword] =
    useState("");


  const [page, setPage] =
    useState(0);


  const [totalPages, setTotalPages] =
    useState(0);


  const size = 10;


  /*
   * =====================================
   * LOAD ARTICLES
   * =====================================
   */

  const loadArticles =
    async (
      currentPage = page
    ) => {

      try {

        setLoading(true);

        setError("");


        const data =
          await getManageableArticles(
            currentPage,
            size
          );


        setArticles(
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
          "Unable to load articles."
        );

      } finally {

        setLoading(false);
      }
    };


  /*
   * =====================================
   * INITIAL LOAD
   * =====================================
   */

  useEffect(() => {

    loadArticles(0);

  }, []);


  /*
   * =====================================
   * SEARCH ARTICLES
   * =====================================
   */

  const handleSearch =
    async (
      event
    ) => {

      event.preventDefault();


      if (
        !searchKeyword.trim()
      ) {

        loadArticles(0);

        return;
      }


      try {

        setLoading(true);

        setError("");


        const data =
          await searchManageableArticles(

            searchKeyword,

            0,

            size

          );


        setArticles(
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
          "Unable to search articles."
        );

      } finally {

        setLoading(false);
      }
    };


  /*
   * =====================================
   * CLEAR SEARCH
   * =====================================
   */

  const handleClearSearch =
    () => {

      setSearchKeyword("");

      loadArticles(0);
    };


  /*
   * =====================================
   * CHANGE PAGE
   * =====================================
   */

  const handlePageChange =
    async (
      newPage
    ) => {

      if (
        searchKeyword.trim()
      ) {

        try {

          setLoading(true);


          const data =
            await searchManageableArticles(

              searchKeyword,

              newPage,

              size

            );


          setArticles(
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
            "Unable to load articles."
          );

        } finally {

          setLoading(false);
        }

      } else {

        loadArticles(
          newPage
        );
      }
    };


  /*
   * =====================================
   * TOGGLE PUBLISH STATUS
   * =====================================
   */

  const handlePublishToggle =
    async (
      article
    ) => {

      try {

        await updateArticlePublishStatus(

          article.id,

          !article.published

        );


        await handlePageChange(
          page
        );

      } catch (error) {

        console.error(error);

        alert(
          "Unable to update article status."
        );
      }
    };


  /*
   * =====================================
   * DELETE ARTICLE
   *
   * ADMIN ONLY
   * =====================================
   */

  const handleDelete =
    async (
      article
    ) => {

      const confirmed =
        window.confirm(

          `Are you sure you want to delete "${article.title}"?`

        );


      if (!confirmed) {

        return;
      }


      try {

        await deleteArticle(
          article.id
        );


        /*
         * Reload current page
         */

        await handlePageChange(
          page
        );

      } catch (error) {

        console.error(error);

        alert(
          "Unable to delete article."
        );
      }
    };


  /*
   * =====================================
   * LOADING
   * =====================================
   */

  if (loading) {

    return (

      <div className="page-container">

        Loading articles...

      </div>

    );
  }


  /*
   * =====================================
   * ERROR
   * =====================================
   */

  if (error) {

    return (

      <div className="page-container">

        <p>
          {error}
        </p>

        <button
          onClick={() => loadArticles(0)}
        >
          Try Again
        </button>

      </div>

    );
  }


  return (

    <div className="manage-articles-page">


      {/* HEADER */}

      <div className="manage-header">

        <div>

          <span>
            CONTENT MANAGEMENT
          </span>

          <h1>

            {user?.role === "ADMIN"

              ? "Manage Articles"

              : "My Articles"

            }

          </h1>

          <p>

            {user?.role === "ADMIN"

              ? "Create, manage and publish constitutional articles."

              : "Create and manage your constitutional learning content."

            }

          </p>

        </div>


        <button
          className="create-article-button"
          onClick={() =>
            navigate(
              "/articles/create"
            )
          }
        >

          + Create Article

        </button>

      </div>


      {/* SEARCH */}

      <form
        className="manage-search"
        onSubmit={handleSearch}
      >

        <input

          type="text"

          placeholder="Search articles..."

          value={searchKeyword}

          onChange={(event) =>
            setSearchKeyword(
              event.target.value
            )
          }

        />


        <button
          type="submit"
        >

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


      {/* ARTICLES TABLE */}

      <div className="articles-management-table">

        <table>

          <thead>

            <tr>

              <th>
                Article
              </th>

              <th>
                Part
              </th>

              <th>
                Category
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

            {articles.length === 0 ? (

              <tr>

                <td
                  colSpan="5"
                >

                  No articles found.

                </td>

              </tr>

            ) : (

              articles.map(

                (article) => (

                  <tr
                    key={article.id}
                  >

                    {/* ARTICLE */}

                    <td>

                      <strong>

                        Article {
                          article.articleNumber
                        }

                      </strong>

                      <br />

                      <span>

                        {article.title}

                      </span>

                    </td>


                    {/* PART */}

                    <td>

                      {article.partNumber}

                      <br />

                      <small>

                        {article.partTitle}

                      </small>

                    </td>


                    {/* CATEGORY */}

                    <td>

                      {article.categoryName}

                    </td>


                    {/* STATUS */}

                    <td>

                      <span

                        className={
                          article.published

                            ? "status published"

                            : "status draft"
                        }

                      >

                        {article.published

                          ? "Published"

                          : "Draft"

                        }

                      </span>

                    </td>


                    {/* ACTIONS */}

                    <td>

                      <div
                        className="article-actions"
                      >


                        {/* EDIT */}

                        <button
                          onClick={() =>
                            navigate(

                              `/articles/${article.id}/edit`

                            )
                          }
                        >

                          Edit

                        </button>


                        {/* PUBLISH */}

                        <button
                          onClick={() =>
                            handlePublishToggle(
                              article
                            )
                          }
                        >

                          {article.published

                            ? "Unpublish"

                            : "Publish"

                          }

                        </button>


                        {/* DELETE ADMIN ONLY */}

                        {user?.role === "ADMIN" && (

                          <button

                            className="delete-button"

                            onClick={() =>
                              handleDelete(
                                article
                              )
                            }

                          >

                            Delete

                          </button>

                        )}

                      </div>

                    </td>

                  </tr>

                )

              )

            )}

          </tbody>

        </table>

      </div>


      {/* PAGINATION */}

      {totalPages > 1 && (

        <div
          className="management-pagination"
        >

          <button

            disabled={
              page === 0
            }

            onClick={() =>
              handlePageChange(
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

            disabled={
              page >= totalPages - 1
            }

            onClick={() =>
              handlePageChange(
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


export default ManageArticles;