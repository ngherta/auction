<template>
  <router-link :to="'/auction/'+this.id" class="card text-decoration-none" style="width: 18rem; color: black">
    <div class="image-auction-item">
      <img class="card-img-top" :src=this.image alt="Card image cap">
    </div>
    <div class="card-body">
      <h3 class="card-title text-center h3">{{ this.title }}</h3>
      <p v-if="lastBid != 0" class="card-text text-center">Last bid: {{ this.lastBid }} USD</p>
      <p v-else-if="lastBid == 0" class="card-text text-center">Bid not found</p>
      <p class="card-text text-center">Finish price: {{ this.finishPrice }}</p>
    </div>
  </router-link>
</template>

<script>

export default {
  name: "AuctionItem",
  props: [
    'id',
    'title',
    'finishPrice',
    'image',
    'lastBid'
  ]
}
</script>

<style scoped>
.image-auction-item {
  width: 17.5rem;
  /*height: 10rem;*/
  object-fit: scale-down;
  margin: 0 auto;
}
</style>