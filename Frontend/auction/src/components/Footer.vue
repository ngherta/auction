<template>
  <footer class="flex-lg-wrap pl-5 pr-5 footer pt-4 pb-4">
    <div class="container-fluid footer row m-auto">
      <div class="col-sm d-flex m-auto">
        <p class="text-center">{{footerDescription}}</p>
      </div>
      <div class="col-sm d-flex flex-column justify-content-center">
        <div class="ml-auto mr-auto">
          <a class="mr-1" href="#">
            <Icon name = 'twitter' class="footer-icons"/>
          </a>
          <a class="mr-1" href="#">
            <Icon name = 'instagram' class="footer-icons"/>
          </a>
          <a href="#">
            <Icon name = 'facebook' class="footer-icons"/>
          </a>
        </div>
        <span class="text-center pt-3">Copyright 2021, LOT.<br>
        All rights reserved.</span>
      </div>
        <div class="col d-flex">
          <div class="d-flex m-auto flex-column justify-content-center">
            <span class="pb-3 footer-info">
            <Icon name = 'email' class="footer-icons"/>
          info@lot.md</span>
            <span class="pb-3 footer-info">
            <Icon name = 'phone' class="footer-icons"/>
          068777999</span>
            <span>
            <Icon name = 'place' class="footer-icons"/>
          Moldova, Chisinau</span>
          </div>
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
.footer {
  background-color: #E8EDEF;
}

.footer-icons {
  opacity: 75%;
}

</style>