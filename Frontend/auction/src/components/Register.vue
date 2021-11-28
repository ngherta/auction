<template>
  <div>
    <h2>Register</h2>
    <router-link to="/login" class="btn btn-link">
      <h2>Log in</h2>
    </router-link>
    <form @submit.prevent="handleSubmit">
      <div class="form-group">
        <label for="email">Username</label>
        <input type="text" v-model="user.email" id="email" name="email" class="form-control" />
      </div>
      <div class="form-group">
        <label for="firstName">First Name</label>
        <input type="text" v-model="user.firstName" id="firstName" name="firstName" class="form-control" />
      </div>
      <div class="form-group">
        <label for="lastName">Last Name</label>
        <input type="text" v-model="user.lastName" id="lastName" name="lastName" class="form-control" />
      </div>
      <div class="form-group">
        <label for="password">Password</label>
        <input type="password" v-model="user.password" id="password" name="password" class="form-control" />
      </div>
      <div class="form-group">
        <button class="btn btn-primary" :disabled="status.registering">Register</button>
      </div>
    </form>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  data () {
    return {
      user: {
        firstName: '',
        lastName: '',
        username: '',
        password: ''
      },
      submitted: false
    }
  },
  computed: {
    ...mapState('account', ['status'])
  },
  methods: {
    ...mapActions('account', ['register']),
    handleSubmit() {
      this.submitted = true;
      this.register(this.user);
    }
  }
};
</script>

<style scoped>

</style>