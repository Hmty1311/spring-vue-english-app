<script setup lang="ts">
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";

import { fetchQuizResultApi } from "../api/quizApi";
import type { QuizResult } from "../types/QuizResult";
import { useRouter } from "vue-router";

const router = useRouter();

const retryQuiz = () => {
    router.push("/quiz");
};

const backToWords = () => {
    router.push("/words");
};

const route = useRoute();

const result = ref<QuizResult>();

const loading = ref(false);

const loadResult = async () => {

    loading.value = true;

    try {

        const sessionId = Number(route.params.sessionId);

        const response =
            await fetchQuizResultApi(sessionId);

        result.value = response.data;

    } finally {
        loading.value = false;
    }

};

onMounted(() => {
    loadResult();
});
</script>

<template>
    <div>
        <h1>Quiz Result</h1>

        <div v-if="loading">
            Loading...
        </div>

        <div v-else-if="result">
            <p>
                {{ result.correct }} / {{ result.total }} 問正解
            </p>

            <p>
                正答率：{{ result.accuracy }}%
            </p>

            <h2>間違えた単語</h2>

            <ul>
                <li
                    v-for="wrongAnswer in result.wrongAnswers"
                    :key="wrongAnswer.word"
                >
                    {{ wrongAnswer.word }}：{{ wrongAnswer.meaning }}
                </li>
            </ul>
        </div>
        <button @click="retryQuiz">
            もう一度挑戦
        </button>

        <button @click="backToWords">
            単語一覧へ戻る
        </button>
    </div>
</template>