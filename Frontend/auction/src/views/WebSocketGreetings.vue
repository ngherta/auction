<template>
  <div>
    <div
        id="main-content"
        class="container">
      <div class="row">
        <div class="col-md-6">
          <form class="form-inline">
            <div class="form-group">
              <label for="connect">WebSocket connection:</label>
              <button
                  id="connect"
                  class="btn btn-default"
                  type="submit"
                  :disabled="connected == true"
                  @click.prevent="connect"
              >Connect
              </button>
              <button
                  id="disconnect"
                  class="btn btn-default"
                  type="submit"
                  :disabled="connected == false"
                  @click.prevent="disconnect"
              >Disconnect
              </button>
            </div>
          </form>
        </div>
        <div class="col-md-6">
          <form class="form-inline">
            <div class="form-group">
              <label for="name">What is your name?</label>
              <input
                  type="text"
                  id="name"
                  class="form-control"
                  v-model="send_message"
                  placeholder="Your name here..."
              >
            </div>
            <button
                id="send"
                class="btn btn-default"
                type="submit"
                @click.prevent="send"
            >Send
            </button>
          </form>
        </div>
      </div>
      <div class="row">
        <div class="col-md-12">
          <table
              id="conversation"
              class="table table-striped"
          >
            <thead>
            <tr>
              <th>Greetings</th>
            </tr>
            </thead>
            <tbody>
            <tr
                v-for="item in received_messages"
                :key="item"
            >
              <td>{{ item }}</td>
            </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";

export default {
  name: "websockets",
  data() {
    return {
      received_messages: [],
      send_message: null,
      connected: false,
    };
  },
  methods: {
    getUser() {
      return JSON.parse(localStorage.getItem('user'));
    },
    connect() {
      this.socket = new SockJS("http://localhost:8080/websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
          {"username": this.getUser().userDto.id},
          () => {
            this.connected = true;
            this.stompClient.subscribe("/notification/" + this.getUser().userDto.id, tick => {
              this.received_messages.push(JSON.parse(tick.body));
            });
            this.stompClient.subscribe("/notification/", tick => {
              this.received_messages.push(JSON.parse(tick.body));
            });
          },
          error => {
            this.$notify = ({
              type: 'error',
              text: error

            });
            this.connected = false;
          }
      );
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
    // this.connect();
  }
};
</script>

<style scoped>
</style>