import api from "./api";

export const checkBackendHealth = async () => {
    try {
        const response = await api.get("/health");
        return response.data;
    } catch (error) {
        console.error("Health API Error:", error);
        throw error;
    }
};