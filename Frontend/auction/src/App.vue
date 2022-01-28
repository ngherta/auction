<template>
  <div id="app">
    <div class="d-flex flex-column page">
      <Header class="flex-shrink-0"/>
      <div class="content">
        <router-view/>
      </div>
      <Footer class="flex-shrink-0 mt-auto"/>
      <notifications/>
    </div>
  </div>
</template>

<script>
import Footer from "./components/Footer";
import Header from "./components/Header";

export default {
  name: "websockets",
  components: {Header, Footer},
  data() {
    return {
      received_notification: [],
      connected: false,
    };
  },
  methods: {
    getUser() {
      return JSON.parse(localStorage.getItem('user'));
    },
  },
  mounted() {
  }
};
</script>


<style scoped>
/*@font-face {*/
/*  font-family: "Century Gothic";*/
/*  src: local("Century Gothic"),*/
/*  url("./public/fonts/century-gothic.woff2") format("woff2");*/
/*}*/

@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');

p {
  font-size: 1rem;
}

h1 {
  font-size: 2.5rem;
}

h2 {
  font-size: 1.5rem;
}

figcaption {
  font-size: 0.8rem;
}

@media screen and (min-width: 768px) {
  html {
    font-size: 110%;
  }
}

@media screen and (min-width: 1024px) {
  html {
    font-size: 120%;
  }
}

@media screen and (min-width: 1200px) {
  html {
    font-size: 130%;
  }
}

body {
  font-family: 'Nanum Gothic', sans-serif;
  font-size: 20px;
}

content {
  flex: 1 0 auto;
}

.page {
  min-height: 100vh;
}
</style>