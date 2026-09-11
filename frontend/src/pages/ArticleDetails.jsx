import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";

import Loading from "../components/Loading";

import {
  getArticleById,
} from "../services/articleService";

function ArticleDetails() {

  const { id } = useParams();

  const [article, setArticle] =
    useState(null);

  const [loading, setLoading] =
    useState(true);

  const [error, setError] =
    useState("");


  useEffect(() => {

    loadArticle();

  }, [id]);


  const loadArticle = async () => {

    try {

      setLoading(true);

      const data =
        await getArticleById(id);

      setArticle(data);

    } catch (error) {

      console.error(error);

      setError(
        "Unable to load article details."
      );

    } finally {

      setLoading(false);

    }
  };


  if (loading) {
    return <Loading />;
  }


  if (error) {
    return (
      <div className="error-message">
        {error}
      </div>
    );
  }


  if (!article) {
    return null;
  }


  return (

    <div className="article-details-page">

      <Link
        to="/articles"
        className="back-button"
      >
        ← Back to Articles
      </Link>


      <article className="article-details-card">

        <div className="article-details-header">

          <span className="article-number">
            {article.articleNumber}
          </span>

          <span className="category-badge">
            {article.categoryName}
          </span>

        </div>


        <h1>
          {article.title}
        </h1>


        <div className="article-meta">

          <span>
            📜 {article.partNumber}
          </span>

          <span>
            {article.partTitle}
          </span>

        </div>


        <section className="content-section">

          <h2>
            Constitutional Context
          </h2>

          <p>
            {article.constitutionalText ||
              "Constitutional text is not available."}
          </p>

        </section>


        <section className="content-section simplified">

          <h2>
            Simple Explanation
          </h2>

          <p>
            {article.simplifiedExplanation ||
              "Simplified explanation is not available."}
          </p>

        </section>


        <section className="content-section">

          <h2>
            Key Concepts
          </h2>

          <div className="keywords">

            {article.keywords
              ? article.keywords
                  .split(",")
                  .map(
                    (keyword, index) => (

                      <span
                        key={index}
                        className="keyword-tag"
                      >
                        {keyword.trim()}
                      </span>

                    )
                  )
              : (
                <span>
                  No keywords available.
                </span>
              )}

          </div>

        </section>

      </article>

    </div>

  );
}

export default ArticleDetails;