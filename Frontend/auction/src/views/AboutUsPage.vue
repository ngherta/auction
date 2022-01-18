<template>
  <div class="container">
    <h1 class="h1">About us</h1>
    <p>
      Welcome to the LOT
      <span v-if="this.loggedInValue">
        , <b>{{
          this.$store.state.auth.user.userDto.firstName + ' ' + this.$store.state.auth.user.userDto.lastName
        }}</b>
      </span>!<br>
      LOT is a platform for creating events of buying and selling goods by offering them up for bid, taking bids, and
      then selling the item to the highest bidder or buying the item from the lowest bidder.
      My customer is a person that is collect interested in selling something or just wants to buy some rare item and
      get emotions.
      One of the reasons I chose this project is because I chose project for my bachelor's degree at university. Another
      reason is the fact that i have never done anything like this. It involves many
      things that I'll be doing for the first time. I want to create a convenient and useful application that people
      will use. I’m sure I’ll learn a lot out of all this. It should prove challenging,
      which is the point of this program.
    </p>
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