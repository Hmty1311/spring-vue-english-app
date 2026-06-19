import axios from "axios";

const client = axios.create({
  // baseURL: "http://localhost:8080/api"
  baseURL: "https://improved-capybara-7rxq96pvq5c959-8080.app.github.dev/api"
});

client.interceptors.request.use(config => {

  const token = localStorage.getItem("token");

  if (token) {
    config.headers.Authorization =
      `Bearer ${token}`;
  }

  return config;
});

export default client;