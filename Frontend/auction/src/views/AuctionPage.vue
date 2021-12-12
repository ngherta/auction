<template>
  <div class="container">
    <Form @submit="handleBet" :validation-schema="schema">
      <div>
        <div class="mb-3">
          <Field name="bid" id="bid" type="number" class="form-control" />
          <ErrorMessage name="bid" class="error-feedback" />
        </div>

        <div class="">
          <button class="btn btn-primary btn-block" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
            BID
          </button>
        </div>
      </div>
    </Form>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import BettingService from "../services/betting.service"
import AuctionService from "../services/auction.service"
import {ErrorMessage, Field, Form} from "vee-validate";
import * as yup from "yup";

export default {
  name: "AuctionPage",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      bid: yup
          .number()
          .required("Bid is required!")
    });
    return {
      received_messages: [],
      send_message: null,
      connected: false,
      auctionId: this.$route.params.id,
      content: null,
      loading: false,
      bids: [],
      successful: false,
      bid: "",
      schema,
    };
  },
  methods: {
    getUser() {
      return JSON.parse(localStorage.getItem('user'));
    },
    handleBet(bet) {
      console.log(bet.bid);
      this.loading = true;

      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send("/app/betting/" + this.auctionId,  JSON.stringify({'auctionId': String(this.auctionId), 'userId': this.getUser().userDto.id, 'bet' : bet.bid}));
      }
      this.loading = false;
    },
    connect() {
      this.socket = new SockJS("http://localhost:8080/websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
          {},
          frame => {
            this.connected = true;
            console.log("NGH" + frame);
            // this.received_messages = BettingService.getBidsForByAuction(1006);

            console.log("qq" + this.received_messages);
            console.log(this.auctionId);
            this.stompClient.subscribe("/betting/" + this.auctionId, tick => {
              console.log("asdasdasd")
              console.log("tick.body = " + tick.body);
              this.received_messages.push(JSON.parse(tick.body));
              console.log(tick)
            });

            this.stompClient.subscribe("/user/betting/errors", tick => {
              console.log("ERROR connected")
              console.log("tick.body = " + tick.body);
              this.received_messages.push(JSON.parse(tick.body));
              console.log(tick)
            });
          },
          error => {
            console.log(error);
            this.connected = false;
          }
      );
    },
    disconnect() {
      if (this.stompClient) {
        this.stompClient.disconnect();
      }
      this.connected = false;
    },
    tickleConnection() {
      this.connected ? this.disconnect() : this.connect();
    },
  },
  mounted() {
    this.connect();
    AuctionService.getAuctionById(this.auctionId).then(
        (response) => {
          this.content = response.data;
        },
        (error) => {
          this.content =
              (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
              error.message ||
              error.toString();
        }
    );
    BettingService.getBidsForByAuction(this.auctionId).then(
        (response) => {
          this.bids = response.data
        },
        (error) => {
          this.bids =
              (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
              error.message ||
              error.toString();
        }
    )
  }
};
</script>

<style scoped>

</style>