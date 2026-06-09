<script setup lang="ts">
import { onMounted, reactive, ref, watch } from "vue";
import router from "../router";
import { createWordApi } from "../api/wordApi";
const form = reactive({
    word: "",
    meaning: "",
    memorized:false,
});

const submit = async () => {
    await createWordApi(form);

    router.push("/words");
}

const loading = ref(false);

</script>

<template>
    <div>
        <h1>Create WOrd</h1>

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