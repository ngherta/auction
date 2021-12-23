<template>
  <div class="d-flex justify-content-center">
    <router-link class="logo-link" to="/home">
      <img class="img-logo img-fluid" src="../assets/logo.jpg" alt="Logo">
    </router-link>
  </div>
</template>

<script>
export default {
  name: "Main_Logo"
}
</script>

<style scoped>
@media only screen and (min-width: 576px) {
  .img-logo {
    width: 80%;
  }
}

@media only screen and (min-width: 768px) {
  .img-logo {
    width: 60%;
  }
}

@media only screen and (min-width: 992px) {
  .img-logo {
    width: 40%;
  }
}

@media only screen and (min-width: 1200px) {
  .img-logo {
    width: 30%;
  }
}

@media only screen and (min-width: 1400px) {
  .img-logo {
    width: 30%;
  }
}

.logo-link {
  display: contents;
}

/*.img-logo {*/
/*  width: 20%;*/
/*}*/
</style>