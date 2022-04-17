import {createApp} from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import VueSocialSharing from 'vue-social-sharing';
// import Datepicker from 'vue3-datepicker';
import Notifications from '@kyvg/vue3-notification'
// import Datetimepicker from 'vue3-date-time-picker';


createApp(App)
    .use(router)
    .use(store)
    .use(Notifications)
    .use(VueSocialSharing)
    // .component("Datetimepicker", Datetimepicker)
    // .component("Datepicker", Datepicker)
    .mount("#app");

export default {
    const: {
        API_URL: "http://34.140.181.128:8081",
    }
}