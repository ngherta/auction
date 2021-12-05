<template>
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
</template>

<script>
import AuctionService from '../services/auction.service';
export default {
  name: "Home",
  data() {
    return {
      // user: JSON.parse(localStorage.getItem('user')).userDto.firstName
      auctions : ""
    };
  },
  mounted() {
    AuctionService.getAuctions().then(
        (response) => {
          this.auctions = response.data.content;
          console.log(response.data);
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
  },
};
</script>

<style>
.card {
  max-width: 370px;
}
</style>
