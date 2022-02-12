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
import Datetimepicker from 'vue3-date-time-picker';
// import Countdown from 'vue3-flip-countdown'


createApp(App)
    .use(router)
    .use(store)
    .use(Notifications)
    .use(VueSocialSharing)
    .use(VueConst)
    // .use(Countdown).mount('#app')
    .component("Datetimepicker", Datetimepicker)
    .component("Datepicker", Datepicker)
    .component("font-awesome-icon", FontAwesomeIcon)
    .mount("#app");

export default {
    const: {
        API_URL: "http://localhost:8080",
    }
}