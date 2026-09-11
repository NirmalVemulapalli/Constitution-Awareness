function SearchBar({
  searchTerm,
  setSearchTerm,
  onSearch,
}) {
  const handleSubmit = (event) => {
    event.preventDefault();
    onSearch();
  };

  return (
    <form
      className="search-bar"
      onSubmit={handleSubmit}
    >

      <input
        type="text"
        placeholder="Search articles, rights, equality..."
        value={searchTerm}
        onChange={(event) =>
          setSearchTerm(event.target.value)
        }
      />

      <button type="submit">
        Search
      </button>

    </form>
  );
}

export default SearchBar;