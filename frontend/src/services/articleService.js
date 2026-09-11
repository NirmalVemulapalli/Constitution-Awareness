import api from "./api";


/*
 * =====================================
 * PUBLIC ARTICLE APIs
 * =====================================
 */


/*
 * Get all published articles
 */
export const getArticles = async (
  page = 0,
  size = 6
) => {

  const response = await api.get(
    "/articles",
    {
      params: {
        page,
        size,
      },
    }
  );

  return response.data;
};


/*
 * Get single article
 */
export const getArticleById = async (
  id
) => {

  const response = await api.get(
    `/articles/${id}`
  );

  return response.data;
};


/*
 * Search published articles
 */
export const searchArticles = async (
  keyword,
  page = 0,
  size = 6
) => {

  const response = await api.get(
    "/articles/search",
    {
      params: {
        keyword,
        page,
        size,
      },
    }
  );

  return response.data;
};


/*
 * Get articles by category
 */
export const getArticlesByCategory = async (
  categoryId,
  page = 0,
  size = 6
) => {

  const response = await api.get(
    `/articles/category/${categoryId}`,
    {
      params: {
        page,
        size,
      },
    }
  );

  return response.data;
};


/*
 * Get articles by constitutional part
 */
export const getArticlesByPart = async (
  partId,
  page = 0,
  size = 6
) => {

  const response = await api.get(
    `/articles/part/${partId}`,
    {
      params: {
        page,
        size,
      },
    }
  );

  return response.data;
};


/*
 * =====================================
 * ARTICLE MANAGEMENT APIs
 *
 * ADMIN + EDUCATOR
 * =====================================
 */


/*
 * Get manageable articles
 *
 * ADMIN:
 * All articles
 *
 * EDUCATOR:
 * Only own articles
 */
export const getManageableArticles = async (
  page = 0,
  size = 10
) => {

  const response = await api.get(
    "/articles/manage",
    {
      params: {
        page,
        size,
      },
    }
  );

  return response.data;
};


/*
 * Search manageable articles
 */
export const searchManageableArticles = async (
  keyword,
  page = 0,
  size = 10
) => {

  const response = await api.get(
    "/articles/manage/search",
    {
      params: {
        keyword,
        page,
        size,
      },
    }
  );

  return response.data;
};


/*
 * Create article
 */
export const createArticle = async (
  articleData
) => {

  const response = await api.post(
    "/articles",
    articleData
  );

  return response.data;
};


/*
 * Update article
 */
export const updateArticle = async (
  id,
  articleData
) => {

  const response = await api.put(
    `/articles/${id}`,
    articleData
  );

  return response.data;
};


/*
 * Publish / Unpublish article
 */
export const updateArticlePublishStatus = async (
  id,
  published
) => {

  const response = await api.patch(
    `/articles/${id}/publish`,
    null,
    {
      params: {
        published,
      },
    }
  );

  return response.data;
};


/*
 * Delete article
 *
 * ADMIN ONLY
 */
export const deleteArticle = async (
  id
) => {

  await api.delete(
    `/articles/${id}`
  );
};