import client from "./client"

export const fetchQuizzesApi = (count: number) => {
    return client.get("/quizzes", {
        params: { count }
    });
};

export const registerQuizResultApi = (data: {
    wordId: number;
    selectedMeaning: string;
}) => {
    return client.post("/quizzes/result", data);
};