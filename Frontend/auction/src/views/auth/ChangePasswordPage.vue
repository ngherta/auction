<template>
  <Main_Logo class="mt-5 mb-5"></Main_Logo>
  <div class="container border mb-4">
    <div class="container p-0">
      <div class="row">
        <div class="col-sm border-bottom p-0">
          <router-link to="/register" class="nav-link text-center list-group-item list-group-item-action">SIGN UP</router-link>
        </div>
        <div class="line"></div>
        <div class="col-sm border-bottom p-0">
          <router-link to="/login" class="nav-link text-center list-group-item list-group-item-action">SIGN IN</router-link>
        </div>
      </div>
    </div>
    <div class="container register-form pt-5 pb-5">
      <div>
        <h2 class="h2">
          Change your password
        </h2>
        <p>
          Type in your new password below!
        </p>
      </div>
      <Form @submit="handleUpdatePassword" :validation-schema="schema">
        <div v-if="!successful">
          <div class="mb-3">
            <label for="password">Password:</label>
            <Field name="password" id="password" type="password" class="form-control" />
            <ErrorMessage name="password" class="error-feedback" />
          </div>

          <div class="">
            <button class="btn btn-primary btn-block" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
              RESET A PASSWORD
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
import Main_Logo from "@/components/Main_Logo";

export default {
  name: "ChangePasswordPage",
  components: {
    Form,
    Field,
    ErrorMessage,
    Main_Logo,
  },
  data() {
    const schema = yup.object().shape({
      password: yup.string().required("Password is required!"),
    });

    return {
      successful: false,
      loading: false,
      message: "",
      code: this.$route.params.id,
      schema,
      request: {
        code : this.$route.params.id,
        password : "",
      }
    };
  },
  methods: {
    disableUserAfterReset() {
      console.log("Try!!!")
      this.$store.dispatch("users/disableUserAfterReset", this.code).then(
          () => {
            console.log("Disable: code[" + this.code + "]");
          },
          (error) => {
            this.loading = false;
            console.log(error);
            this.$router.push("/error")
          });
    },
    handleUpdatePassword(data) {
      this.loading = true;

      this.request.password = data.password;

      this.$store.dispatch("users/updatePassword", this.request).then(
          () => {
            console.log("Change password...")
            this.$router.push("/login");
          },
          (error) => {
            this.loading = false;
            this.message =
                (error.response &&
                    error.response.data &&
                    error.response.data.message) ||
                error.message ||
                error.toString();
          });
    }
  },
  mounted() {
    this.disableUserAfterReset();
    console.log("hello!")
  }
}
</script>

<style scoped>

@media only screen and (min-width: 576px) {
  .register-form {
    padding: 1rem 1rem;
  }

  .line {
    display: none;
  }
}

@media only screen and (min-width: 768px) {
  .register-form {
    padding: 1rem 1rem;
  }

  .line {
    display: none;
  }
}

@media only screen and (min-width: 992px) {
  .register-form {
    padding: 1rem 11rem;
  }

  .line {
    display: block;
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

.line {
  content: " ";
  height: 30px;
  width: 1px;
  position: relative;
  right: 0px;
  top: 14px;
  background: #dee2e6;
}

.nav-link {
  font-size: 1.5rem;
  border: 0;
}
</style>