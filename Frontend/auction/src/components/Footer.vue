<template>
  <footer class="footer">
    <div class="container">
      <p>{{footerDescription}}</p>
    </div>
    <div>
      <a href="#">
        <Icon name = 'twitter'></Icon>
      </a>
      <a href="#">
        <Icon name = 'instagram'></Icon>
      </a>
      <a onclick="" href="#">
        <Icon name = 'facebook'></Icon>
      </a>
      <p>Copyright 2021, LOT.<br>
        All rights reserved.</p>
    </div>
    <div>
      <p>
        <Icon name = 'email'></Icon>
        info@lot.md</p>
      <p>
        <Icon name = 'phone'></Icon>
        068777999</p>
      <p><Icon name = 'place'></Icon>
        Moldova, Chisinau</p>
    </div>
  </footer>
</template>

<script>
import Icon from "@/components/svg/Icon";

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