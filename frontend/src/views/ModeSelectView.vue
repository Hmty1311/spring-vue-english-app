<script setup lang="ts">
    import router from "../router";
    import { logoutApi } from "../api/authApi";
    import PageHeader from "../components/PageHeader.vue";


    const goWords = () => {
        router.push("../words");
    };

    const goQuiz = () => {
        router.push("../quiz");
    };

    const logout = async () => {
        try {
            await logoutApi();

            localStorage.removeItem("token");

            router.push("../login");

        } catch (e) {
            alert("ログアウトに失敗しました。")
        }
    };
</script>
<template>
    <PageHeader
        title="EngApp"
        :showBack="false"
        :showMenu="false"
    />
    <div class="mode-container">
        <button 
            type="button"
            class="mode-button" 
            @click="goWords">
            📚 単語管理
        </button>

        <button 
            type="button"
            class="mode-button" 
            @click="goQuiz"
        >
            🎯 クイズ
        </button>

        <button 
            type="button"
            class="mode-button" 
            @click="logout"
        >
            🚪 ログアウト
        </button>
    </div>
</template>

<style scoped>
    .mode-container {
        display: flex;
        flex-direction: column;
        gap: 16px;

        max-width: 400px;
        margin: 40px auto;
    }

    .mode-button {
        padding: 16px;
        border-radius: 12px;
        font-size: 18px;
        cursor: pointer;
    }
</style>