import client from "./client";

export const fetchWordsApi = (params: any) => {
        return client.get(`/words`,{
            params,
        });
}

export const createWordApi = (data: any) => {
    return client.post(`/words`,data);
}

export const fetchWordApi = (id: number) => {
    return client.get(`/words/${id}`);
};

export const updateWordApi = (
    id: number,
    data: any
) => {
    return client.put(
        `/words/${id}`,
        data
    );
};