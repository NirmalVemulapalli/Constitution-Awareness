import api from "./api";


/*
 * =====================================
 * USER MANAGEMENT APIs
 *
 * ADMIN ONLY
 * =====================================
 */


/*
 * Get all users
 */
export const getUsers = async (
  page = 0,
  size = 10
) => {

  const response = await api.get(
    "/admin/users",
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
 * Search users
 */
export const searchUsers = async (
  keyword,
  page = 0,
  size = 10
) => {

  const response = await api.get(
    "/admin/users/search",
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
 * Get single user
 */
export const getUserById = async (
  id
) => {

  const response = await api.get(
    `/admin/users/${id}`
  );

  return response.data;
};


/*
 * Update user
 */
export const updateUser = async (
  id,
  userData
) => {

  const response = await api.put(
    `/admin/users/${id}`,
    userData
  );

  return response.data;
};


/*
 * Delete user
 */
export const deleteUser = async (
  id
) => {

  await api.delete(
    `/admin/users/${id}`
  );
};