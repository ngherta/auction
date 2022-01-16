<template>
  <div class="container">
    <h1 class="h1">Create auction</h1>
    <div>{{ this.$store.state.imageLink }}</div>
    <div>{{ this.images }}</div>


    <div>
      <Form @submit="createAuction" :validation-schema="schema">
        <div v-if="!successful" class="row">
          <div class="mb-3 col-4">
            <label for="title">TITLE:</label>
            <Field name="title" id="title" type="text" class="form-control"/>
            <ErrorMessage name="title" class="error-feedback"/>
          </div>
          <div class="mb-3 col-4">
            <label for="description">DESCRIPTION:</label>
            <Field name="description" id="description" type="text" class="form-control"/>
            <ErrorMessage name="description" class="error-feedback"/>
          </div>
          <div class="mb-3 col-4">
            <label for="startPrice">START PRICE:</label>
            <Field name="startPrice" id="startPrice" type="number" class="form-control"/>
            <ErrorMessage name="startPrice" class="error-feedback"/>
          </div>
          <div class="mb-3 col-4">
            <label for="category">CATEGORY:</label>
            <select class="custom-select" id="category" v-model="selectedCategories">
              <option v-for="category in categories"
                      :key="category.id"
                      :value="category.id"
                      :disabled="category.type == 'CATEGORY'"
                      :class="{ category: category.type == 'CATEGORY' }">
                {{ category.name }}
              </option>
            </select>
          </div>
          <div class="mb-3 col-4">
            <div class="custom-control custom-checkbox">
              <input @click="toggle()" type="checkbox" checked class="custom-control-input" id="customCheck1">
              <label class="custom-control-label" for="customCheck1">Do you want to have maximum price for
                auction?</label>
            </div>
            <div v-if="isCheckedFinishPrice">
              <label for="finishPrice">FINISH PRICE:</label>
              <Field name="finishPrice" id="finishPrice" type="number" class="form-control"/>
              <ErrorMessage name="finishPrice" class="error-feedback"/>
            </div>
          </div>
<!--          TODO: add styles because we cant use .form-control-->
          <div class="mb-3">
            <label for="startDate">START DATE:</label>
            <Datetimepicker v-model="startDate"
                            class=""
                            id="startDate"/>

          </div>
          <div class="mb-3">
            <label for="finishDate">FINISH DATE:</label>
            <Datetimepicker v-model="finishDate"
                            id="finishDate"/>

          </div>
          <UploadFiles @uploadNewImages="uploadNewImages($event)"/>

          <div class="">
            <button class="btn btn-primary btn-block" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
              Create
            </button>
          </div>
        </div>
      </Form>
    </div>
  </div>
</template>

<script>
import {ErrorMessage, Field, Form} from "vee-validate";
import * as yup from "yup";
import CategoryService from "../services/category.service"
import UploadFiles from "../views/UploadFiles";
import AuctionService from "../services/auction.service";
import Datetimepicker from 'vue3-date-time-picker';
import 'vue3-date-time-picker/dist/main.css'

export default {
  name: "CreateAuctionPage",
  components: {
    Form,
    Field,
    ErrorMessage,
    UploadFiles,
    Datetimepicker,
  },
  data() {
    const schema = yup.object().shape({});
    return {
      successful: false,
      loading: false,
      message: "",
      schema,
      isCheckedFinishPrice: true,
      categories: [],
      selectedCategories: [],
      images: [],
      startDate: null,
      finishDate: null,
    };
  },
  methods: {
    createAuction(data) {
      const auction = {
        title: data.title,
        description: data.description,
        startPrice: data.startPrice,
        finishPrice: data.finishPrice,
        categoryIds: this.selectedCategories,
        images: this.images,
        userId: this.$store.state.auth.user.userDto.id,
        startDate: this.startDate,
        finishDate: this.finishDate,
      }
      console.log(this.$store.state.auth.user);
      console.log(auction);
      AuctionService.create(auction).then(
          (response) => {
            console.log(response);
          },
          (error) => {
            this.$notify({
              text: error.message,
              type: 'error'
            });
          }
      )
    },
    toggle() {
      this.isCheckedFinishPrice = !this.isCheckedFinishPrice;
    },
    getCategories() {
      CategoryService.getCategoriesForCreateAuction().then(
          (response) => {
            this.prepareCategories(response.data);
          }
      )
    },
    uploadNewImages(event) {
      this.images.push(event);
    },
    prepareCategories(categories) {
      for (let i = 0; i < categories.length; i++) {
        this.categories.push({
          id: categories[i].mainCategory.id,
          type: categories[i].mainCategory.type,
          name: categories[i].mainCategory.categoryName
        });
        for (let q = 0; q < categories[i].listSubCategories.length; q++) {
          this.categories.push({
            id: categories[i].listSubCategories[q].id,
            type: categories[i].listSubCategories[q].type,
            name: categories[i].listSubCategories[q].categoryName
          });
        }
      }
    }
  },
  mounted() {
    this.getCategories();
  }
}
</script>

<style scoped>
.category {
  background-color: #9c9c9c;
}
</style>