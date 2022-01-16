<template>
  <Main_Logo class="mt-5 mb-5"></Main_Logo>
  <div class="container border mb-4">
    <div class="container p-0">
      <div class="row">
        <div class="col-sm border-bottom border-info p-0">
          <router-link to="/register" class="nav-link text-center list-group-item list-group-item-action">SIGN UP
          </router-link>
        </div>
        <div class="line"></div>
        <div class="col-sm border-bottom p-0">
          <router-link to="/login" class="nav-link text-center list-group-item list-group-item-action">SIGN IN
          </router-link>
        </div>
      </div>
    </div>
    <div class="container register-form pt-5 pb-5">
      <Form @submit="handleRegister" :validation-schema="schema">
        <div v-if="!successful">
          <div class="mb-3">
            <label for="firstName">FIRST NAME:</label>
            <Field name="firstName" id="firstName" type="text" class="form-control"/>
            <ErrorMessage name="firstName" class="error-feedback"/>
          </div>
          <div class="mb-3">
            <label for="lastName">LAST NAME:</label>
            <Field name="lastName" id="lastName" type="text" class="form-control"/>
            <ErrorMessage name="lastName" class="error-feedback"/>
          </div>
          <div class="mb-3">
            <label for="birthday">BIRTHDAY:</label>
            <Datepicker class="form-control"
                        id="birthday"
                        v-model="date"
            />
          </div>
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
import {Form, Field, ErrorMessage} from "vee-validate";
import * as yup from "yup";
import Main_Logo from "@/components/Main_Logo";
import Datepicker from 'vue3-datepicker';
import {ref} from 'vue'


export default {
  name: "Register",
  components: {
    Form,
    Field,
    ErrorMessage,
    Main_Logo,
    Datepicker,
  },
  setup() {
    return {
      date: ref(new Date()),
    };
  },
  data() {
    const schema = yup.object().shape({
      firstName: yup
          .string()
          .required("Username is required!")
          .min(3, "Must be at least 3 characters!")
          .max(20, "Must be maximum 20 characters!"),
      lastName: yup
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
      // birthday: yup
      //     .string()
      //     .required("Birthday is required!")
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
      this.$router.push("/home");
    }
  },
  methods: {
    handleRegister(user) {
      this.message = "";
      this.successful = false;
      this.loading = true;
      user.birthday = this.date;

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

/*.nav-link:hover {*/
/*  background-color: lightgray;*/
/*  color: black;*/
/*}*/

.error-feedback {
  color: red;
}
</style>
