<script setup lang = "ts">
import { ref, onMounted } from 'vue'
import axios from 'axios'

const loginId = ref('')
const password = ref('')

const message = ref('')
const token = ref('')

const login = async () => {
    message.value = ''

    try{
        const response = await axios.post('http://localhost:8080/api/login', {
            loginId: loginId.value,
            password: password.value
        })

        localStorage.setItem('token', response.data.token)

        token.value = response.data.token
        message.value = `ログイン成功: ${response.data.userName}`
    } catch (error: any) {
        if(error.response?.data?.message){
            message.value = error.response.data.message
        } else {
            message.value = 'ログインに失敗しました'
        }
    }
}

onMounted(() => {
    const savedToken = localStorage.getItem('token')

    if(savedToken) {
        token.value = savedToken
        message.value = 'ログイン済み'
    }
})

const logout = () => {
    localStorage.removeItem('token')

    token.value = ''
    message.value = 'ログアウトしました'
}

</script>

<template>
    <div>
        <h1>ログイン</h1>

        <div>
            <input v-model="loginId" placeholder="ログインID" />
        </div>

        <div>
            <input 
               v-model="password" 
                type="password" 
                placeholder="パスワード" 
            />
        </div>

        <button @click="login">
            ログイン
        </button>

        <button v-if="token" @click="logout">
            ログアウト
        </button>

        <p>{{  message }}</p>

        <p v-if="token">
            トークン: {{ token }}
        </p>
    </div>
</template>