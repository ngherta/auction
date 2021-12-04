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
