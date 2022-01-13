<template>
  <div class="container mt-5">
    <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img class="d-block w-100" src="../assets/logo.jpg" alt="First slide">
        </div>
        <div class="carousel-item">
          <img class="d-block w-100" src="../assets/logo.jpg" alt="Second slide">
        </div>
        <div class="carousel-item">
          <img class="d-block w-100" src="../assets/logo.jpg" alt="Third slide">
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
    <div class="">
      <div class="card-deck" id="auctions-rendering">
        <div v-for="auction in auctions" :key="auction.id" class="card">
          <img class="card-img-top" v-bind:src="auction.images[0]" alt="image of auction">
          <div class="card-body">
            <h5 class="card-title">{{ auction.title }}</h5>
            <p class="card-text">{{ auction.description }}</p>
            <p class="card-text">
              <small class="text-muted">{{ auction.statusType }}</small>
            </p>
          </div>
        </div>
      </div>
    </div>
    <div class="d-flex mt-5 mb-5">
      <router-link to="/auctions" class="badge badge-pill badge-info m-auto text-uppercase">Show more</router-link>
    </div>
  </div>
</template>

<script>
import AuctionService from '../services/auction.service';

export default {
  name: "Home",
  data() {
    return {
      auctions: ""
    };
  },
  mounted() {
    AuctionService.getAuctions(15, null).then(
        (response) => {
          this.auctions = response.data.content;
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
.card {
  max-width: 370px;
}
</style>
