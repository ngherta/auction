<template>
  <div class="border container-lg p-5 mb-5">
    <Icon name="profile-account-overview" :size="1.3"/>
    <h2 class="h2 mt-2">Edit details</h2>
    <p class="mt-2">Feel free to edit any of your details below.</p>
    <div class="mt-4">
      <Form @submit="handleUpdate" :validation-schema="schema">
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
            <Datetimepicker v-model="date"
                            class=""
                            :enableTimePicker="false"
                            id="birthday"/>
          </div>
          <div class="mb-3">
            <label for="email">EMAIL:</label>
            <Field :value=user.email name="email" id="email" type="email" class="form-control"/>
            <ErrorMessage name="email" class="error-feedback"/>
          </div>

          <div class="">
            <button class="btn btn-primary btn-block" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
              Update
            </button>
          </div>
        </div>
      </Form>
    </div>
    <div class="d-flex justify-content-center mt-3">
      <router-link @click="this.$emit('handleChangePage', 'account/password')"
                   to="/profile/account/password">Do you want to change password?</router-link>
    </div>
  </div>
</template>

<script>
import Icon from "../../../components/Icon";
import Datetimepicker from 'vue3-date-time-picker';
import 'vue3-date-time-picker/dist/main.css';
import {Form, Field, useForm, ErrorMessage} from "vee-validate";
import * as yup from "yup";
import {ref} from 'vue'
import UserService from '../../../services/user.service';

export default {
  name: "Account",
  components: {
    Form,
    Field,
    ErrorMessage,
    Icon,
    Datetimepicker,
  },
  setup() {

    return {
      date: ref(new Date()),
    };
  },
  data() {
    const schema = yup.object().shape({
      // firstName: yup
      //     .string()
      //     .required("Username is required!")
      //     .min(3, "Must be at least 3 characters!")
      //     .max(20, "Must be maximum 20 characters!"),
      // lastName: yup
      //     .string()
      //     .required("Username is required!")
      //     .min(3, "Must be at least 3 characters!")
      //     .max(20, "Must be maximum 20 characters!"),
      // email: yup
      //     .string()
      //     .required("Email is required!")
      //     .email("Email is invalid!")
      //     .max(50, "Must be maximum 50 characters!"),
      // password: yup
      //     .string()
      //     .required("Password is required!")
      //     .min(6, "Must be at least 6 characters!")
      //     .max(40, "Must be maximum 40 characters!"),
      // // birthday: yup
      // //     .string()
      // //     .required("Birthday is required!")
    });

    return {
      successful: false,
      loading: false,
      schema,
      user: {
        id: '',
        firstName: '',
        lastName: '',
        email: '',
        birthday: ''
      },
    };
  },
  methods: {
    handleUpdate(user) {
      this.successful = false;
      this.loading = true;
      user.birthday = this.date;
      console.log(user);

      this.$store.dispatch("users/update", user).then(
          (response) => {
            this.successful = true;
            this.loading = false;

            let parts = response.data.birthday.split("-");
            this.date = new Date('20' + parseInt(parts[2], 10),
                parseInt(parts[1], 10) - 1,
                parseInt(parts[0], 10));

            this.user = {
              id: response.data.id,
              firstName: response.data.firstName,
              lastName: response.data.lastName,
              email: response.data.email,
              birthday: this.date
            }
            this.initFields();
          },
          (error) => {
            this.$notify({
              text: error.errorMessage,
              type: 'error'
            });
            this.successful = false;
            this.loading = false;
          }
      );
    },
    initFields() {
      // document.getElementById('firstName').value = this.user.firstName;
      // document.getElementById('lastName').value = this.user.lastName;
      // document.getElementById('email').value = this.user.email;
      // document.getElementById('firstName').value = this.user.firstName;

    },
    getUserData() {
      const userId = this.$store.state.auth.user.userDto.id;
      UserService.getUserById(userId).then(
          (response) => {
            let parts = response.data.birthday.split("-");
            this.date = new Date('20' + parseInt(parts[2], 10),
                parseInt(parts[1], 10) - 1,
                parseInt(parts[0], 10));

            this.user = {
              id: response.data.id,
              firstName: response.data.firstName,
              lastName: response.data.lastName,
              email: response.data.email,
              birthday: this.date
            }
            useForm({
              initialValues: this.user,
            });
            this.initFields();
          },
          (error) => {
            this.$notify({
              text: error.errorMessage,
              type: 'error'
            });
          }
      )
    },
  },
  mounted() {
    this.getUserData();
  }
}
</script>

<style scoped>

</style>