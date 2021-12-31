<template>
  <div id="carouselExampleControls" class="carousel slide w-50" data-ride="carousel">
    <div class="carousel-inner" v-for="(image, index) of images" :key="image">
        <div class="carousel-item" v-bind:class="{active : index == 0}">
          <img class="d-block w-100" v-bind:src="image">
        </div>

      <!--      <div class="carousel-item active">-->
<!--        <img class="d-block w-100" v-bind:src="images[0]">-->
<!--      </div>-->
<!--      <div class="carousel-item">-->
<!--        <img class="d-block w-100" v-bind:src="images[1]">-->
<!--      </div>-->
    </div>
    <a class="carousel-control-prev" href="#carouselExampleControls" role="button" data-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#carouselExampleControls" role="button" data-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
  <div class="container">
    <div>
      <div class="d-flex justify-content-between align-items-center flex-wrap">
        <h1 class="h1">{{content.title}}</h1>
        <!-- Button trigger modal -->
        <div>
          <button type="button" class="btn btn-success btn-circle" data-toggle="modal" data-target="#qrModal">
            SHARE
          </button>
        </div>

      </div>

      <!-- Modal -->
      <div class="modal fade" id="qrModal" tabindex="-1" role="dialog" aria-labelledby="qrModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="qrModalLabel">Modal title</h5>
              <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
            <div class="modal-body">
              <img v-bind:src="'https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=http://localhost:8081/auction/' + auctionId">
            </div>
            <div class="modal-footer">
              <ShareNetwork
                  network="vk"
                  url="https://news.vuejs.org/issues/180"
                  title=""
                  description=""
                  quote="qqq"
                  hashtags="auction"
              >
                Share on Facebook
              </ShareNetwork>
              <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
              <button type="button" class="btn btn-primary">Save changes</button>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div>
      <span>Lot number: #{{content.id}}</span>
    </div>
    <div>
      <span>Charity percent: {{content.charityPercent}}%</span>
    </div>
    <div>
      <span>Start price: {{content.startPrice}} MDL</span>
    </div>
    <div>
      <span>Finish price: {{content.finishPrice}} MDL</span>
    </div>
    <div>
      <span>Start date: {{content.startDate}}</span>
    </div>
    <div>
      <span>Finish date: {{content.finishDate}}</span>
    </div>
    <div>
      <span>Description: {{content.description}}</span>
    </div>
  </div>

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
import router from "@/router";

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
      content: "",
      qr_image: "",
      loading: false,
      bids: [],
      successful: false,
      bid: "",
      images: [],
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
          {userId : "17"},
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
    AuctionService.getAuctionById(this.auctionId).then(
        (response) => {
          for (let i = 0; i < response.data.images.length; i++) {
            this.images.push(response.data.images[i]);
          }
          // this.images = response.data.images;
          this.content = response.data;
        },
        (error) => {
          this.content =
              (error.response &&
                  error.response.data &&
                  error.response.data.message) ||
              error.message ||
              error.toString();
          router.push("/error");
        }
    );
    this.connect();
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
.btn-circle {
  border-radius: 1rem;
  padding: 0 0.5rem;
  display: block;
  align-self: flex-start;
}
</style>