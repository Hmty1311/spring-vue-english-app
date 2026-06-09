import client from "./client";

export const fetchWordsApi = (params: any) => {
        return client.get("/words",{
            params,
        });
}

export const createWordApi = (data: any) => {
    return client.post("/words",data);
}