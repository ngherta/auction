<template>
  <div id="app">
    <Header/>
    <div class="container">
      <router-view />
    </div>
    <Footer/>
  </div>
</template>

<script>
import Footer from "./components/Footer";
import Header from "./components/Header";
export default {
  components: {Header, Footer}
};
</script>


<style scoped>
/*@font-face {*/
/*  font-family: "Century Gothic";*/
/*  src: local("Century Gothic"),*/
/*  url("./public/fonts/century-gothic.woff2") format("woff2");*/
/*}*/

@import url('https://fonts.googleapis.com/css2?family=Nanum+Gothic&display=swap');

body {
  font-family: 'Nanum Gothic', sans-serif;
  font-size: 20px;
}
</style>