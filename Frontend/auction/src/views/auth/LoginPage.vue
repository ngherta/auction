<template>
  <Main_Logo class="mt-5 mb-5"></Main_Logo>
  <div class="container border mb-4">
    <div class="container p-0">
      <div class="row">
        <div class="col-sm border-bottom p-0">
          <router-link to="/register" class="nav-link text-center list-group-item list-group-item-action">SIGN UP
          </router-link>
        </div>
        <div class="line"></div>
        <div class="col-sm border-bottom border-info p-0">
          <router-link to="/login" class="nav-link text-center list-group-item list-group-item-action">SIGN IN
          </router-link>
        </div>
      </div>
    </div>
    <div class="container register-form pt-5 pb-5">
      <Form @submit="handleLogin" :validation-schema="schema">
        <div v-if="!successful">
          <div class="mb-3">
            <label for="email">EMAIL:</label>
            <Field name="email" id="email" type="email" class="form-control"/>
            <ErrorMessage name="email" class="error-feedback"/>
          </div>
          <div class="mb-3">
            <label for="password">PASSWORD:</label>
            <Field name="password" id="password" type="password" class="form-control"/>
            <ErrorMessage name="password" class="error-feedback"/>
          </div>

          <div class="">
            <button class="btn btn-primary btn-block" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
              Login
            </button>
          </div>
        </div>
      </Form>
      <div class="d-flex justify-content-center mt-3">
        <router-link to="/password/reset">Forgot password?</router-link>
      </div>

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
import {Form, Field, ErrorMessage} from "vee-validate";
import * as yup from "yup";
import Main_Logo from "@/components/Main_Logo";

export default {
  name: "Login",
  components: {
    Form,
    Field,
    ErrorMessage,
    Main_Logo,
  },
  data() {
    const schema = yup.object().shape({
      email: yup.string().required("Email is required!"),
      password: yup.string().required("Password is required!"),
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
  created() {
    if (this.loggedIn) {
      this.$router.push("/profile/account");
    }
  },
  methods: {
    handleLogin(user) {
      this.loading = true;

      this.$store.dispatch("auth/login", user).then(
          (response) => {
            console.log(response);
            if (response.userDto.needTutorial === true) {
              this.$router.push("/tutorial/1");
            } else {
              this.$router.push("/home");
            }
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
@media only screen and (min-width: 576px) {
  .register-form {
    padding: 1rem 1rem;
  }

  .line {
    display: block;
  }
}

@media only screen and (min-width: 768px) {
  .register-form {
    padding: 1rem 1rem;
  }

  .line {
    display: block;
  }
}

@media only screen and (min-width: 992px) {
  .register-form {
    padding: 1rem 11rem;
  }
}

@media only screen and (min-width: 1200px) {
  .register-form {
    padding: 1rem 11rem;
  }
}

@media only screen and (min-width: 1400px) {
  .register-form {
    padding: 1rem 11rem;
  }
}

.nav-link {
  font-size: 1.5rem;
  border: 0;
}

.line {
  content: " ";
  height: 30px;
  width: 1px;
  position: relative;
  right: 0px;
  top: 14px;
  background: #dee2e6;
}

.error-feedback {
  color: red;
}
</style>