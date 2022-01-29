<template>
  <div class="border container-lg p-5 mb-5">
    <Icon name="profile-account-overview" :size="1.3"/>
    <h2 class="h2 mt-2">Change password</h2>
    <p class="mt-2">Feel free to change your password.</p>
    <div class="mt-4">
      <Form @submit="handleUpdate" :validation-schema="schema">
        <div v-if="!successful">
          <div class="mb-3">
            <label for="currentPassword">CURRENT PASSWORD:</label>
            <Field name="currentPassword" id="currentPassword" type="text" class="form-control"/>
            <ErrorMessage name="currentPassword" class="error-feedback"/>
          </div>
          <div class="mb-3">
            <label for="newPassword">NEW PASSWORD:</label>
            <Field name="newPassword" id="newPassword" type="text" class="form-control"/>
            <ErrorMessage name="newPassword" class="error-feedback"/>
          </div>
          <div class="mb-3">
            <label for="confirmNewPassword">CONFIRM NEW PASSWORD:</label>
            <Field name="confirmNewPassword" id="confirmNewPassword" type="text" class="form-control"/>
            <ErrorMessage name="confirmNewPassword" class="error-feedback"/>
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
        <div v-if="successful">
          <p class="text-center">SUCCESS</p>
        </div>
      </Form>
    </div>
  </div>
</template>

<script>
import Icon from "../../../components/Icon";
import {Form, Field, ErrorMessage} from "vee-validate";
import * as yup from "yup";
import UserService from '../../../services/user.service';

export default {
  name: "ChangePasswordComponent",
  components: {
    Form,
    Field,
    ErrorMessage,
    Icon,
  },
  data() {
    const schema = yup.object().shape({
      confirmNewPassword: yup
          .string()
          .required("Password is required!")
          .min(6, "Must be at least 6 characters!")
          .max(40, "Must be maximum 40 characters!"),
      newPassword: yup
          .string()
          .required("Password is required!")
          .min(6, "Must be at least 6 characters!")
          .max(40, "Must be maximum 40 characters!"),
      currentPassword: yup
          .string()
          .required("Password is required!")
          .min(6, "Must be at least 6 characters!")
          .max(40, "Must be maximum 40 characters!"),
    });

    return {
      successful: false,
      loading: false,
      schema,
      userId: this.$store.state.auth.user.userDto.id
    };
  },
  methods: {
    handleUpdate(data) {
      this.successful = false;
      this.loading = true;

      if (data.newPassword !== data.confirmNewPassword) {
        this.$notify({
          text: "Confirm password is wrong",
          type: "error"
        });
        this.loading = false;
        return;
      }

      UserService.updatePassword(
          this.userId,
          data.currentPassword,
          data.newPassword)
          .then(
              () => {
                this.successful = true;
                this.loading = false;

              },
              (error) => {
                this.$notify({
                  text: error.response.data.errorMessage,
                  type: 'error'
                });
                this.successful = false;
                this.loading = false;
              }
          );
    }
  },
  mounted() {
  }
}
</script>

<style scoped>
.error-feedback {
  color: red;
}
</style>