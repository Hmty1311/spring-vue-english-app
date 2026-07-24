import client from "./client";

export const loginApi = (data: {
    loginId: string;
    password: string;
}) => {
    return client.post("/login", data);
};

export const logoutApi = () => {
    return client.post("/logout");
};