import {createApp} from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import {FontAwesomeIcon} from './plugins/font-awesome';
import VueSocialSharing from 'vue-social-sharing';
import VueConst from 'vue-const';
import Datepicker from 'vue3-datepicker';
import Notifications from '@kyvg/vue3-notification'



createApp(App)
    .use(router)
    .use(store)
    .use(Notifications)
    .use(VueSocialSharing)
    .use(VueConst)
    .component("Datepicker", Datepicker)
    .component("font-awesome-icon", FontAwesomeIcon)
    .mount("#app");

export default {
    const: {
        API_URL: "http://localhost:8080",
    }
}