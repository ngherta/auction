<template>
  <div class="row justify-content-between ml-auto mr-auto mt-5 container">
    <div class="col-3">
      <profile-menu :active-item="currentPage"
                    @handleChangePage="handleChangePage($event)"/>
    </div>
    <div class="col-8">
      <account v-if="this.currentPage == 'account'"></account>
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

</style>