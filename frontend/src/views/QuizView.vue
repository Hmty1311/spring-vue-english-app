<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import { fetchQuizzesApi, registerQuizResultApi } from "../api/quizApi";
import type { Quiz } from "../types/Quiz";
import router from "../router";

const quizzes = ref<Quiz[]>([]);
const loading = ref(false);

const currentIndex = ref(0);
const currentQuiz = computed(() => quizzes.value[currentIndex.value]);

const answered = ref(false);
const correct = ref(false);
const correctMeaning = ref("");

const loadQuizzes = async () => {
    loading.value = true;

    try {
        const response = await fetchQuizzesApi(10);
        quizzes.value = response.data;
    } finally {
        loading.value = false;
    }
};

const selectedMeaning = ref("");

const submitAnswer = async () => {

    if (!currentQuiz.value) {
        return;
    }

    if (!selectedMeaning.value) {
        alert("回答を選択してください。");
        return;
    }

    try {
        const response = await registerQuizResultApi({
            wordId: currentQuiz.value.wordId,
            selectedMeaning: selectedMeaning.value
        });

        answered.value = true;
        correct.value = response.data.correct;
        correctMeaning.value = response.data.correctMeaning;

        selectedMeaning.value = "";

        if (currentIndex.value === quizzes.value.length - 1) {
            router.push("/result");
            return;
        }

        currentIndex.value++;

    } catch (e) {
        console.error(e);
        alert("回答の送信に失敗しました。");
    }
};

const nextQuestion = () => {

    answered.value = false;
    selectedMeaning.value = "";
    correct.value = false;
    correctMeaning.value = "";

    if(currentIndex.value === quizzes.value.length -1) {
        router.push("/result");
        return;
    }

    currentIndex.value++;
}


onMounted(() => {
    loadQuizzes();
});
</script>

<template>
    <div>
        <h1>Quiz</h1>

        <div v-if="loading">
            Loading...
        </div>

        <div v-else-if="quizzes.length === 0">
            No Quiz
        </div>

        <div v-else-if="currentQuiz">
            <h2>{{ currentQuiz.word }}</h2>

            <ul class="choice-list">
                <li
                    v-for="choice in currentQuiz.choices"
                    :key="choice"
                >
                    <label>
                        <input 
                            type="radio"
                            v-model="selectedMeaning"
                            :value="choice"
                            :disabled="answered"
                        />
                        {{ choice }}
                    </label>
                </li>
            </ul>

            
            <button 
                v-if="!answered"
                @click="submitAnswer"
                :disabled="!selectedMeaning"
            >
                回答する
            </button>
            
            <div v-if="answered">
                <h3 v-if="correct">
                    ○ 正解！
                </h3>

                <div v-else>
                    <h3>× 不正解</h3>

                    <p>
                        正解: {{ correctMeaning }}
                    </p>
                </div>

                <button @click="nextQuestion">
                    次へ
                </button>
            </div>            
        </div>

    </div>
</template>

<style scoped>
.choice-list {
    list-style: none;
    padding: 0;
    margin: 0;
}
</style>