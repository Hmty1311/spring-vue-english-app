<script setup lang="ts">
import { onMounted, reactive, ref, watch } from "vue";
import { fetchWordsApi } from "../api/wordApi";
import type { Word } from "../types/Word";
import { deleteWordApi } from "../api/wordApi";
import router from "../router";

const words = ref<Word[]>([]);

const goQuiz = () => {
    router.push("../quiz");
};

const searchCondition = reactive({
  keyword: "",
  memorized: null as boolean | null,
  page: 0,
  size: 20,
});

const search = async () => {
  searchCondition.page = 0;
  await fetchWords();
};

const totalPages = ref(0);
const loading = ref(false);
const errorMessage = ref("");

const fetchWords = async () => {
  try {
    loading.value = true;
    errorMessage.value = "";

    const response =
      await fetchWordsApi(searchCondition);

    words.value = response.data.content;
    totalPages.value = response.data.totalPages;
    } catch (error) {
      errorMessage.value = "Failed to fetch words.";
      console.error(error);
    } finally {
      loading.value = false;
    }
};

const deleteWord = async (id: number) => {
  if (!confirm("Delete this word?")) return;

  try {
    await deleteWordApi(id);

    // 画面更新
    await fetchWords();

  } catch (error) {
    console.error(error);
    errorMessage.value = "Failed to delete word.";
  }
};

watch(
  () => searchCondition.memorized,
  () => {
    search();
  }
);

onMounted(fetchWords);

</script>

<template>
  <div>
    <h1>Word List</h1>

    <button @click="goQuiz">
        Quiz
    </button>

    <div>
      <RouterLink to="/words/create">
        Create Word
      </RouterLink>
    </div>

    <div v-if="errorMessage">
      {{ errorMessage }}
    </div>

    <input 
      v-model="searchCondition.keyword"
      placeholder="Search word"
      @keyup.enter="search"
    />

    <select v-model="searchCondition.memorized">
      <option :value="null">All</option>
      <option :value="true">Memorized</option>
      <option :value="false">Not Memorized</option>
    </select>

    <button @click="search" :disabled="loading">Search</button>

    <div v-if="loading">
      Loading...
    </div>

    <div v-else>
      <div
        v-if="words.length === 0">
        No words found.
      </div>

      <ul v-else>
        <li
          v-for="word in words"
          :key="word.wordId"
        >
          {{ word.word }}
          -
          {{ word.meaning }}
          -
          {{ word.example }}

          <RouterLink
            :to="`/words/${word.wordId}/edit`"
          >
          Edit
          </RouterLink>

          <button
            @click="deleteWord(word.wordId)"
          >
            Delete
          </button>
        </li>
      </ul>
    </div>

    <button
      :disabled = "
      loading ||
      searchCondition.page === 0"
      @click = "searchCondition.page--; fetchWords()"
    >
      Prev
    </button>

    <span>
      {{ searchCondition.page + 1 }}
      /
      {{ totalPages }}
    </span>

    <button
      :disabled = "
      loading ||
      searchCondition.page >= totalPages - 1"
      @click = "searchCondition.page++; fetchWords()"
    >
      Next
    </button>
  </div>
</template>