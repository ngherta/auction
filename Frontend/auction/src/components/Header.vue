<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light pr-5 pl-5">
    <div>
      <router-link to="/home">
        <img class="img-logo" src="../assets/logo.jpg" alt="Logo">
      </router-link>
      <button class="navbar-toggler ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
    </div>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/home" class="nav-link text-center">HOME</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/home" class="nav-link text-center">AUCTIONS</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/home" class="nav-link text-center">ABOUT US</router-link>
        </li>

        <!-- For Tablet Mobile. if(resizeForDevice) === true -->
        <li v-if="!resizeForDevice" class="nav-item">
          <router-link to="/home" v-if="currentUser" class="nav-link text-center">My profile</router-link>
        </li>
        <li v-if="!resizeForDevice" class="nav-item">
          <button v-on:click="logOut" v-if="currentUser" class="nav-link text-center">Logout</button>
        </li>
        <li v-if="!resizeForDevice" class="nav-item">
          <router-link to="/register" v-if="!currentUser" class="nav-link text-center">Register</router-link>
        </li>
        <li v-if="!resizeForDevice" class="nav-item">
          <router-link to="/login" v-if="!currentUser" class="nav-link text-center">Login</router-link>
        </li>


        <li v-if="$windowWidth > 992" class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="asdffff" id="navbarDropdownProfile" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            MY PROFILE
          </a>
          <div class="dropdown-menu" aria-labelledby="navbarDropdownProfile">
            <router-link to="/home" v-if="currentUser" class="dropdown-item">My profile</router-link>
            <button v-on:click="logOut" v-if="currentUser" class="dropdown-item">Logout</button>
            <router-link to="/register" v-if="!currentUser" class="dropdown-item">Register</router-link>
            <router-link to="/home" v-if="!currentUser" class="dropdown-item">Login</router-link>
            <div class="dropdown-divider"></div>
            <router-link to="/home" class="dropdown-item">Something else here</router-link>
          </div>
        </li>
        <!--        <li class="nav-item">-->
<!--          <a class="nav-link disabled" href="#">Disabled</a>-->
<!--        </li>-->
      </ul>
    </div>
  </nav>
</template>

<script>
import { useWindowSize } from 'vue-window-size';

export default {
  setup() {
    const { width, height } = useWindowSize();
    return {
      windowWidth: width,
      windowHeight : height
    };
  },
  name: "Header",
  data() {
    return {
      $windowWidth: this.windowWidth,
    };
  },


  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    showAdminItem() {
      if (this.currentUser && this.currentUser['roles']) {
        return this.currentUser['roles'].includes('ROLE_ADMIN');
      }

      return false;
    }
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    },
    resizeForDevice() {
      if (this.windowWidth <= 992 ) {
        return true;
      }
      else {
        return false;
      }
    }
  }
};
</script>

<style scoped>
.img-logo {
  width: 10%;
}
</style>