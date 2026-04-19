<script setup>
import { ref } from 'vue'
import axios from 'axios'

const message = ref('')

const callApi = async () => {
  try {
    const response = await axios.get('http://localhost:8080/api/login')
    message.value = response.data
  } catch (error) {
    console.error(error)
    message.value = 'エラー発生'
  }
}
</script>

<template>
  <h1>EngApp</h1>
  <button @click="callApi">API呼び出し</button>
  <p>{{ message }}</p>
</template>