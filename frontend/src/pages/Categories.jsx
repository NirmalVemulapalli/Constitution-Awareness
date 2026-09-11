import {
  useEffect,
  useState,
} from "react";

import {
  getCategories,
  createCategory,
  updateCategory,
  deleteCategory,
} from "../services/categoryService";


function Categories() {

  const [categories, setCategories] =
    useState([]);

  const [name, setName] =
    useState("");

  const [description, setDescription] =
    useState("");

  const [editingId, setEditingId] =
    useState(null);

  const [loading, setLoading] =
    useState(true);

  const [submitting, setSubmitting] =
    useState(false);

  const [error, setError] =
    useState("");

  const [success, setSuccess] =
    useState("");


  const loadCategories = async () => {

    try {

      setLoading(true);

      const data =
        await getCategories();

      setCategories(data);

    } catch (error) {

      console.error(error);

      setError(
        "Unable to load categories."
      );

    } finally {

      setLoading(false);
    }
  };


  useEffect(() => {

    loadCategories();

  }, []);


  const resetForm = () => {

    setName("");

    setDescription("");

    setEditingId(null);
  };


  const handleSubmit = async (event) => {

    event.preventDefault();

    setError("");

    setSuccess("");


    if (!name.trim()) {

      setError(
        "Category name is required."
      );

      return;
    }


    try {

      setSubmitting(true);


      const categoryData = {

        name: name.trim(),

        description:
          description.trim() || null,

      };


      if (editingId) {

        await updateCategory(
          editingId,
          categoryData
        );

        setSuccess(
          "Category updated successfully."
        );

      } else {

        await createCategory(
          categoryData
        );

        setSuccess(
          "Category created successfully."
        );
      }


      resetForm();

      await loadCategories();

    } catch (error) {

      console.error(error);

      setError(

        error?.response?.data?.message ||

        "Unable to save category."

      );

    } finally {

      setSubmitting(false);
    }
  };


  const handleEdit = (category) => {

    setError("");

    setSuccess("");

    setEditingId(category.id);

    setName(category.name);

    setDescription(
      category.description || ""
    );


    window.scrollTo({

      top: 0,

      behavior: "smooth",

    });
  };


  const handleDelete = async (id) => {

    const confirmed =
      window.confirm(
        "Are you sure you want to delete this category?"
      );


    if (!confirmed) {

      return;
    }


    try {

      setError("");

      setSuccess("");


      await deleteCategory(id);


      setSuccess(
        "Category deleted successfully."
      );


      if (editingId === id) {

        resetForm();
      }


      await loadCategories();

    } catch (error) {

      console.error(error);

      setError(

        error?.response?.data?.message ||

        "Unable to delete category."

      );
    }
  };


  return (

    <div className="dashboard-page">

      <div className="dashboard-header">

        <span>
          ADMINISTRATION
        </span>

        <h1>
          Category Management
        </h1>

        <p>
          Create, update and organize categories
          for constitutional content.
        </p>

      </div>


      <div className="management-grid">


        {/* Create / Edit Category */}

        <div className="management-card">

          <h2>

            {editingId
              ? "Edit Category"
              : "Create Category"}

          </h2>


          <form
            onSubmit={handleSubmit}
            className="category-form"
          >

            <div className="form-group">

              <label>
                Category Name
              </label>

              <input
                type="text"
                placeholder="Example: Fundamental Rights"
                value={name}
                onChange={(event) =>
                  setName(event.target.value)
                }
                maxLength={100}
                required
              />

            </div>


            <div className="form-group">

              <label>
                Description
              </label>

              <textarea
                placeholder="Enter a short description..."
                value={description}
                onChange={(event) =>
                  setDescription(
                    event.target.value
                  )
                }
                maxLength={500}
                rows="4"
              />

            </div>


            {error && (

              <p className="form-error">
                {error}
              </p>

            )}


            {success && (

              <p className="form-success">
                {success}
              </p>

            )}


            <div className="form-actions">

              <button
                type="submit"
                className="primary-button"
                disabled={submitting}
              >

                {submitting
                  ? "Saving..."
                  : editingId
                    ? "Update Category"
                    : "Create Category"}

              </button>


              {editingId && (

                <button
                  type="button"
                  className="secondary-button"
                  onClick={resetForm}
                >
                  Cancel
                </button>

              )}

            </div>

          </form>

        </div>


        {/* Category List */}

        <div className="management-card">

          <h2>
            Existing Categories
          </h2>


          {loading ? (

            <p>
              Loading categories...
            </p>

          ) : categories.length === 0 ? (

            <p>
              No categories created yet.
            </p>

          ) : (

            <div className="category-list">

              {categories.map((category) => (

                <div
                  key={category.id}
                  className="category-item"
                >

                  <div className="category-content">

                    <h3>
                      {category.name}
                    </h3>


                    {category.description && (

                      <p>
                        {category.description}
                      </p>

                    )}

                  </div>


                  <div className="category-actions">

                    <button
                      type="button"
                      className="edit-button"
                      onClick={() =>
                        handleEdit(category)
                      }
                    >
                      Edit
                    </button>


                    <button
                      type="button"
                      className="delete-button"
                      onClick={() =>
                        handleDelete(category.id)
                      }
                    >
                      Delete
                    </button>

                  </div>

                </div>

              ))}

            </div>

          )}

        </div>

      </div>

    </div>

  );
}


export default Categories;