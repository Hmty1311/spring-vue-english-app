<template>
  <div>
    <h1>Login</h1>
    <button @click="handleLogin">テストログイン</button>
  </div>
</template>

<script setup lang="ts">
import { login } from '../api/loginApi'

const handleLogin = () => {
  login()
}
</script>