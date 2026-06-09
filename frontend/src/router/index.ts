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

const routes = [
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
  }

];



const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;