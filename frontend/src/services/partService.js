import api from "./api";


export const getParts = async () => {

  const response = await api.get("/parts");

  return response.data;
};