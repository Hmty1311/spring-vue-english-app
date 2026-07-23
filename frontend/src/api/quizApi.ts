import client from "./client"

export const fetchQuizzesApi = (count: number) => {
    return client.get("/quizzes", {
        params: { count }
    });
};

export const registerQuizResultApi = (data: {
    sessionId: number;
    wordId: number;
    selectedMeaning: string;
}) => {
    return client.post("/quizzes/result", data);
};

export const startQuizApi = (count: number) => {
    return client.post("/quizzes/sessions", null,{
        params: { count }
    });
};

export const fetchQuizResultApi = (sessionId: number) => {
    return client.get(`/quizzes/result/${sessionId}`);
};