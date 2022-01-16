<template>
  <div class="">
    <ul class="list-unstyled">
      <li>
        <router-link @click="this.$emit('handleChangePage', 'account')" to="/profile/account" class="">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'account'}">
            <Icon name="profile-account-overview"/>
            <span class="pl-3 profile-item-text">Account overview</span>
          </div>
        </router-link>
      </li>
      <li>
        <router-link @click="this.$emit('handleChangePage', 'user-table')"  to="/profile/user-table" class="">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'user-table'}">
            <Icon name="profile-auction-table"/>
            <span class="pl-3 profile-item-text">User table</span>
          </div>
        </router-link>
      </li>
      <li>
        <router-link @click="this.$emit('handleChangePage', 'auction-table')" to="/profile/auction-table" class="">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'auction-table'}">
            <Icon name="profile-auction-table"/>
            <span class="pl-3 profile-item-text">Auction table</span>
          </div>
        </router-link>
      </li>
      <li>
        <router-link @click="this.$emit('handleChangePage', 'statistic')" to="/profile/statistic" class="">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'statistic'}">
            <Icon name="profile-statistic"/>
            <span class="pl-3 profile-item-text">Statistic</span>
          </div>
        </router-link>
      </li>
      <li>
        <router-link to="/home" class="">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'account-overview'}">
            <Icon name="profile-account-overview"/>
            <span class="pl-3 profile-item-text">Account overview</span>
          </div>
        </router-link>
      </li>
    </ul>
  </div>
</template>

<script>
import Icon from '../../components/Icon';

export default {
  name: "ProfileMenu",
  components: {
    Icon,
  },
  props: [
    'activeItem'
  ],
  methods: {
    // isActiveItem(item) {
    //   console.log("NGH!!!!");
    //   console.log(this.$route);
    //   if (item == 'account-overview') {
    //     return true;
    //   }
    // }
  }
}
</script>

<style scoped>
.profile-item-active {
  background-color: #999999;
}

</style>