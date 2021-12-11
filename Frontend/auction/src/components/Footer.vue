<template>
  <footer class="container">
    <div class="footer row">
      <div class="container col-sm">
        <p>{{footerDescription}}</p>
      </div>
      <div class="col-sm">
        <div class="col-sm">
          <a href="#">
            <Icon name = 'twitter'></Icon>
          </a>
          <a href="#">
            <Icon name = 'instagram'></Icon>
          </a>
          <a href="#">
            <Icon name = 'facebook'></Icon>
          </a>
        </div>
        <span class="text-center">Copyright 2021, LOT.<br>
        All rights reserved.</span>
      </div>
      <div class="col-sm m-auto row flex-column">
      <span class="col">
        <Icon name = 'email'></Icon>
        info@lot.md</span>
        <span class="col">
        <Icon name = 'phone'></Icon>
        068777999</span>
        <span class="col">
        <Icon name = 'place'></Icon>
        Moldova, Chisinau</span>
      </div>
    </div>
  </footer>
</template>

<script>
import Icon from "./Icon"
export default {
  name: "Footer",
  components: {Icon},
  data() {
    return {
      footerDescription: 'LOT is a platform for creating events of ' +
          'buying and selling goods by offering them ' +
          'up for bid, taking bids, and then selling the item to the ' +
          'highest bidder or buying the item from the lowest bidder.'
    }
  }
}
</script>

<style scoped>

</style>