function Pagination({
  currentPage,
  totalPages,
  onPageChange,
}) {
  if (totalPages <= 1) {
    return null;
  }

  const pages = [];

  for (let i = 0; i < totalPages; i++) {
    pages.push(i);
  }

  return (
    <div className="pagination">

      <button
        disabled={currentPage === 0}
        onClick={() =>
          onPageChange(currentPage - 1)
        }
      >
        ← Previous
      </button>

      {pages.map((page) => (
        <button
          key={page}
          className={
            page === currentPage
              ? "page-number active-page"
              : "page-number"
          }
          onClick={() =>
            onPageChange(page)
          }
        >
          {page + 1}
        </button>
      ))}

      <button
        disabled={
          currentPage === totalPages - 1
        }
        onClick={() =>
          onPageChange(currentPage + 1)
        }
      >
        Next →
      </button>

    </div>
  );
}

export default Pagination;