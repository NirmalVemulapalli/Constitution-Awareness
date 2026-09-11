import {
  useEffect,
  useState,
} from "react";

import {
  useNavigate,
  useParams,
} from "react-router-dom";

import {
  createArticle,
  getArticleById,
  updateArticle,
} from "../services/articleService";

import {
  getCategories,
} from "../services/categoryService";

import {
  getParts,
} from "../services/partService";


function ArticleForm() {

  const {
    id,
  } = useParams();


  const navigate =
    useNavigate();


  const isEditMode =
    Boolean(id);


  /*
   * =====================================
   * FORM DATA
   * =====================================
   */

  const [formData, setFormData] =
    useState({

      articleNumber: "",

      title: "",

      constitutionalText: "",

      simplifiedExplanation: "",

      keywords: "",

      partId: "",

      categoryId: "",

      published: false,

    });


  /*
   * =====================================
   * DROPDOWN DATA
   * =====================================
   */

  const [categories, setCategories] =
    useState([]);


  const [parts, setParts] =
    useState([]);


  /*
   * =====================================
   * PAGE STATE
   * =====================================
   */

  const [loading, setLoading] =
    useState(true);


  const [submitting, setSubmitting] =
    useState(false);


  const [error, setError] =
    useState("");


  /*
   * =====================================
   * LOAD FORM DATA
   * =====================================
   */

  useEffect(() => {

    const loadData =
      async () => {

        try {

          setLoading(true);

          setError("");


          /*
           * Load categories and parts
           */

          const [

            categoriesData,

            partsData,

          ] = await Promise.all([

            getCategories(),

            getParts(),

          ]);


          setCategories(
            categoriesData || []
          );


          setParts(
            partsData || []
          );


          /*
           * EDIT MODE
           *
           * Load existing article
           */

          if (isEditMode) {

            const article =

              await getArticleById(
                id
              );


            setFormData({

              articleNumber:
                article.articleNumber || "",

              title:
                article.title || "",

              constitutionalText:
                article.constitutionalText || "",

              simplifiedExplanation:
                article.simplifiedExplanation || "",

              keywords:
                article.keywords || "",

              partId:
                article.partId || "",

              categoryId:
                article.categoryId || "",

              published:
                article.published || false,

            });
          }

        } catch (error) {

          console.error(error);

          setError(

            "Unable to load article form data."

          );

        } finally {

          setLoading(false);
        }
      };


    loadData();

  }, [

    id,

    isEditMode,

  ]);


  /*
   * =====================================
   * HANDLE INPUT CHANGE
   * =====================================
   */

  const handleChange =
    (event) => {

      const {

        name,

        value,

        type,

        checked,

      } = event.target;


      setFormData(

        (previousData) => ({

          ...previousData,

          [name]:

            type === "checkbox"

              ? checked

              : value,

        })

      );
    };


  /*
   * =====================================
   * SUBMIT FORM
   * =====================================
   */

  const handleSubmit =
    async (
      event
    ) => {

      event.preventDefault();


      try {

        setSubmitting(true);

        setError("");


        /*
         * Convert IDs to numbers
         */

        const articleData = {

          ...formData,

          partId:
            Number(formData.partId),

          categoryId:
            Number(formData.categoryId),

        };


        /*
         * CREATE
         */

        if (!isEditMode) {

          await createArticle(
            articleData
          );

        }


        /*
         * UPDATE
         */

        else {

          await updateArticle(

            id,

            articleData

          );
        }


        /*
         * RETURN TO MANAGEMENT PAGE
         */

        navigate(
          "/articles/manage"
        );

      } catch (error) {

        console.error(error);


        setError(

          error?.response?.data?.message ||

          "Unable to save article."

        );

      } finally {

        setSubmitting(false);
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

        Loading...

      </div>

    );
  }


  /*
   * =====================================
   * PAGE
   * =====================================
   */

  return (

    <div className="article-form-page">


      {/* HEADER */}

      <div className="article-form-header">

        <span>

          ARTICLE MANAGEMENT

        </span>


        <h1>

          {isEditMode

            ? "Edit Article"

            : "Create Article"

          }

        </h1>


        <p>

          {isEditMode

            ? "Update constitutional learning content."

            : "Add new constitutional learning content to the platform."

          }

        </p>

      </div>


      {/* ERROR */}

      {error && (

        <div className="form-error">

          {error}

        </div>

      )}


      {/* FORM */}

      <form
        className="article-form"
        onSubmit={handleSubmit}
      >


        {/* ARTICLE NUMBER */}

        <div className="form-group">

          <label>

            Article Number

          </label>


          <input

            type="text"

            name="articleNumber"

            value={
              formData.articleNumber
            }

            onChange={handleChange}

            placeholder="Example: 21"

            required

          />

        </div>


        {/* TITLE */}

        <div className="form-group">

          <label>

            Article Title

          </label>


          <input

            type="text"

            name="title"

            value={
              formData.title
            }

            onChange={handleChange}

            placeholder="Example: Protection of Life and Personal Liberty"

            required

          />

        </div>


        {/* PART */}

        <div className="form-group">

          <label>

            Constitutional Part

          </label>


          <select

            name="partId"

            value={
              formData.partId
            }

            onChange={handleChange}

            required

          >

            <option value="">

              Select Part

            </option>


            {parts.map(

              (part) => (

                <option
                  key={part.id}
                  value={part.id}
                >

                  {part.partNumber}
                  {" - "}
                  {part.title}

                </option>

              )

            )}

          </select>

        </div>


        {/* CATEGORY */}

        <div className="form-group">

          <label>

            Category

          </label>


          <select

            name="categoryId"

            value={
              formData.categoryId
            }

            onChange={handleChange}

            required

          >

            <option value="">

              Select Category

            </option>


            {categories.map(

              (category) => (

                <option
                  key={category.id}
                  value={category.id}
                >

                  {category.name}

                </option>

              )

            )}

          </select>

        </div>


        {/* CONSTITUTIONAL TEXT */}

        <div className="form-group">

          <label>

            Constitutional Text

          </label>


          <textarea

            name="constitutionalText"

            value={
              formData.constitutionalText
            }

            onChange={handleChange}

            placeholder="Enter the official constitutional text..."

            rows="8"

          />

        </div>


        {/* SIMPLIFIED EXPLANATION */}

        <div className="form-group">

          <label>

            Simplified Explanation

          </label>


          <textarea

            name="simplifiedExplanation"

            value={
              formData.simplifiedExplanation
            }

            onChange={handleChange}

            placeholder="Explain this article in simple language..."

            rows="8"

          />

        </div>


        {/* KEYWORDS */}

        <div className="form-group">

          <label>

            Keywords

          </label>


          <input

            type="text"

            name="keywords"

            value={
              formData.keywords
            }

            onChange={handleChange}

            placeholder="Example: fundamental rights, liberty, equality"

          />

        </div>


        {/* PUBLISH STATUS */}

        <div className="form-checkbox">

          <input

            type="checkbox"

            name="published"

            checked={
              formData.published
            }

            onChange={handleChange}

          />


          <label>

            Publish article immediately

          </label>

        </div>


        {/* ACTION BUTTONS */}

        <div className="form-actions">


          <button

            type="button"

            className="cancel-button"

            onClick={() =>
              navigate(
                "/articles/manage"
              )
            }

          >

            Cancel

          </button>


          <button

            type="submit"

            className="save-button"

            disabled={submitting}

          >

            {submitting

              ? "Saving..."

              : isEditMode

                ? "Update Article"

                : "Create Article"

            }

          </button>

        </div>

      </form>

    </div>

  );
}


export default ArticleForm;