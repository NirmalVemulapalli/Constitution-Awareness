import api from "./api";

export const askAiQuestion = async (question, sessionId = null) => {
  const response = await api.post("/ai/chat", {
    question,
    sessionId,
  });

  return response.data;
};