<template>
  <div class="row justify-content-between ml-auto mr-auto mt-5 container">
    <div class="profile-menu">
      <profile-menu :active-item="currentPage"
                    @handleChangePage="handleChangePage($event)"/>
    </div>
    <div class="profile-view">
      <account v-if="this.currentPage == 'account'" @handleChangePage="handleChangePage($event)"/>
      <change-password-component v-else-if="this.currentPage == 'account/password'"/>
      <user-table v-else-if="this.currentPage == 'user-table'"/>
      <auction-table v-else-if="this.currentPage == 'auction-table'"/>
      <statistic-page v-else-if="this.currentPage == 'statistic'"/>
      <user-auction-list v-else-if="this.currentPage == 'my-auctions'"/>
      <create-new-category v-else-if="this.currentPage == 'create-category'"/>
    </div>
  </div>
</template>

<script>
import ProfileMenu from './ProfileMenu';
import Account from './items/Account';
import UserTable from './admin/UserTable';
import AuctionTable from './admin/AuctionTable';
import StatisticPage from './StatisticPage';
import UserAuctionList from './items/UserAuctionList';
import CreateNewCategory from './admin/CreateNewCategory';
import ChangePasswordComponent from "./items/ChangePasswordComponent";

export default {
  name: "ProfilePage",
  components: {
    ProfileMenu,
    Account,
    UserTable,
    AuctionTable,
    UserAuctionList,
    StatisticPage,
    CreateNewCategory,
    ChangePasswordComponent,
  },
  data() {
    return {
      currentPage: null,
    }
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    }
  },
  methods: {
    handleChangePage(event) {
      this.currentPage = event;
    }
  },
  mounted() {
    this.currentPage = this.$route.path.replaceAll('/profile/', '');
    // if (!this.currentUser) {
    //   this.$router.push('/login');
    // }
  }
}
</script>

<style scoped>
@media only screen and (min-width: 576px) {
  .profile-menu,
  .profile-view {
    flex: 0 0 100%;
    max-width: 100%;
  }
}

@media only screen and (min-width: 992px) {
  .profile-menu {
    flex: 0 0 25%;
    max-width: 25%;
  }
  .profile-view {
    flex: 0 0 75%;
    max-width: 75%;
  }
}

.profile-menu,
.profile-view {
  position: relative;
  width: 100%;
  padding-right: 15px;
  padding-left: 15px;
}
</style>