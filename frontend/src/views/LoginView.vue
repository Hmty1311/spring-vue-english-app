<script setup>
import { ref } from "vue";
import { useRouter } from "vue-router";
import client from "../api/client";

const router = useRouter();

const loginId = ref("");
const password = ref("");

const errorMessage = ref("");

const login = async () => {

  errorMessage.value = "";

  try {

    const response = await client.post(
      "/login",
      {
        loginId: loginId.value,
        password: password.value
      }
    );

    localStorage.setItem(
      "token",
      response.data.token
    );

    router.push("/words");

  } catch (error) {

    errorMessage.value =
      "ログインに失敗しました";
  }
};
</script>

<template>
  <div>
    <h1>Login</h1>

    <div>
      <input
        v-model="loginId"
        placeholder="Login ID"
      />
    </div>

    <div>
      <input
        v-model="password"
        type="password"
        placeholder="Password"
      />
    </div>

    <div>
      <button @click="login">
        Login
      </button>
    </div>

    <p v-if="errorMessage">
      {{ errorMessage }}
    </p>
  </div>
</template>