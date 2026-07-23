import {
  createRouter,
  createWebHistory
} from "vue-router";

import LoginView
  from "../views/LoginView.vue";
import WordListView
  from "../views/WordListView.vue";
import WordCreateView
  from "../views/WordCreateView.vue";
import WordEditView
  from "../views/WordEditView.vue";
import QuizView 
  from "../views/QuizView.vue";
import ResultView 
  from "../views/ResultView.vue";

const routes = [
  {
    path: "/",
    redirect: "/login"
  },
  {
    path: "/login",
    component: LoginView
  },
  {
    path: "/words",
    component: WordListView
  },
  {
    path: "/words/create",
    component: WordCreateView
  },
  {
    path: "/words/:id/edit",
    component: WordEditView
  },
  {
    path: "/quiz",
    component:QuizView
  },
  {
    path: "/result/:sessionId",
    component: ResultView
  }

];



const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;