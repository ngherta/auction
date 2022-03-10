<template>
  <div class="border container-lg p-5 mb-5">
    <Icon name="settings"/>
    <h2 class="h2 mt-2">Payments</h2>
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
    <div v-if="data.length == 0" class="alert alert-dark" role="alert">
      Data not found
    </div>
    <div v-for="(item, index) in data" :key="index" class="card mw-100">
      <div class="card-header">
        Price: <b>{{ item.price }} $</b>
      </div>
      <div class="card-body">
        <h5 class="card-title">
          <router-link :to="'/auction/' + item.auctionEvent.id">
            {{ item.auctionEvent.title }}
          </router-link>
        </h5>
        <p class="card-text">{{item.needAddress}}</p>
        <button @click="currentWinnerId = item.auctionEvent.id; currentWinner = item"
                v-if="item.needAddress == true" type="button" class="btn btn-primary" data-toggle="modal" data-target="#addressModal">Confirm Address</button>
        <a :href="item.paymentOrder.link" target="_blank" class="btn btn-primary">Pay</a>
      </div>
    </div>
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
          <form>
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
          </form>
        </div>
        <div class="modal-footer">
          <button v-if="this.$store.state.auth.user.userDto.hasDefaultAddress == true"
                  @click="useDefaultAddress"
                  type="button"
                  class="btn btn-secondary"
                  data-dismiss="modal">Use default address</button>
          <button type="submit"
                  @click="submitAddress"
                  class="btn btn-primary"
                  data-dismiss="modal">Add address</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import Icon from '../../../components/Icon';
import PaymentService from "@/services/payment.service";
import WinnerService from "@/services/winner.service";

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
      userId: this.$store.state.auth.user.userDto.id,
      currentWinnerId: null,
      currentWinner: null,
      country: '',
      city: '',
      address: '',
    }
  },
  methods: {
    submitAddress() {
      const data = {
        country: this.country,
        city: this.city,
        address: this.address
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
      console.log("TESTTSTSET")
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
      PaymentService.getReceiveOrderWithAuctionByUserId(this.userId, this.page, this.perPage)
          .then(
              (response) => {
                this.loading = false;
                this.data = response.data.content;
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
              }
          )
    }
  },
  mounted() {
    this.getSendPayments();
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
</style>