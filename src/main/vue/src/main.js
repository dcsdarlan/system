import Vue from 'vue';
import App from './App.vue';
import Vuetify from 'vuetify';
import VueResource from 'vue-resource';
import VueRouter from 'vue-router';
import FlagIcon from 'vue-flag-icon';
import CONFIG from './config';
/* Helpers */
import {routes} from './routes';

/* Modules */
Vue.use(CONFIG);
Vue.use(Vuetify, {
  theme: {
    primary: "#3f8ab7",
    secondary: "#3f8ab7",
    accent: "#3f8ab7",
    info: "#007bff",
    error: "#dc3545",
    success: "#28a745",
    warning: "#ffaa00"
  }
});

Vue.use(VueResource);
Vue.use(VueRouter);
Vue.use(FlagIcon);

const router = new VueRouter({
  routes : routes,
  mode: 'history'
});
router.beforeEach((to, from, next) => {
  const hasTitle = to.matched.slice().reverse().find(r => r.meta && r.meta.title);
  if(hasTitle) {
    document.title = to.meta.title;
  }
  const hasPath = to.matched.slice().reverse().find(r => r.path && true);
  if(hasPath) {
    //
  }
  next();
});

new Vue({
  el: '#app',
  router,
  render: h => h(App)
});


