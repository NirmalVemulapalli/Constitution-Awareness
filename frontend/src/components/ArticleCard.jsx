import { Link } from "react-router-dom";

function ArticleCard({ article }) {
  return (
    <div className="article-card">

      <div className="article-card-header">

        <span className="article-number">
          {article.articleNumber}
        </span>

        <span className="category-badge">
          {article.categoryName}
        </span>

      </div>

      <h3>{article.title}</h3>

      <p>
        {article.simplifiedExplanation
          ? article.simplifiedExplanation.length > 160
            ? article.simplifiedExplanation.substring(0, 160) + "..."
            : article.simplifiedExplanation
          : "No simplified explanation available."}
      </p>

      <div className="article-card-footer">

        <span className="part-label">
          {article.partNumber}
        </span>

        <Link
          to={`/articles/${article.id}`}
          className="read-more"
        >
          Read More →
        </Link>

      </div>

    </div>
  );
}

export default ArticleCard;