<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light pr-5 pl-5">
    <div class="d-flex">
      <router-link class="img-link" to="/home">
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
        <li id="auctions-link-item" class="nav-item">
          <router-link to="/auctions" class="nav-link text-center">AUCTIONS</router-link>
        </li>
        <li class="nav-item">
          <router-link to="/about-us" class="nav-link text-center">ABOUT US</router-link>
        </li>

        <!-- For Tablet Mobile. if(resizeForDevice) == true -->
        <li v-if="isMobile" class="nav-item">
          <router-link to="/profile/account" v-if="currentUser" class="nav-link text-center">MY PROFILE</router-link>
        </li>
        <li v-if="isMobile" class="nav-item">
          <router-link to="/login" v-on:click="logOut" v-if="currentUser" class="nav-link text-center">LOGOUT
          </router-link>
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
            <router-link to="/profile/account" v-if="currentUser" class="dropdown-item">My profile</router-link>
            <router-link to="/login" v-on:click="logOut" v-if="currentUser" class="dropdown-item">Logout</router-link>
            <router-link to="/register" v-if="!currentUser" class="dropdown-item">Register</router-link>
            <router-link to="/login" v-if="!currentUser" class="dropdown-item">Login</router-link>
          </div>
        </li>

        <li v-if="this.$store.state.auth.status.loggedIn == true" id="notification-item" class="nav-item dropdown d-flex">
          <button class="nav-link text-light" id="navbarDropdown" role="button" data-toggle="dropdown"
                  aria-haspopup="true" aria-expanded="false">
            <icon name="notification-bell"
                  :size="1.5"/>
            <span class="badge-notification-count badge badge-danger">{{ this.unSeenNotification }}</span>
          </button>
          <ul class="dropdown-menu dropdown-menu-notification">
            <li class="head text-light bg-dark">
              <div class="row">
                <div class="col-lg-12 col-sm-12 col-12">
                  <span>New notifications ({{ this.unSeenNotification }})</span>
                  <button @click="seenNotifications" type="button" class="float-right text-light">Mark all as read
                  </button>
                </div>
              </div>
            </li>
            <li v-if="this.notifications.length == 0" class="notification-container">
              <div class="notification-box p-2">
                <h6 class="text-center">You don't have any notifications :(</h6>
              </div>
            </li>
            <li class="notification-container">
              <div class="notification-box border-bottom"
                   :class="{'bg-gray' : notification.seen == false }"
                   v-for="notification in notifications.slice().reverse()"
                   :key="notification.messageId">
                <div class="row pl-2">
                  <div class="col-lg-3 d-flex align-items-center col-sm-3 col-3 text-center">
                    <img :src="notification.image" class="img-fluid">
                  </div>
                  <div class="col-lg-8 col-sm-8 col-8">
<!--                    <strong class="text-info">David John</strong>-->
                    <div class="white-space-nowrap" v-html="notification.message"/>
                    <small class="text-warning">{{ notification.genDate }}</small>
                  </div>
                </div>
              </div>
            </li>
          </ul>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
import {useWindowSize} from 'vue-window-size';
import router from "@/router";
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import Icon from "./Icon";
import properties from "@/properties";

export default {
  name: "Header",
  components: {Icon},
  props: {
    msg: String
  },
  data() {
    return {
      $windowWidth: this.windowWidth,
      isMobile: false,
      notifications: [],
      isConnectedToNotifications: false,
      unSeenNotification: 0,
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
    seenNotifications() {
      let request = [];
      for (let i = 0; i < this.notifications.length; i++) {
        if (this.notifications[i].seen == false) {
          this.notifications[i].seen = true;
          request.push(this.notifications[i].messageId);

          if (this.stompClient && this.stompClient.connected) {
            this.stompClient.send("/app/notification/" + this.currentUser['userDto'].id, JSON.stringify({
              'list': request,
            }));
          }
        }
      }
      this.unSeenNotification = 0;
    },
    logOut() {
      this.$store.dispatch('auth/logout');
      this.notifications = [];
      this.unSeenNotification = 0;
      router.push("/login");
    },
    resizeForDevice() {
      if (this.windowWidth <= 992) {
        this.isMobile = true;
      }
    },
    isAdmin() {
      if (this.currentUser) {
        for (let i = 0; i < this.currentUser['userDto'].userRole.length; i++) {
          if (JSON.stringify(this.currentUser['userDto'].userRole[i]) == '{"name":"USER"}') {
            return true;
          }
        }
      }
      return false;
    },
    getUser() {
      return JSON.parse(localStorage.getItem('user'));
    },
    countUnSeenMessages(notification) {
      if (notification.seen == false) {
        this.unSeenNotification = this.unSeenNotification + 1;
      }
    },
    connect() {
      this.socket = new SockJS(properties.API_URL + "/websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
          {"username": this.getUser().userDto.id},
          () => {
            this.isConnectedToNotifications = true;
            this.stompClient.subscribe("/notification/" + this.getUser().userDto.id,
                tick => {
                  let notification = JSON.parse(tick.body)
                  if (!this.isExistNotification(notification)) {
                    this.notifications.push(notification);
                    this.countUnSeenMessages(notification);
                  }
                });
            this.stompClient.subscribe("/notification/",
                tick => {
                  let notification = JSON.parse(tick.body)
                  if (!this.isExistNotification(notification)) {
                    this.notifications.push(notification);
                    this.countUnSeenMessages(notification);
                  }
                });
          },
          error => {
            this.$notify({
              type: 'error',
              text: error
            })
            this.isConnectedToNotifications = false;
          }
      );
    },
    isExistNotification(notification) {
      for (let i = 0; i < this.notifications.length; i++) {
        if (this.notifications[i].messageId == notification.messageId) {
          return true;
        }
      }
      return false;
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect(
            () => {
            },
            {"username": this.getUser().userDto.id});
      }
      this.connected = false;
    },
    tickleConnection() {
      this.connected ? this.disconnect() : this.connect();
    },
  },
  mounted() {
    this.isAdmin();
    this.resizeForDevice();
  },
  watch: {
    $route(to, from) {
      console.log(to);
      console.log(from);
      if (this.$store.state.auth.status.loggedIn == true &&
          this.isConnectedToNotifications == false) {
        this.connect();
      }
    }
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

button {
  all: unset;
}

.nav-item {
  white-space: nowrap;
}

.badge-notification-count {
  position: absolute;
  top: -5px;
  right: -5px;
}

.dropdown-menu-notification {
  top: 60px;
  right: 0px;
  left: unset;
  width: 460px;
  box-shadow: 0px 5px 7px -1px #c1c1c1;
  padding: 0px;
}

.dropdown-menu-notification:before {
  content: "";
  position: absolute;
  top: -20px;
  right: 12px;
  border: 10px solid #343A40;
  border-color: transparent transparent #343A40 transparent;
}

.head {
  padding: 5px 15px;
  border-radius: 3px 3px 0px 0px;
}

.notification-box {
  padding: 10px 0px;
}

.img-link {
  display: contents;
}

.white-space-nowrap {
  white-space: normal;
}

.bg-gray {
  background-color: #eee;
}

.notification-container {
  max-height: 300px;
  overflow-x: hidden;
  overflow-y: scroll;
}

@media (max-width: 640px) {
  .dropdown-menu-notification {
    top: 50px;
    left: -16px;
    width: 290px;
  }

  .nav .nav-item, .nav .nav-item a {
    padding-left: 0px;
  }
}
</style>