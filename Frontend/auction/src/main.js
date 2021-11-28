import Vue from 'vue'
import App from './App.vue'

Vue.config.productionTip = false
Vue.component('icon', require('./components/svg/Icon').default);

new Vue({
  render: h => h(App),
}).$mount('#app')
