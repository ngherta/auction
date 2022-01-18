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
import AuctionTable from "@/views/profile/admin/AuctionTable";
import Chart from "@/views/profile/admin/Chart";
import TopCategories from "@/views/profile/admin/TopCategories";
import Logging from "@/views/profile/admin/Logging";
import CreateNewCategory from "@/views/profile/admin/CreateNewCategory";
import AuctionsPage from '@/views/AuctionsPage';
import ProfilePage from '@/views/profile/ProfilePage';
import AboutUsPage from '@/views/AboutUsPage';

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
  {
    path:"/logging",
    component: Logging,
  },
  {
    path: "/category/create",
    component: CreateNewCategory,
  },
  {
    path: "/about-us",
    component: AboutUsPage,
  },
  {
    path: "/auctions",
    component: AuctionsPage,
  },
  {
    path: "/auctions/:filter",
    component: AuctionsPage,
  },
  {
    path: "/profile/account",
    component: ProfilePage,
  },
  {
    path: "/profile/user-table",
    component: ProfilePage
  },
  {
    path: "/profile/auction-table",
    component: ProfilePage
  },
  {
    path: "/profile/statistic",
    component: ProfilePage
  },
  {
    path: "/profile/my-auctions",
    component: ProfilePage
  },
  {
    path: "/profile/create-category",
    component: ProfilePage
  }
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