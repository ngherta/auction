<template>
  <div class="container border p-5">
    <h1 class="h1">{{ this.title }}</h1>
    <div class="d-flex justify-content-end mt-3">
      <button class="badge badge-pill filter-button"
              @click="changeGetBy('ALL')"
              :class="{'badge-light' : this.getBy != 'ALL',
              'badge-success' : this.getBy == 'ALL'}">All
      </button>
      <button class="badge badge-pill ml-2 filter-button"
              @click="changeGetBy('WINNING')"
              :class="{'badge-light' : this.getBy != 'WINNING',
              'badge-success' : this.getBy == 'WINNING'}">Winning
      </button>
      <button class="badge badge-pill ml-2 filter-button"
              @click="changeGetBy('OWNER')"
              :class="{'badge-light' : this.getBy != 'OWNER',
              'badge-success' : this.getBy == 'OWNER'}">My auctions
      </button>
      <button class="badge badge-pill ml-2 filter-button"
              @click="changeGetBy('PARTICIPANT')"
              :class="{'badge-light' : this.getBy != 'PARTICIPANT',
              'badge-success' : this.getBy == 'PARTICIPANT'}">Participant
      </button>
    </div>
    <div v-if="this.data.length != 0">
      <div v-for="auction in data" :key="auction" class="card mt-3" style="max-width: 540px;">
        <!--        <router-link :to="'/auction/' + auction.id"-->
        <!--                     class="text-decoration-none">-->
        <div class="row no-gutters">
          <div class="img-item">
            <img class="image-item"
                 :src="auction.images[0]"
                 alt="Image of auction">
          </div>
          <div class="col-md-8">
            <div class="card-body">
              <h5 class="card-title">{{ auction.title }}</h5>
              <p class="card-text">{{ auction.description }}</p>
              <p class="card-text"><small class="text-muted">{{ auction.statusType }}</small></p>
              <a v-if="title == 'WINNING'"
                 :href="paymentOrders.get(auction.id)"
                 class="btn btn-success">Pay</a>
            </div>
          </div>
        </div>
        <!--        </router-link>-->
      </div>
    </div>
    <h4 v-else-if="this.data.length == 0 && this.loading == false"
        class="alert text-center alert-dark mt-5" role="alert">
      List is empty :(
    </h4>
    <span
        v-show="loading"
        class="spinner-border m-auto spinner-border-sm"
    ></span>
  </div>
</template>

<script>
import AuctionService from '../../../services/auction.service';
import PaymentService from '@/services/payment.service';

export default {
  name: "UserAuctionList",
  data() {
    return {
      data: [],
      page: 1,
      perPage: 5,
      getBy: 'ALL',
      loading: false,
      userId: this.$store.state.auth.user.userDto.id,
      title: 'Auctions',
      paymentOrders: new Map(),
    }
  },
  methods: {
    changeGetBy(filter) {
      this.data = [];
      this.getBy = filter;
      this.loading = true;
      if (filter == 'ALL') {
        this.title = 'All Auctions'
        this.getAuctionsByOwner();
      } else if (filter == 'WINNING') {
        this.title = 'WINNING';
        this.getWinningAuctions();
      } else if (filter == 'OWNER') {
        this.title = 'My auctions';
        this.getAuctionsByOwner();
      } else if (filter == 'PARTICIPANT') {
        this.title = 'PARTICIPANT';
        this.getAuctionsByParticipant();
      }
      window.scrollTo(0, 0);
    },
    changePage(index) {
      this.page = index;
      if (this.getBy == 'ALL') {
        this.getAuctionsByOwner(this.userId);
      }
      window.scrollTo(0, 0);
    },
    getWinningAuctions() {
      PaymentService.getPaymentsWithAuctionByUserId(this.userId, this.page, this.perPage).then(
          (response) => {
            this.data = [];
            console.log(response);
            const tmpData = response.data.content;
            for (let i = 0; i < tmpData.length; i++) {
              this.data.push(tmpData[i].auctionEvent);
              this.paymentOrders.set(tmpData[i].auctionEvent.id, tmpData[i].link)
              console.log(tmpData[i].auctionEvent.id);
              console.log(this.paymentOrders.get(tmpData[i].auctionEvent.id))
            }
            console.log(tmpData);
          },
          (error) => {
            this.$notify({
              text: error,
              type: 'error'
            });
          }
      )
      this.loading = false;
    },
    getAuctionsByOwner() {
      AuctionService.getByOwner(this.userId, this.page, this.perPage).then(
          (response) => {
            this.data = response.data.content;
          },
          (error) => {
            this.$notify({
              text: error,
              type: 'error'
            });
          }
      )
      this.loading = false;
    },
    getAuctionsByParticipant() {
      AuctionService.getByParticipant(this.userId, this.page, this.perPage).then(
          (response) => {
            this.data = response.data.content;
          },
          (error) => {
            this.$notify({
              text: error,
              type: 'error'
            });
          }
      )
      this.loading = false;
    }
  },
  mounted() {
    this.getAuctionsByOwner();
  },

}
</script>

<style scoped>
.filter-button {
  border: none;
}

.image-item {
  max-width: 11rem;
  margin: auto;
}

.img-item {
  /*height: 10rem;*/
  display: flex;
  object-fit: scale-down;
  margin: 0 auto;
  max-width: 11rem;
}

.text-decoration-none {
  color: black;
}
</style>