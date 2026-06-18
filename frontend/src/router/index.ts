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
    component: WordEditView,
  }

];



const router = createRouter({
  history: createWebHistory(),
  routes
});

export default router;