import { createWebHistory, createRouter } from "vue-router";
import Home from "./views/Home.vue";
import Login from "./views/auth/LoginPage.vue";
import Register from "./views/auth/RegisterPage.vue";
import WebSocketGreetings from "./views/WebSocketGreetings";
import UploadFiles from "./views/UploadFiles";
import AuctionPage from "@/views/AuctionPage";
import CreateAuctionPage from "@/views/CreateAuctionPage";
import NotFoundPage from "@/views/NotFoundPage";
import ForgotPasswordPage from "@/views/auth/ForgotPasswordPage";
import ChangePasswordPage from "@/views/auth/ChangePasswordPage";
import Actuator from "@/components/Actuator";
import UserTable from "@/views/profile/admin/UserTable";
import AuctionTable from "@/views/profile/admin/AuctionTable";
import Chart from "@/views/profile/admin/Chart";
import TopCategories from "@/views/profile/admin/TopCategories";
// lazy-loaded
const Profile = () => import("./views/Profile.vue")

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
    path: "/password/reset",
    component: ForgotPasswordPage,
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
    path: "/error",
    component: NotFoundPage,
  },
  {
    path: "/auction/:id",
    component: AuctionPage,
  },
  {
    path: "/auction/create",
    component: CreateAuctionPage,
  },
  {
    path: "/user/update/password/:id",
    component: ChangePasswordPage
  },
  {
    path: "/actuator",
    component: Actuator
  },
  {
    path: "/table/user",
    component: UserTable
  },
  {
    path:"/table/auction",
    component: AuctionTable,
  },
  {
    path:"/chart",
    component: Chart,
  },
  {
    path:"/bar",
    component: TopCategories,
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