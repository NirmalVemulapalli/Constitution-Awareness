import { useEffect, useState } from "react";

import ArticleCard from "../components/ArticleCard";
import SearchBar from "../components/SearchBar";
import Pagination from "../components/Pagination";
import Loading from "../components/Loading";

import {
getArticles,
searchArticles,
getArticlesByCategory,
getArticlesByPart,
} from "../services/articleService";

import { getCategories } from "../services/categoryService";
import { getParts } from "../services/partService";

function Articles() {

const [articles, setArticles] = useState([]);

const [categories, setCategories] =
useState([]);

const [parts, setParts] =
useState([]);

const [loading, setLoading] =
useState(true);

const [error, setError] =
useState("");

// What user is currently typing
const [searchTerm, setSearchTerm] =
useState("");

// Actual submitted search query
const [activeSearch, setActiveSearch] =
useState("");

const [selectedCategory, setSelectedCategory] =
useState("");

const [selectedPart, setSelectedPart] =
useState("");

const [currentPage, setCurrentPage] =
useState(0);

const [totalPages, setTotalPages] =
useState(0);

const pageSize = 6;

// Load categories and parts once
useEffect(() => {
loadFilters();
}, []);

// Load articles when filters, page, or submitted search changes
useEffect(() => {
loadArticles();
}, [
currentPage,
selectedCategory,
selectedPart,
activeSearch,
]);

const loadFilters = async () => {


try {

  const [
    categoriesData,
    partsData,
  ] = await Promise.all([
    getCategories(),
    getParts(),
  ]);

  setCategories(categoriesData);
  setParts(partsData);

} catch (error) {

  console.error(error);

}


};

const loadArticles = async () => {


try {

  setLoading(true);
  setError("");

  let data;

  if (selectedCategory) {

    data =
      await getArticlesByCategory(
        selectedCategory,
        currentPage,
        pageSize
      );

  } else if (selectedPart) {

    data =
      await getArticlesByPart(
        selectedPart,
        currentPage,
        pageSize
      );

  } else if (activeSearch.trim()) {

    data =
      await searchArticles(
        activeSearch,
        currentPage,
        pageSize
      );

  } else {

    data =
      await getArticles(
        currentPage,
        pageSize
      );
  }

  setArticles(data.content);

  setTotalPages(data.totalPages);

} catch (error) {

  console.error(error);

  setError(
    "Unable to load articles. Please check the backend connection."
  );

} finally {

  setLoading(false);

}


};

const handleSearch = () => {


setSelectedCategory("");
setSelectedPart("");

setActiveSearch(
  searchTerm.trim()
);

setCurrentPage(0);


};

const handleCategoryChange = (
event
) => {


setSelectedCategory(
  event.target.value
);

setSelectedPart("");

setSearchTerm("");
setActiveSearch("");

setCurrentPage(0);


};

const handlePartChange = (
event
) => {


setSelectedPart(
  event.target.value
);

setSelectedCategory("");

setSearchTerm("");
setActiveSearch("");

setCurrentPage(0);


};

return (


<div className="articles-page">

  <section className="articles-header">

    <h1>
      Explore the Constitution
    </h1>

    <p>
      Learn about the Articles, Fundamental Rights,
      and principles that form the foundation of
      India's democracy.
    </p>

  </section>


  <div className="articles-controls">

    <SearchBar
      searchTerm={searchTerm}
      setSearchTerm={setSearchTerm}
      onSearch={handleSearch}
    />


    <div className="filters">

      <select
        value={selectedCategory}
        onChange={handleCategoryChange}
      >

        <option value="">
          All Categories
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


      <select
        value={selectedPart}
        onChange={handlePartChange}
      >

        <option value="">
          All Parts
        </option>

        {parts.map((part) => (

          <option
            key={part.id}
            value={part.id}
          >
            {part.partNumber} — {part.title}
          </option>

        ))}

      </select>

    </div>

  </div>


  {loading && <Loading />}


  {error && (
    <div className="error-message">
      {error}
    </div>
  )}


  {!loading &&
    !error &&
    articles.length === 0 && (

      <div className="empty-state">

        <h3>
          No articles found
        </h3>

        <p>
          Try searching with a different keyword.
        </p>

      </div>

    )}


  {!loading &&
    !error &&
    articles.length > 0 && (

      <>
        <div className="articles-grid">

          {articles.map((article) => (

            <ArticleCard
              key={article.id}
              article={article}
            />

          ))}

        </div>


        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPageChange={setCurrentPage}
        />

      </>

    )}

</div>


);
}

export default Articles;
