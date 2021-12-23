import {createApp} from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import {FontAwesomeIcon} from './plugins/font-awesome';
import VueSocialSharing from 'vue-social-sharing';
import VueConst from 'vue-const';


createApp(App)
    .use(router)
    .use(store)
    .use(VueSocialSharing)
    .use(VueConst)
    .component("font-awesome-icon", FontAwesomeIcon)
    .mount("#app");

export default {
    const: {
        API_URL: "http://localhost:8080",
    }
}