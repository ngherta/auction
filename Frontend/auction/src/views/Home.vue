<template>
  <div class="container mt-5">
    <div id="carouselExampleControls" class="carousel slide mb-5" data-ride="carousel">
      <div class="carousel-inner">
        <div v-for="(image, index) of this.sortImages()"
             :key="image"
             class="carousel-item "
             v-bind:class="{active : index == 0}">
          <router-link :to="image.url">
            <img class="d-block w-100"
                 v-bind:src="image.imageLink" :alt="index + 'slide'">
          </router-link>
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
    <div class="d-flex auction-box flex-wrap mb-5 ml-auto justify-content-between">
      <div v-for="(auction, index) in auctions"
           :key="auction.id"
           :id="'auction-item-' + index"
           class="card m-auto"
           style="width: 18rem;">
        <img :src="auction.images[0]" height="300" class="card-img-top" alt="image of auction">
        <div class="card-body">
          <h5 class="card-title">{{ auction.title }}</h5>
          <p class="card-text">Status: <b>{{ auction.statusType }}</b></p>
        </div>
        <ul class="list-group list-group-flush">
          <li class="list-group-item">Charity percent:
            <b>{{ auction.charityPercent }} %</b></li>
          <li class="list-group-item">Start date: <b>{{ auction.startDate }}</b></li>
        </ul>
        <div class="card-body">
          <router-link :to="'/auction/' + auction.id" class="card-link">Open</router-link>
        </div>
      </div>
    </div>
    <div class="d-flex mt-5 mb-5">
      <router-link to="/auctions" style="font-size: 1rem"
                   class="badge badge-pill badge-info m-auto pt-1 pb-1 pr-3 pl-3 text-uppercase font-weight-bold">Show
        more
      </router-link>
    </div>
  </div>
</template>

<script>
import AuctionService from '../services/auction.service';
import ImageLinkService from '../services/imageLink.service';

export default {
  name: "Home",
  data() {
    return {
      auctions: [],
      images: [],
    };
  },
  methods: {
    sortImages() {
      return this.images.slice().sort(function (a, b) {
        return (a.sequence > b.sequence) ? 1 : -1;
      });
    }
  },
  mounted() {
    ImageLinkService.getAllByType('HOME_PAGE').then(
        (response) => {
          this.images = response.data;
        },
        (error) => {
          this.$notify({
            text: error.response.data.errorMessage,
            type: 'error'
          });
        }
    );
    AuctionService.getAuctions(3, null).then(
        (response) => {
          this.auctions = response.data.content;
          if (this.auctions.length > 0) {
            this.$emit('handleAuctionIdForTutorial', this.auctions[0].id)
          }
        },
        (error) => {
          this.$notify({
            text: error.response.data.errorMessage,
            type: 'error'
          });
        }
    );
  },
};
</script>

<style>
/*@media only screen and (max-width: 800px) {*/
/*  .auction-box {*/
/*    flex-direction: column;*/
/*    align-items: center;*/
/*    margin-top: 1rem;*/
/*  }*/
/*}*/
.card {
  max-width: 370px;
}
</style>
