<template>
  <div class="row ml-auto mr-auto mt-5">
    <div class="col-2">
      <profile-menu :active-item="currentPage"
                    @handleChangePage="handleChangePage($event)"/>
    </div>
    <div class="col-9">
      <account v-if="this.currentPage == 'account'"></account>
      <user-table v-else-if="this.currentPage == 'user-table'"/>
    </div>
  </div>
</template>

<script>
import ProfileMenu from './ProfileMenu';
import Account from './items/Account';
import UserTable from './admin/UserTable'

export default {
  name: "ProfilePage",
  components: {
    ProfileMenu,
    Account,
    UserTable,
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