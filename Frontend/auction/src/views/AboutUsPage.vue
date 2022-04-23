<template>
  <div class="container mt-5">
    <h1 class="h1 text-center mb-3">About us</h1>
    <p>
      LOT is a platform for creating events of buying and selling goods by offering them up for bid, taking bids, and
      then selling the item to the highest bidder or buying the item from the lowest bidder.
      My customer is a person that is collect interested in selling something or just wants to buy some rare item and
      get emotions.
    </p>
    <p>It involves many things that I'll be doing for the first time. I want to create a convenient and useful application that people
      will use. I’m sure I’ll learn a lot out of all this. It should prove challenging,
      which is the point of this program.</p>
    <p>If you find a bug, you can create a <a href="https://github.com/gogo6ar/auction/issues" target="_blank">report</a>.</p>
  </div>
</template>

<script>
export default {
  name: "AboutUsPage",
  components: {},
  data() {
    return {
      date: null,
      loggedInValue: false,
    }
  },
  methods: {
    loggedIn() {
      if (this.$store.state.auth.status.loggedIn) {
        return true;
      } else {
        return false;
      }
    },
  },
  created() {
    this.loggedInValue = this.loggedIn();
  }
}
</script>

<style scoped>

</style>