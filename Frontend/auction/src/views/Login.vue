<template>
  <div class="d-flex align-content-center text-center">
    <router-link to="/home" >
      <img class="img-logo" src="../assets/logo.jpg" alt="Logo">
    </router-link>
  </div>
  <div class="container-lg container-border">
    <div class="container">
      <div class="row">
        <div class="col-sm border-bottom p-1">
          <router-link to="/register" class="nav-link text-center">SIGN UP</router-link>
        </div>
        <div class="line"></div>
        <div class="col-sm border-bottom border-info p-1">
          <router-link to="/login" class="nav-link text-center">SIGN IN</router-link>
        </div>
      </div>
    </div>
    <div class="container register-form">
      <Form @submit="handleLogin" :validation-schema="schema">
        <div v-if="!successful">
          <div class="mb-3">
            <label for="email">EMAIL:</label>
            <Field name="email" id="email" type="email" class="form-control" />
            <ErrorMessage name="email" class="error-feedback" />
          </div>
          <div class="mb-3">
            <label for="password">PASSWORD:</label>
            <Field name="password" id="password" type="password" class="form-control" />
            <ErrorMessage name="password" class="error-feedback" />
          </div>

          <div class="">
            <button class="btn btn-primary btn-block" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
              Sign Up
            </button>
          </div>
        </div>
      </Form>

      <div
          v-if="message"
          class="alert"
          :class="successful ? 'alert-success' : 'alert-danger'"
      >
        {{ message }}
      </div>
    </div>
  </div>
</template>

<script>
import { Form, Field, ErrorMessage } from "vee-validate";
import * as yup from "yup";

export default {
  name: "Login",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      email: yup.string().required("Email is required!"),
      password: yup.string().required("Password is required!"),
    });

    return {
      loading: false,
      message: "",
      schema,
    };
  },
  computed: {
    loggedIn() {
      return this.$store.state.auth.status.loggedIn;
    },
  },
  created() {
    if (this.loggedIn) {
      this.$router.push("/profile");
    }
  },
  methods: {
    handleLogin(user) {
      this.loading = true;

      this.$store.dispatch("auth/login", user).then(
          () => {
            this.$router.push("/home");
          },
          (error) => {
            this.loading = false;
            this.message =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();
          }
      );
    },
  },
};
</script>



<style scoped>
.line {
  content: " ";
  height: 25px;
  width: 2px;
  position: absolute;
  right: 0px;
  top: 14px;
  background: #2d2d2d;
  opacity: .1;
}

@media only screen and (min-width: 576px){
  .register-form {
    padding: 1rem 1rem;
  }
}

@media only screen and (min-width: 768px){
  .register-form {
    padding: 1rem 1rem;
  }
}

@media only screen and (min-width: 992px){
  .register-form {
    padding: 1rem 11rem;
  }
}

@media only screen and (min-width: 1200px){
  .register-form {
    padding: 1rem 11rem;
  }
}

@media only screen and (min-width: 1400px){
  .register-form {
    padding: 1rem 11rem;
  }
}

.img-logo {
  width: 20%;
}

.error-feedback {
  color: red;
}
</style>