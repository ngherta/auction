<template>
  <div class="">
    <ul class="list-unstyled">
      <li>
        <router-link @click="this.$emit('handleChangePage', 'account')"
                     to="/profile/account"
                     :disabled="activeItem == 'account'"
                     class="text-decoration-none profile-item-link">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'account' || activeItem == 'update/password'}">
            <Icon name="profile-account-overview"/>
            <span class="pl-3 profile-item-text">Account overview</span>
          </div>
        </router-link>
      </li>
      <li>
        <router-link @click="this.$emit('handleChangePage', 'my-auctions')"
                     to="/profile/my-auctions"
                     :disabled="activeItem == 'my-auctions'"
                     class="text-decoration-none profile-item-link">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'my-auctions'}">
            <Icon name="profile-my-auctions" size="1.6"/>
            <span class="pl-3 profile-item-text">My auctions</span>
          </div>
        </router-link>
      </li>
      <li>
        <router-link @click="this.$emit('handleChangePage', 'settings')"
                     to="/profile/settings"
                     :disabled="activeItem == 'settings'"
                     class="text-decoration-none profile-item-link profile-item-link">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'settings'}">
            <Icon name="settings" size="0.8"/>
            <span class="pl-3 profile-item-text">Settings</span>
          </div>
        </router-link>
      </li>
      <li>
        <router-link @click="this.$emit('handleChangePage', 'payments')"
                     to="/profile/payments"
                     :disabled="activeItem == 'payments'"
                     class="text-decoration-none profile-item-link profile-item-link">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'payments'}">
            <Icon name="payments" size="1.8"/>
            <span class="pl-3 profile-item-text">Payments</span>
          </div>
        </router-link>
      </li>
      <li v-show="isAdmin">
        <router-link @click="this.$emit('handleChangePage', 'create-category')"
                     to="/profile/create-category"
                     :disabled="activeItem == 'create-category'"
                     class="text-decoration-none profile-item-link">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'create-category'}">
            <Icon name="categories" size="1.2"/>
            <span class="pl-3 profile-item-text">New categories</span>
          </div>
        </router-link>
      </li>
      <li v-show="isAdmin">
        <router-link @click="this.$emit('handleChangePage', 'home-images')"
                     to="/profile/home-images"
                     :disabled="activeItem == 'home-images'"
                     class="text-decoration-none profile-item-link profile-item-link">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'home-images'}">
            <Icon name="profile-images" size="1.7"/>
            <span class="pl-3 profile-item-text">Home images</span>
          </div>
        </router-link>
      </li>
      <li v-show="isAdmin">
        <router-link @click="this.$emit('handleChangePage', 'user-table')"
                     to="/profile/user-table"
                     :disabled="activeItem == 'user-table'"
                     class="text-decoration-none profile-item-link">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'user-table'}">
            <Icon name="profile-auction-table"/>
            <span class="pl-3 profile-item-text">User table</span>
          </div>
        </router-link>
      </li>
      <li v-show="isAdmin">
        <router-link @click="this.$emit('handleChangePage', 'auction-table')"
                     to="/profile/auction-table"
                     :disabled="activeItem == 'auction-table'"
                     class="text-decoration-none profile-item-link">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'auction-table'}">
            <Icon name="profile-auction-table"/>
            <span class="pl-3 profile-item-text">Auction table</span>
          </div>
        </router-link>
      </li>
      <li v-show="isAdmin">
        <router-link @click="this.$emit('handleChangePage', 'complaints')"
                     to="/profile/auction-table"
                     :disabled="activeItem == 'complaints'"
                     class="text-decoration-none profile-item-link">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'complaints'}">
            <Icon name="profile-complaints" size="1.2"/>
            <span class="pl-3 profile-item-text">Complaints</span>
          </div>
        </router-link>
      </li>
      <li v-show="isAdmin">
        <router-link @click="this.$emit('handleChangePage', 'statistic')"
                     to="/profile/statistic"
                     :disabled="activeItem == 'statistic'"
                     class="text-decoration-none profile-item-link">
          <div class="p-3 border profile-item"
               :class="{'profile-item-active' : activeItem == 'statistic'}">
            <Icon name="profile-statistic"/>
            <span class="pl-3 profile-item-text">Statistic</span>
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
  data() {
    return {
      isAdmin: this.$store.state.auth.isAdmin,
    }
  },
  methods: {

  },
  mounted() {
  }
}
</script>

<style scoped>
.profile-item-active {
  background-color: #999999;
}

.profile-item-active:hover {
  background-color: #999999 !important;
}

.profile-item-link {
  color: #0D0D0D;
}

.profile-item-link:hover {
  background-color: #f2f2f2;
}

.profile-item:hover {
  background-color: #f2f2f2;
}

</style>