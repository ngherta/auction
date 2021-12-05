<template>
  <div>
    <router-link to="/home" class="m-auto">
      <img class="img-logo m-auto" src="../assets/logo.jpg" alt="Logo">
    </router-link>
  </div>
  <div class="container-lg container-border">
    <div class="container">
      <div class="row">
        <div class="col-sm border-bottom border-info p-1">
          <router-link to="/register" class="nav-link text-center">SIGN UP</router-link>
        </div>
        <div class="line"></div>
        <div class="col-sm border-bottom p-1">
          <router-link to="/login" class="nav-link text-center">SIGN IN</router-link>
        </div>
      </div>
    </div>
    <div class="container p-5">
      <Form @submit="handleRegister" :validation-schema="schema">
        <div v-if="!successful">
          <div class="mb-3">
            <label for="firstName">FIRST NAME:</label>
            <Field name="firstName" id="firstName" type="text" class="form-control" />
            <ErrorMessage name="firstName" class="error-feedback" />
          </div>
          <div class="mb-3">
            <label for="lastName">LAST NAME:</label>
            <Field name="lastName" id="lastName" type="text" class="form-control" />
            <ErrorMessage name="lastName" class="error-feedback" />
          </div>
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
  name: "Register",
  components: {
    Form,
    Field,
    ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      username: yup
        .string()
        .required("Username is required!")
        .min(3, "Must be at least 3 characters!")
        .max(20, "Must be maximum 20 characters!"),
      email: yup
        .string()
        .required("Email is required!")
        .email("Email is invalid!")
        .max(50, "Must be maximum 50 characters!"),
      password: yup
        .string()
        .required("Password is required!")
        .min(6, "Must be at least 6 characters!")
        .max(40, "Must be maximum 40 characters!"),
    });

    return {
      successful: false,
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
  mounted() {
    if (this.loggedIn) {
      this.$router.push("/profile");
    }
  },
  methods: {
    handleRegister(user) {
      this.message = "";
      this.successful = false;
      this.loading = true;

      this.$store.dispatch("auth/register", user).then(
        (data) => {
          this.message = data.message;
          this.successful = true;
          this.loading = false;
          this.$router.push("/login");

        },
        (error) => {
          this.message =
            (error.response &&
              error.response.data &&
              error.response.data.message) ||
            error.message ||
            error.toString();
          this.successful = false;
          this.loading = false;
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

.img-logo {
  width: 20%;
}

.error-feedback {
  color: red;
}
</style>
