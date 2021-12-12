import { createWebHistory, createRouter } from "vue-router";
import Home from "./views/Home.vue";
import Login from "./views/Login.vue";
import Register from "./views/Register.vue";
import WebSocketGreetings from "./views/WebSocketGreetings";
import UploadFiles from "./views/UploadFiles";
import ProfileUserTable from "./views/profile/admin/UserTable"
import AuctionPage from "@/views/AuctionPage";
// lazy-loaded
const Profile = () => import("./views/Profile.vue")
const BoardAdmin = () => import("./views/BoardAdmin.vue")
const BoardModerator = () => import("./views/BoardModerator.vue")
const BoardUser = () => import("./views/BoardUser.vue")

const routes = [
  {
    path: "/",
    name: "home",
    component: Home,
  },
  {
    path: "/home",
    component: Home,
  },
  {
    path: "/login",
    component: Login,
  },
  {
    path: "/register",
    component: Register,
  },
  {
    path: "/images",
    component: UploadFiles,
  },
  {
    path: "/websocket",
    component: WebSocketGreetings,
  },
  {
    path: "/profile",
    name: "profile",
    // lazy-loaded
    component: Profile,
  },
  {
    path: "/profile/usertable",
    component: ProfileUserTable,
  },
  {
    path: "/auction/:id",
    component: AuctionPage,
  },
  {
    path: "/admin",
    name: "admin",
    // lazy-loaded
    component: BoardAdmin,
  },
  {
    path: "/mod",
    name: "moderator",
    // lazy-loaded
    component: BoardModerator,
  },
  {
    path: "/user",
    name: "user",
    // lazy-loaded
    component: BoardUser,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

// router.beforeEach((to, from, next) => {
//   const publicPages = ['/login', '/register', '/home'];
//   const authRequired = !publicPages.includes(to.path);
//   const loggedIn = localStorage.getItem('user');
//
//   // trying to access a restricted page + not logged in
//   // redirect to login page
//   if (authRequired && !loggedIn) {
//     next('/login');
//   } else {
//     next();
//   }
// });

export default router;