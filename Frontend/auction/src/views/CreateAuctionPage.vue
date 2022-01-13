<template>
  <div class="container">
    <h1 class="h1">Create auction</h1>
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
            <!--            <v-select-->
            <!--                placeholder="Choose a category"-->
            <!--                label="category"-->
            <!--                :options="categories"-->
            <!--                :selectable="(option) => option.type == 'SUB_CATEGORY'"-->
            <!--            />-->
            <select class="custom-select">
              <option v-for="category in categories"
                      :key="category.id"
                      value={{category.id}}
                      :disabled="category.type == 'CATEGORY'"
                      v-bind:class="{ category: category.type == 'CATEGORY' }">
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

          <div class="">
            <button class="btn btn-primary btn-block" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
              Create
            </button>
          </div>
          <UploadFiles v-bind="imagesData"/>
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

export default {
  name: "CreateAuctionPage",
  components: {
    Form,
    Field,
    ErrorMessage,
    UploadFiles,
  },
  data() {
    const schema = yup.object().shape({});
    return {
      successful: false,
      imagesData : [],
      loading: false,
      message: "",
      schema,
      isCheckedFinishPrice: true,
      categories: [],
      timer: null
    };
  },
  methods: {
    createAuction(auction) {
      console.log("Create auction! Content : " + JSON.stringify(auction));
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
    prepareCategories(categories) {
      console.log(categories);
      console.log(this.categories);
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
        console.log(this.categories);
      }
    }
  },
  mounted() {
    this.timer = setInterval(() => {
      console.log(UploadFiles.data());
    }, 1000),
    this.getCategories();
  }
}
</script>

<style scoped>
.category {
  background-color: #9c9c9c;
}
</style>