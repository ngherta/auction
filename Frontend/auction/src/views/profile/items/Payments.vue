<template>
  <div class="border container-lg p-5 mb-5">
    <Icon name="payments" size="3"/>
    <div class="d-flex justify-content-between">
      <h2 class="h2 mt-2">Payments</h2>
      <h5>Balance: {{ this.userBalance }} $
        <button v-if="userBalance > 0"
                @click="modalType = 'WITHDRAW_MONEY'"
                type="button"
                class="btn"
                data-toggle="modal"
                data-target="#addressModal"><Icon class="m-auto" size="1.3" name="withdraw-money"></Icon>
        </button>
      </h5>
    </div>
    <div class="d-flex justify-content-end mt-3 mb-3">
      <button class="badge badge-pill filter-button"
              @click="changeGetBy('SEND')"
              :class="{'badge-light' : this.getBy != 'SEND',
              'badge-success' : this.getBy == 'SEND'}">SEND
      </button>
      <button class="badge badge-pill ml-2 filter-button"
              @click="changeGetBy('RECEIVE')"
              :class="{'badge-light' : this.getBy != 'RECEIVE',
              'badge-success' : this.getBy == 'RECEIVE'}">RECEIVE
      </button>
    </div>
    <div v-for="(item, index) in data" :key="index" class="card mw-100 mb-4">
      <div class="card-header">
        Price: <b>{{ item.price }} $</b>
      </div>
      <div class="card-body">
        <h5 class="card-title">
          <router-link :to="'/auction/' + item.auctionEvent.id">
            {{ item.auctionEvent.title }} (#{{ item.auctionEvent.id }})
          </router-link>
        </h5>
        <div class="step-container">
          <ul class="progressbar list-unstyled">
            <li :class="{'active-item' : ['CREATED', 'NEED_ADDRESS', 'DELIVERY_START', 'DELIVERY_PROCESSING', 'DELIVERY_FINISHED'].includes(item.status)}">
              Won
            </li>
            <li :class="{'active-item' : ['NEED_ADDRESS', 'DELIVERY_START', 'DELIVERY_PROCESSING', 'DELIVERY_FINISHED'].includes(item.status)}">
              Paid
            </li>
            <li :class="{'active-item' : ['DELIVERY_START', 'DELIVERY_PROCESSING', 'DELIVERY_FINISHED'].includes(item.status)}">
              Wait shipping
            </li>
            <li :class="{'active-item' : ['DELIVERY_PROCESSING', 'DELIVERY_FINISHED'].includes(item.status)}">Delivery
              progress
            </li>
            <li :class="{'active-item' : ['DELIVERY_FINISHED'].includes(item.status)}">Delivered</li>
          </ul>
        </div>
        <p class="card-text mt-10"
           v-if="'DELIVERY_PROCESSING' == item.status && item.trackNumber != null">
          Track number:
          <b>{{ item.trackNumber }}</b>
        </p>
        <p class="card-text"
           :class="{'mt-10' : 'DELIVERY_PROCESSING' != item.status}">
          <small>Auction was finished:
            <b>{{ item.genDate }}</b>
          </small>
        </p>
        <button @click="currentWinnerId = item.auctionEvent.id; currentWinner = item; modalType = 'ADD_ADDRESS'"
                v-if="item.needAddress == true && getBy == 'SEND'"
                type="button"
                class="btn btn-primary mr-3"
                data-toggle="modal"
                data-target="#addressModal">Confirm Address
        </button>
        <a :href="item.paymentOrder.link"
           v-if="item.status == 'CREATED' && getBy == 'SEND'"
           target="_blank"
           class="btn btn-primary mr-3">Pay</a>
        <button @click="currentWinnerId = item.auctionEvent.id; currentWinner = item; modalType = 'ADD_TRACK_NUMBER'"
                v-if="item.status == 'DELIVERY_START' && getBy == 'RECEIVE'"
                type="button"
                class="btn btn-primary mr-3"
                data-toggle="modal"
                data-target="#addressModal">Add track-number
        </button>
        <button @click="currentWinnerId = item.auctionEvent.id;
        currentWinner = item; handleDelivered()"

                v-if="item.status == 'DELIVERY_PROCESSING' && getBy == 'SEND'"
                type="button"
                class="btn btn-primary mr-3"
                :disabled="buttonLoading">
              <span
                  v-show="buttonLoading"
                  class="spinner-border spinner-border-sm"></span>Delivered
        </button>
      </div>
    </div>
    <span
        v-show="loading"
        class="spinner-border m-auto spinner-border-sm"
    ></span>
    <div v-if="data.length == 0" class="alert alert-dark" role="alert">
      Data not found
    </div>
    <nav v-if="loading == false && data.length != 0"
         aria-label="Page navigation example"
         class="d-flex justify-content-center mt-5">
      <ul class="pagination ">
        <li class="page-item list-unstyled">
          <button class="page-link"
                  type="button"
                  :disabled="countOfPages == 1"
                  @click="changePage(this.page-1)"
                  aria-label="Previous">
            <span aria-hidden="true">&laquo;</span>
          </button>
        </li>
        <li v-for="index in countOfPages" :key="index"
            :class="{active: this.page == index,
                  'd-none': !isPageForRender(index)}"
            :aria-current="{page: this.page == index}"
            class="page-item">
          <button @click="changePage(index)"
                  class="page-link"
                  :disabled="countOfPages == 1"
                  type="button">{{ index }}
          </button>
        </li>
        <li class="page-item">
          <button class="page-link"
                  type="button"
                  @click="changePage(this.page+1)"
                  :disabled="countOfPages == 1"
                  aria-label="Next">
            <span aria-hidden="true">&raquo;</span>
          </button>
        </li>
      </ul>
    </nav>
  </div>

  <div class="modal fade" id="addressModal" tabindex="-1" aria-labelledby="addressModalLabel" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="addressModalLabel">New message</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Close">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <div class="modal-body">
          <form v-if="modalType == 'ADD_ADDRESS'" @submit="submitAddress">
            <div class="form-group">
              <label for="addCountry" class="col-form-label">Country:</label>
              <input v-model="country" name="country" type="text" class="form-control" id="addCountry"/>
            </div>
            <div class="form-group">
              <label for="addCity" class="col-form-label">City:</label>
              <input v-model="city" name="city" type="text" class="form-control" id="addCity"/>
            </div>
            <div class="form-group">
              <label for="addAddress" class="col-form-label">Address:</label>
              <input v-model="address" name="address" type="text" class="form-control" id="addAddress"/>
            </div>
            <div class="form-check">
              <input class="form-check-input" type="checkbox" v-model="needToSaveAsDefault" id="needToSaveAsDefault">
              <label class="form-check-label" for="needToSaveAsDefault">
                Save as default address
              </label>
            </div>
          </form>
          <form v-if="modalType == 'ADD_TRACK_NUMBER'" @submit="submitTrackNumber">
            <div class="form-group">
              <h3 class="h3">Delivery address</h3>
              <p><strong>Name: </strong>{{currentWinner.auctionEvent.user.firstName + ' ' + currentWinner.auctionEvent.user.lastName}}</p>
              <p><strong>Country: </strong>{{currentWinner.country}}</p>
              <p><strong>City: </strong>{{currentWinner.city}}</p>
              <p><strong>Street: </strong>{{currentWinner.address}}</p>
              <p class="border-bottom"></p>
              <p>Add track number and change status to <b>DELIVERY-PROCESSING</b></p>
              <label for="trackNumber" class="col-form-label">Track number:</label>
              <input v-model="trackNumber" name="trackNumber" type="text" class="form-control" id="trackNumber"/>
            </div>
          </form>
          <form v-if="modalType == 'WITHDRAW_MONEY'" @submit="submitWithdrawMoneyRequest">
            <h3 class="h3">Withdraw money</h3>
            <p><strong>Balance: </strong>{{this.userBalance}}</p>
            <div class="form-check">
              <label for="userName" class="col-form-label">Name: </label>
              <input v-model="nameRequest" name="userNameInput" placeholder="John Smith" type="text" class="form-control" id="userName"/>
            </div>
            <div class="form-check">
              <label for="cardNumber" class="col-form-label">Card number: </label>
              <input v-model="cardRequest" name="cardNumberInput" placeholder="1234567812345678" type="text" class="form-control" id="cardNumber"/>
            </div>
            <div class="form-check">
              <label for="expiredDate" class="col-form-label">Expire date: </label>
              <input v-model="expireDateRequest" name="expiredDate" placeholder="10/2024" type="text" class="form-control" id="expiredDate"/>
            </div>
            <div class="form-check">
              <label for="withDrawMoneyInput" class="col-form-label">Amount: </label>
              <input v-model="amountRequest" name="withDrawMoneyInput" type="number" class="form-control" id="withDrawMoneyInput"/>
            </div>
          </form>
        </div>
        <div class="modal-footer">
          <button v-if="this.$store.state.auth.user.userDto.hasDefaultAddress == true && modalType == 'ADD_ADDRESS'"
                  @click="useDefaultAddress"
                  type="button"
                  class="btn btn-secondary"
                  data-dismiss="modal">Use default address
          </button>
          <button type="submit"
                  v-if="modalType == 'ADD_ADDRESS'"
                  @click="submitAddress"
                  class="btn btn-primary"
                  data-dismiss="modal">Add address
          </button>
          <button @click="submitWithdrawMoneyRequest"
                  type="button"
                  v-if="modalType == 'WITHDRAW_MONEY'"
                  class="btn btn-primary"
                  data-dismiss="modal">Submit
          </button>
          <button type="submit"
                  v-if="modalType == 'ADD_TRACK_NUMBER'"
                  @click="submitTrackNumber"
                  class="btn btn-primary"
                  data-dismiss="modal">Add track-number
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Icon from '../../../components/Icon';
import WinnerService from "@/services/winner.service";
import UserService from "@/services/user.service";

export default {
  name: "Payments",
  components: {
    Icon
  },
  data() {
    return {
      data: [],
      getBy: 'SEND',
      loading: false,
      page: 1,
      perPage: 5,
      countOfPages: '',
      userId: this.$store.state.auth.user.userDto.id,
      currentWinnerId: null,
      currentWinner: null,
      country: '',
      city: '',
      modalType: '',
      address: '',
      trackNumber: '',
      userBalance: 0,
      buttonLoading: false,
      needToSaveAsDefault: false,
      amountRequest: 0,
      nameRequest : null,
      cardRequest : null,
      expireDateRequest : null
    }
  },
  methods: {
    isPageForRender(index) {
      //index < this.page - 4 || index > this.page + 5 }
      if (this.page < 5) {
        if (index < 9) {
          return true;
        } else {
          return false;
        }
      } else {
        if (index < this.page - 4 || index > this.page + 5) {
          return false;
        } else {
          return true;
        }
      }
    },
    changePage(index) {
      this.page = index;
      this.getAuctions();
      window.scrollTo(0, 0);
    },
    submitWithdrawMoneyRequest() {
      UserService.withdrawMoney(this.userId, this.amountRequest, this.nameRequest, this.cardRequest, this.expireDateRequest).then(
          () => {
          },
          (error) => {
            this.$notify({
              type: 'error',
              text: error.response.data.errorMessage
            });
          }
      );
    },
    handleDelivered() {
      WinnerService.delivered(this.currentWinnerId).then(
          () => {
            this.currentWinnerId = null;
            this.currentWinner.status = 'DELIVERY_FINISHED';
          },
          (error) => {
            this.$notify({
              type: 'error',
              text: error.response.data.errorMessage
            });
          }
      )
    },
    submitTrackNumber() {
      WinnerService.addTrackNumber(this.trackNumber, this.currentWinnerId).then(
          () => {
            this.currentWinnerId = null;
            this.currentWinner.status = 'DELIVERY_PROCESSING';
            this.currentWinner.trackNumber = this.trackNumber;
          },
          (error) => {
            this.$notify({
              type: 'error',
              text: error.response.data.errorMessage
            });
          }
      )
    },
    submitAddress() {
      const data = {
        country: this.country,
        city: this.city,
        address: this.address,
        saveAsDefault: this.needToSaveAsDefault
      }
      WinnerService.addAddress(data, this.currentWinnerId).then(
          () => {
            this.currentWinnerId = null;
            this.currentWinner.needAddress = false;
          },
          (error) => {
            this.$notify({
              type: 'error',
              text: error.response.data.errorMessage
            });
          }
      )
    },
    useDefaultAddress() {
      WinnerService.useDefaultAddress(this.currentWinnerId).then(
          () => {
            this.currentWinnerId = null;
            this.currentWinner.needAddress = false;
          },
          (error) => {
            this.$notify({
              type: 'error',
              text: error.response.data.errorMessage
            });
          }
      )
    },
    changeGetBy(filter) {
      this.data = [];
      this.getBy = filter;
      this.loading = true;
      if (filter == 'SEND') {
        this.page = 1;
        this.getSendPayments();
      } else if (filter == 'RECEIVE') {
        this.page = 1;
        this.data = [];
        this.getReceivePayments()
      }
      window.scrollTo(0, 0);
    },
    getReceivePayments() {
      this.loading = true;
      WinnerService.getWinnerForAuctionCreator(this.userId, this.page, this.perPage)
          .then(
              (response) => {
                console.log(response)
                this.loading = false;
                this.data = response.data.content;
                this.countOfPages = response.data.totalPages;
              },
              (error) => {
                this.$notify({
                  type: 'error',
                  text: error.response.data.errorMessage
                });
              }
          )
    },
    getSendPayments() {
      this.loading = true;
      WinnerService.getWinner(this.userId, this.page, this.perPage)
          .then(
              (response) => {
                this.loading = false;
                this.data = response.data.content;
                this.countOfPages = response.data.totalPages;
              }
          )
    },
    getCurrentAuctionWinnerById(id) {
      for (let i = 0; i < this.data.length; i++) {
        if (this.data[i].auctionEvent.id == id) {
          return this.data[i];
        }
      }
    },
    getUser() {
      UserService.getUserById(this.userId).then(
          (response) => {
            this.userBalance = response.data.moneyBalance;
          },
          (error) => {
            this.$notify({
              type: 'error',
              text: error.response.data.errorMessage
            });
          }
      )
    },
  },
  mounted() {
    this.getSendPayments();
    this.getUser();
  }
}
</script>

<style scoped>
.filter-button {
  border: none;
}

.card-title a {
  color: #0D0D0D;
}

.step-container {
  width: 100%;
  position: absolute;
  z-index: 1;
}

.progressbar li {
  float: left;
  width: 20%;
  position: relative;
  text-align: center;
}

.progressbar li:before {
  content: "1";
  width: 30px;
  height: 30px;
}

.mt-10 {
  margin-top: 6rem;
}

.progressbar li:before {
  content: "1";
  width: 30px;
  height: 30px;
  border: 2px solid #bebebe;
  display: block;
  margin: 0 auto 10px auto;
  border-radius: 50%;
  line-height: 27px;
  background: white;
  color: #bebebe;
  text-align: center;
  font-weight: bold;
}

.progressbar {
  counter-reset: step;
}

.progressbar li:before {
  content: counter(step);
  counter-increment: step;
  width: 30px;
  height: 30px;
  border: 2px solid #bebebe;
  display: block;
  margin: 0 auto 10px auto;
  border-radius: 50%;
  line-height: 27px;
  background: white;
  color: #bebebe;
  text-align: center;
  font-weight: bold;
}

.progressbar li:after {
  content: '';
  position: absolute;
  width: 100%;
  height: 3px;
  background: #979797;
  top: 15px;
  left: -50%;
  z-index: -1;
}

.progressbar li:first-child:after {
  content: none;
}

.progressbar li.active-item:after {
  background: #3aac5d;
}

.progressbar li.active-item:before {
  border-color: #3aac5d;
  background: #3aac5d;
  color: white
}
</style>