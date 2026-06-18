<script setup lang="ts">
import axios from "axios";
import { onMounted, reactive, ref } from "vue";
import { useRoute } from "vue-router";

import router from "../router";

import {
    fetchWordApi,
    updateWordApi
} from "../api/wordApi";

const route = useRoute();

const wordId = Number(route.params.id);

const form = reactive({
    word: "",
    meaning: "",
    example: "",
    memorized:false,
});

const loading = ref(false);

const errorMessage = ref("");

const fetchWord = async() => {

    try {

        loading.value = true;

        const response = await fetchWordApi(wordId);

        form.word = response.data.word;
        form.meaning = response.data.meaning;
        form.example = response.data.example;
        form.memorized = response.data.memorized;

    } catch(error){

        console.error(error);

        errorMessage.value = "Failed to fetch word.";
    
    } finally {

        loading.value = false;
    }
};

const submit = async() => {

    try {

        loading.value = true;

        errorMessage.value = "";

        await updateWordApi(wordId, form);

        router.push("/words");
    
    } catch(error){

        console.error(error);

        if(axios.isAxiosError(error)){

            errorMessage.value =
                error.response?.data?.message
                ?? "Failed to update word.";
        } else {

            errorMessage.value = "Failed to update word.";

        }

    } finally {

        loading.value = false;

    }
};

onMounted(() => {
    fetchWord();
});
</script>

<template>
    <div>
        <h1>Edit Word</h1>

        <div v-if="errorMessage">
            {{  errorMessage }}
        </div>

        <div v-if="loading">
            Loading...
        </div>

        <div v-else>
            <div>
                <input
                    v-model="form.word"
                    placeholder="Word"
                />
            </div>

            <div>
                <input
                    v-model="form.meaning"
                    placeholder="Meaning"
                />
            </div>

            <div>
                <input
                    v-model="form.example"
                    placeholder="Example"
                />
            </div>

            <div>
                <label>
                    <input 
                        type="checkbox"
                        v-model="form.memorized"
                    />
                    Memorized
                </label>
            </div>

            <button
                @click="submit"
                :disabled="loading"
            >
            Update
            </button>
        </div>

    </div>
</template>