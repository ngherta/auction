import Vue from 'vue'
import App from './App.vue'
import { store } from './store';
import { router } from './_helpers/router';


Vue.config.productionTip = false
Vue.component('icon', require('./components/Icon').default);

new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app')