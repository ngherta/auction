<template>

  <div class="container">
    <div class="row mt-5">
      <div v-if="showTest" id="carouselExampleControls" class="col carousel slide w-25" data-ride="carousel">
        <div class="carousel-inner" style="height: 500px">
          <div v-for="(image, index) of images"
               :key="image"
               class="carousel-item"
               v-bind:class="{active : index == 0}">
            <img class="d-block img-responsive"
                 v-bind:src="image">
          </div>

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
      <div class="col d-flex flex-column justify-content-between">
        <div>
          <div class="d-flex justify-content-between align-items-center flex-wrap">
            <h1 class="h1">{{ content.title }}</h1>
            <!-- Button trigger modal -->
            <div>
              <button type="button" class="btn btn-success btn-circle" data-toggle="modal" data-target="#qrModal">
                SHARE
              </button>
            </div>

          </div>

          <!-- Modal -->
          <div class="modal fade" id="qrModal" tabindex="-1" role="dialog" aria-labelledby="qrModalLabel"
               aria-hidden="true">
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title m-auto" id="qrModalLabel">Share this link</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body m-auto">
                  <img
                      v-bind:src="'https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=http://localhost:8081/auction/' + auctionId">
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
                </div>
              </div>
            </div>
          </div>
        </div>
        <div>
          <span>
            <b>
              Lot number:
            </b> #{{ content.id }}</span>
        </div>
        <div>
          <span>
            <b>
              Charity percent:
            </b> {{ content.charityPercent }}%</span>
        </div>
        <div>
          <span>
            <b>Start price:</b>
            {{ content.startPrice }} USD</span>
        </div>
        <div>
          <span>
            <b>Finish price:</b>
            {{ content.finishPrice }} USD</span>
        </div>
        <div>
          <span>
            <b>Start date:</b>
            {{ content.startDate }}</span>
        </div>
        <div>
          <span>
            <b>Finish date:</b>
            {{ content.finishDate }}</span>
        </div>
        <div>
          <span>
            <b>Description:</b>
            {{ content.description }}</span>
        </div>
      </div>
    </div>

    <div class="w-50 p-3 border mt-5">
      <h3 class="text-center mb-4">MAKE A BID</h3>
      <div>
        <Form @submit="handleBet" :validation-schema="schema">
          <div class="row">
            <div class="col input-group">
              <Field name="bid" id="bid" type="number" class="form-control"/>
              <div class="input-group-append">
                <div class="input-group-text">$</div>
              </div>
              <!--          <ErrorMessage name="bid" class="error-feedback" />-->
            </div>

            <div class="d-flex col" style="flex-basis: min-content">
              <button class="btn btn-primary btn-block mt-0 mr-3" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
                BID
              </button>
              <button class="btn btn-primary btn-block mt-0" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
                BUY NOW
              </button>
            </div>
          </div>
        </Form>
        <div class="custom-table mt-3">
          <table class="table table-bordered table-striped">
            <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Name</th>
              <th scope="col">Date</th>
              <th scope="col">Bid</th>
            </tr>
            </thead>
            <tbody class="">
            <tr v-for="(bid,index) in bids.slice().reverse()"
                :key="index"
                class="border-top border-bottom"
                v-bind:class="{
                  firstTableRow : index == 0
                }">
              <td>{{ bids.length - index }}</td>
              <td>{{ bid.user.firstName + ' ' + bid.user.lastName }}</td>
              <td>{{ bid.genDate }}</td>
              <th scope="row">{{ bid.bid }} USD</th>
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
import BettingService from "../services/betting.service"
import AuctionService from "../services/auction.service"
import {Field, Form} from "vee-validate";
import * as yup from "yup";
import router from "@/router";


export default {
  name: "AuctionPage",
  components: {
    Form,
    Field,
    // ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      bid: yup
          .number()
          .required("Bid is required!")
    });
    return {
      showTest: false,
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
        this.stompClient.send("/app/betting/" + this.auctionId, JSON.stringify({
          'auctionId': String(this.auctionId),
          'userId': this.getUser().userDto.id,
          'bet': bet.bid
        }));
      }
      this.loading = false;
    },
    connect() {
      this.socket = new SockJS("http://localhost:8080/websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
          {userId: "17"},
          frame => {
            this.connected = true;
            console.log(frame);
            this.stompClient.subscribe("/betting/" + this.auctionId, tick => {
              this.bids.push(JSON.parse(tick.body));
              console.log(tick.body)
            });

            this.stompClient.subscribe("/user/betting/errors", tick => {
              console.log("tick.body = " + tick.body);
              this.$notify({
                type: 'error',
                text: tick.body.replaceAll('"', ''),
              })
            });
          },
          error => {
            this.$notify({
              text: error.message,
              type: 'error'
            });
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
          this.showTest = true;
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
          this.$notify(error.message);
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

.custom-table {
  position: relative;
  height: 200px;
  overflow: auto;
  display: block;
}

.firstTableRow {
  background-color: #90EE90 !important;
}

.img-responsive {
  object-fit: cover;
  width: auto;
  height: 500px;
}
</style>