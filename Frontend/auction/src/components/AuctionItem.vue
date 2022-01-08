<template>
  <router-link :to="'/auction/'+this.id" class="card text-decoration-none" style="width: 18rem; color: black">
    <img class="card-img-top" :src=this.image alt="Card image cap">
    <div class="card-body">
      <h3 class="card-title text-center">{{ this.title }}</h3>
      <p v-if="lastBid != 0" class="card-text text-center">Last bid: {{ this.lastBid }} USD</p>
      <p v-else-if="lastBid == 0" class="card-text text-center">Bid not found</p>
      <p class="card-text text-center">Finish price: {{ this.finishPrice }}</p>
      <a href="#" class="btn btn-primary text-center">Go somewhere</a>
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

</style>