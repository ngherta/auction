<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light pr-5 pl-5">
    <div class="d-flex">
      <router-link to="/home">
        <img class="img-logo" src="../assets/logo.jpg" alt="Logo">
      </router-link>
      <button class="navbar-toggler ml-auto" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
              aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
    </div>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link to="/home" class="nav-link text-center">HOME</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/auction/create" class="nav-link text-center">AUCTIONS</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/home" class="nav-link text-center">ABOUT US</router-link>
        </li>

        <!-- For Tablet Mobile. if(resizeForDevice) == true -->
        <li v-if="isMobile" class="nav-item">
          <router-link to="/home" v-if="currentUser" class="nav-link text-center">MY PROFILE</router-link>
        </li>
        <li v-if="isMobile" class="nav-item">
          <router-link to="/login" v-on:click="logOut" v-if="currentUser" class="nav-link text-center">LOGOUT</router-link>
        </li>
        <li v-if="isMobile" class="nav-item">
          <router-link to="/register" v-if="!currentUser" class="nav-link text-center">REGISTER</router-link>
        </li>
        <li v-if="isMobile" class="nav-item">
          <router-link to="/login" v-if="!currentUser" class="nav-link text-center">LOGIN</router-link>
        </li>


        <li v-if="!isMobile" class="nav-item dropdown">
          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownProfile" role="button"
             data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
            MY PROFILE
          </a>
          <div class="dropdown-menu" aria-labelledby="navbarDropdownProfile">
            <router-link to="/home" v-if="currentUser" class="dropdown-item">My profile</router-link>
            <button v-on:click="logOut" v-if="currentUser" class="dropdown-item">Logout</button>
            <router-link to="/register" v-if="!currentUser" class="dropdown-item">Register</router-link>
            <router-link to="/login" v-if="!currentUser" class="dropdown-item">Login</router-link>
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
import {useWindowSize} from 'vue-window-size';
import router from "@/router";

export default {
  name: "Header",
  props: {
    msg: String
  },
  data() {
    return {
      $windowWidth: this.windowWidth,
      isMobile: false,
    };
  },
  setup() {
    const {width, height} = useWindowSize();
    return {
      windowWidth: width,
      windowHeight: height
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
      router.push("/login");
    },
    resizeForDevice() {
      if (this.windowWidth <= 992) {
        console.log("NGH -------------- width:" + this.windowWidth);
        this.isMobile = true;
      }
    },
    isAdmin() {
      // console.log(this.currentUser['userDto'].userRole.includes('name: "USER"'));
      // console.log(JSON.stringify(this.currentUser['userDto'].userRole[0]));
      if (this.currentUser) {
        for (let i = 0; i < this.currentUser['userDto'].userRole.length; i++) {
          console.log('{"name":"USER"}');
          console.log(JSON.stringify(this.currentUser['userDto'].userRole[i]))
          if (JSON.stringify(this.currentUser['userDto'].userRole[i]) == '{"name":"USER"}') {
            return true;
          }
        }
      }
      return false;

    },
  },
  mounted() {
    this.isAdmin();
    this.resizeForDevice();
  }
};
</script>

<style scoped>
@media only screen and (max-width: 576px) {
  .img-logo {
    width: 15%;
  }
}

@media only screen and (min-width: 576px) {
  .img-logo {
    width: 15%;
  }
}

@media only screen and (min-width: 768px) {
  .img-logo {
    width: 15%;
  }
}

@media only screen and (min-width: 992px) {
  .img-logo {
    width: 20%;
  }
}

@media only screen and (min-width: 1200px) {
  .img-logo {
    width: 10%;
  }
}

@media only screen and (min-width: 1400px) {
  .img-logo {
    width: 10%;
  }
}
</style>