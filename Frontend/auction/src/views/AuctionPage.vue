<template>
  <div class="container">
    <div class="row mt-5">
      <div v-if="showTest" id="carouselExampleControls" class="col carousel slide w-25 border" data-ride="carousel">
        <div class="carousel-inner" style="height: 500px">
          <div v-for="(image, index) of images"
               :key="image"
               class="carousel-item "
               v-bind:class="{active : index == 0}">
            <img class="d-flex m-auto img-responsive"
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
      <div class="col d-flex flex-column justify-content-between pb-5 pl-5 pr-5">
        <div>
          <div class="d-flex align-items-center">
            <h1 class="h1">{{ content.title }}</h1>
            <div class="ml-auto">
              <button type="button" class="btn btn-success btn-circle" data-toggle="modal" data-target="#qrModal">
                SHARE
              </button>
            </div>
            <div class="ml-2">
              <button type="button"
                      data-toggle="modal"
                      data-target="#complaintModal"
                      class="btn btn-danger btn-circle">COMPLAINT
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
                  <button type="button"
                          class="close close-custom"
                          data-dismiss="modal"
                          aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body m-auto">
                  <img
                      v-bind:src="'https://api.qrserver.com/v1/create-qr-code/?size=150x150&data=http://localhost:8081/auction/' + auctionId">
                </div>
                <div class="modal-footer">
                  <ShareNetwork
                      network="facebook"
                      url="https://news.vuejs.org/issues/180"
                      title=""
                      description=""
                      quote="qqq"
                      hashtags="auction"
                  >
                    Share on Facebook
                  </ShareNetwork>
                  <ShareNetwork
                      network="vk"
                      url="https://news.vuejs.org/issues/180"
                      title=""
                      description=""
                      quote="qqq"
                      hashtags="auction"
                  >
                    Share on VK
                  </ShareNetwork>
                  <!--                  <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>-->
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
            <!--            <span v-html="content.description"/>-->
          </span>
          <!-- Button trigger modal -->
          <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#descriptionModalLong">
            open
          </button>

          <!-- Modal -->
          <div class="modal fade" id="descriptionModalLong" tabindex="-1" role="dialog"
               aria-labelledby="descriptionModalLongTitle" aria-hidden="true">
            <div class="modal-dialog" role="document">
              <div class="modal-content">
                <div class="modal-header">
                  <h5 class="modal-title" id="descriptionModalLongTitle">Description</h5>
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
                <div class="modal-body" v-html="content.description"/>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="row mt-5 mb-5">
      <div class="col-6 p-3 border">
        <button type="button"
                data-toggle="modal"
                data-target="#bettingModalLong"
                class="btn expand-betting-button">
          <icon :name="'expand'"/>
        </button>
        <betting-room :bids="bids"
                      :auction="content"
                      :stomp-client="stompClient"
                      ref="betting-room"
                      @refreshAuction="getData"/>
        <!-- Modal -->
        <div class="modal fade" id="bettingModalLong" tabindex="-1" role="dialog"
             aria-labelledby="bettingModalLongTitle" aria-hidden="true">
          <div class="modal-dialog modal-betting" role="document">
            <div class="modal-content">
              <div class="modal-header">
                <!--                <h5 class="modal-title" id="bettingModalLongTitle">BETTING!</h5>-->
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                  <span aria-hidden="true">&times;</span>
                </button>
              </div>
              <div class="modal-body">
                <betting-room :bids="bids"
                              :auction="content"
                              :stomp-client="stompClient"/>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div class="col-6 border mt-auto pb-4 pr-0">
        <div class="chat-box d-flex flex-column pt-3 pb-3" id="chat-box">
          <div v-for="message in chatMessages"
               class=""
               :class="{'ml-auto' : this.isMessageFromCurrentUser(message.senderId) == true}"
               :key="message">
            <div class="chat-name">{{ message.senderFirstName + ' ' + message.senderLastName }}</div>
            <div class="chat-date">{{ message.genDate }}</div>
            <div class="chat-message pl-2 pr-2">{{ message.message }}</div>
          </div>
        </div>
        <div class="">
          <Form class="d-flex pr-3 pl-3" @submit="handleMessageSending">
            <Field name="message"
                   id="message"
                   type="text"
                   class="form-control col-10"/>
            <button class="btn btn-primary btn-block col-2" type="submit">SEND</button>
          </Form>
        </div>
      </div>
    </div>
  </div>

  <!-- Modal -->
  <div class="modal fade"
       id="complaintModal"
       tabindex="-1"
       role="dialog"
       aria-labelledby="complaintModalLongTitle"
       aria-hidden="true">
    <div class="modal-dialog modal-complaint" role="document">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="complaintModalLongTitle">COMPLAINT</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <Form @submit="handleComplaint">
          <div class="modal-body">
            <div v-if="!complaintSuccessful">
              <div class="mb-3">
                <label for="modalMessage">MESSAGE:</label>
                <textarea name="modalMessage" class="form-control" v-model="complaintMessage" rows="3"/>
              </div>
            </div>
            <div v-else-if="complaintSuccessful">
              <h4 class="alert text-center alert-success" role="alert">
                Your complaint <b class="alert-link">#{{ this.complaintId }}</b> has been accepted.
              </h4>
            </div>
          </div>
          <div class="modal-footer">
            <button v-if="complaintSuccessful" type="button" class="btn btn-secondary" data-dismiss="modal">Close
            </button>
            <div v-else-if="!complaintSuccessful" class="">
              <button class="btn btn-primary btn-block" :disabled="complaintLoading">
              <span
                  v-show="complaintLoading"
                  class="spinner-border spinner-border-sm"
              ></span>
                SEND
              </button>
            </div>
          </div>
        </Form>
      </div>
    </div>
  </div>
</template>

<script>
import SockJS from "sockjs-client";
import Stomp from "webstomp-client";
import BettingService from "../services/betting.service"
import AuctionService from "../services/auction.service"
import ComplaintService from "../services/complaint.service"
import {Field, Form} from "vee-validate";
import * as yup from "yup";
import router from "@/router";
import ChatService from "../services/chat.service";
import BettingRoom from "../components/BettingRoom";
import Icon from "../components/Icon";


export default {
  name: "AuctionPage",
  components: {
    Icon,
    Form,
    Field,
    BettingRoom,
  },
  data() {
    const schema = yup.object().shape({
      bid: yup
          .number()
          .required("Bid is required!")
    });
    return {
      showTest: false,
      stompClient: null,
      received_messages: [],
      send_message: null,
      socket: null,
      connected: false,
      auctionId: this.$route.params.id,
      content: {
        id: "",
        statusType: "",
        description: "",
        title: "",
        auctionType: "",
        startPrice: "",
        finishPrice: "",
        user: "",
        startDate: "",
        finishDate: "",
        genDate: "",
        charityPercent: "",
        images: [],
        lastBid: null,
      },
      qr_image: "",
      loading: false,
      bids: [],
      complaintMessage: null,
      complaintSuccessful: false,
      complaintId: null,
      complaintLoading: false,
      successful: false,
      bid: "",
      images: [],
      chatMessages: [],
      schema,
      betInput: null,
      isWrongBet: null
    };
  },
  methods: {
    isMessageFromCurrentUser(senderId) {
      if (this.$store.state.auth.user) {
        if (this.$store.state.auth.user.userDto.id == senderId) {
          return true;
        } else {
          return false;
        }
      } else {
        return false;
      }
    },
    getUser() {
      return JSON.parse(localStorage.getItem('user'));
    },
    handleComplaint() {
      this.complaintLoading = true;
      this.complaintSuccessful = false;
      const data = {
        userId: this.$store.state.auth.user.userDto.id,
        auctionEventId: this.auctionId,
        message: this.complaintMessage
      }
      ComplaintService.sendComplaint(data).then(
          (response) => {
            this.complaintId = response.data.id;
            this.complaintSuccessful = true;
          },
          error => {
            this.$notify({
              type: 'error',
              text: error.response.data.errorMessage
            });
          }
      );
      this.complaintLoading = false;
    },
    checkBetForChangeColor() {
      if (this.bids.length !== 0) {
        if (this.betInput <= this.bids.findLast(x => x).bid * 1.05) {
          this.isWrongBet = true;
        } else {
          this.isWrongBet = false;
        }
      } else {
        if (this.betInput <= this.content.startPrice * 1.05) {
          this.isWrongBet = true;
        } else {
          this.isWrongBet = false;
        }
      }
    },
    getAllMessagesByAuctionId() {
      ChatService.getMessagesByAuctionId(this.auctionId).then(
          (response) => {
            console.log(response);
            this.chatMessages = response.data;
          },
          (error) => {
            console.log(error);
          }
      );
    },
    handleMessageSending() {
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send("/app/chat/auction/" + this.auctionId, JSON.stringify({
          'senderId': this.$store.state.auth.user.userDto.id,
          'message': 'Hello!'
        }));
        let div = document.getElementById('chat-box');
        div.scroll({top: div.scrollHeight, behavior: 'smooth'});
      }
    },
    handleBet() {
      this.loading = true;
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send("/app/betting/" + this.auctionId, JSON.stringify({
          'auctionId': String(this.auctionId),
          'userId': this.getUser().userDto.id,
          'bet': this.betInput
        }));
      }
      this.loading = false;
    },
    connect() {
      this.socket = new SockJS("http://localhost:8080/websocket");
      this.stompClient = Stomp.over(this.socket);
      this.stompClient.connect(
          () => {
          },
          () => {
            this.connected = true;
            this.stompClient.subscribe("/betting/" + this.auctionId,
                tick => {
                  this.bids.push(JSON.parse(tick.body));
                });

            this.stompClient.subscribe("/chat/auction/" + this.auctionId,
                tick => {
                  this.chatMessages.push(JSON.parse(tick.body));
                });

            this.stompClient.subscribe("/user/betting/errors",
                tick => {
                  this.$notify({
                    type: 'error',
                    text: tick.body
                  })
                });
          },
          error => {
            this.$notify({
              type: 'error',
              text: error
            })
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
    getData() {
      AuctionService.getAuctionById(this.auctionId).then(
          (response) => {
            for (let i = 0; i < response.data.images.length; i++) {
              this.images.push(response.data.images[i]);
            }
            this.content = response.data;
            if (this.content.statusType == 'EXPECTATION') {
              this.$refs["betting-room"].setStartDate(response.data.startDate);
            }
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
    }
  },
  mounted() {
    this.getData();
    this.connect();
    BettingService.getBidsForByAuction(this.auctionId).then(
        (response) => {
          this.bids = response.data
        },
        (error) => {
          this.$notify({
            type: 'error',
            text: error.message
          });
        }
    )
    this.getAllMessagesByAuctionId();
  }
};
</script>

<style scoped>
@media only screen and (min-width: 576px) {
  .modal-dialog {
    max-width: 500px;
    /*margin: 1.75rem auto;*/
  }
}

@media only screen and (min-width: 992px) {
  .modal-dialog {
    max-width: 600px;
  }
}

@media only screen and (min-width: 1200px) {
  .modal-dialog {
    max-width: 700px;
  }

  .modal-dialog.modal-betting {
    max-width: 90%;
  }
}

@media only screen and (min-width: 1400px) {
  .modal-dialog {
    max-width: 800px;
  }

}

@media only screen and (min-width: 1600px) {
  .modal-dialog {
    max-width: 900px;
  }

  .modal-dialog.modal-betting {
    max-width: 80%;
  }
}

.btn-circle {
  border-radius: 1rem;
  padding: 0 0.5rem;
  display: block;
  align-self: flex-start;
}

.img-responsive {
  object-fit: cover;
  width: auto;
  height: 500px;
}

.expand-betting-button {
  position: absolute;
  right: 5px;
  top: 5px;
}

.chat-box {
  height: 394px;
  overflow-x: hidden;
  overflow-y: scroll;
}

.modal-header .close-custom {
  margin: -1rem -1rem -1rem 0;
}

.chat-message {
  display: inline-flex;
  background-color: #9c9c9c;
  border-radius: 48%;
  border-style: solid;
}
</style>