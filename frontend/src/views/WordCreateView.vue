<script setup lang="ts">
import axios from "axios";
import { reactive, ref } from "vue";
import router from "../router";
import { createWordApi } from "../api/wordApi";
import PageHeader from "../components/PageHeader.vue";

const form = reactive({
    word: "",
    meaning: "",
    memorized:false,
});

const loading = ref(false);

const errorMessage = ref("");

const submit = async () => {

    try{
        loading.value = true;
        errorMessage.value = "";

        await createWordApi(form);

        router.push("/words");
    } catch(error){
        console.error(error);

        if(axios.isAxiosError(error)){
            errorMessage.value =
                error.response?.data?.message
                ?? "Failed to create word.";
        } else {
            errorMessage.value =
                "Failed to create word.";
        }
    } finally {
        loading.value = false;
    }
}


</script>

<template>
    <PageHeader
        title="Word Create"
        backTo="/words"
    />
    <div>
        <div>
            <RouterLink to="/words">
                Word List
            </RouterLink>
        </div>

        <div v-if="errorMessage">
            {{  errorMessage }}
        </div>

        <div>
            <input
                v-model="form.word"
                placeholder="Word"/>
        </div>
        <div>
            <input
                v-model="form.meaning"
                placeholder="Meaning"
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

        <button @click="submit"
        :disabled="loading">
            Create
        </button>
    </div>
</template>