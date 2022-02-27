<template>
  <div class="border container-lg p-5 mb-5">
    <Icon name="settings"/>
    <h2 class="h2 mt-2">Payments</h2>
    <div class="d-flex justify-content-end mt-3">
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
    <div>
      {{ data }}
    </div>
  </div>
</template>

<script>
import Icon from '../../../components/Icon';
import PaymentService from "@/services/payment.service";

export default {
  name: "Payments",
  components: {
    Icon,
  },
  data() {
    return {
      data: [],
      getBy: 'SEND',
      loading: false,
      page: 1,
      perPage: 5,
      userId: this.$store.state.auth.user.userDto.id,
    }
  },
  methods: {
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
      PaymentService.getPaymentsWithAuctionByUserId(this.userId, this.page, this.perPage)
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
</style>