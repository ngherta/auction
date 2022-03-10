<template>
  <div class="container mt-5 mb-5">
    <h1 class="h1 mb-4">Create auction</h1>
    <div>
      <Form @submit="createAuction" :validation-schema="schema">
        <div v-if="!successful" class="row">
          <div class="col">
            <div class="mb-3">
              <label for="title">TITLE:</label>
              <Field name="title" id="title" type="text" class="form-control"/>
              <ErrorMessage name="title" class="error-feedback"/>
            </div>
            <div class="mb-3">
              <label for="description">DESCRIPTION:</label>
              <editor @handleChangeValue="handleDescriptionInput($event)" id="description"/>
            </div>
            <div class="mb-3">
              <label for="startPrice">START PRICE:</label>
              <Field name="startPrice" id="startPrice" type="number" class="form-control"/>
              <ErrorMessage name="startPrice" class="error-feedback"/>
            </div>
            <div class="mb-3">
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
            <div class="mb-3">
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

            <!--          TODO: add styles because we cant use .form-control-->
            <div class="mb-3">
              <label for="startDate">START DATE:</label>
              <Datetimepicker v-model="startDate"
                              class=""
                              id="startDate"
                              :minDate="new Date()"/>

            </div>
            <div class="mb-3">
              <label for="finishDate">FINISH DATE:</label>
              <Datetimepicker v-model="finishDate"
                              id="finishDate"
                              :minDate="startDate"/>

            </div>
            <div class="mb-3">
              <div class="form-check form-switch">
                <input class="form-check-input" v-model="isCharity" type="checkbox" role="switch" id="isCharity">
                <label class="form-check-label" for="isCharity">Do you want to donate 10% of the amount to
                  charity?</label>
              </div>
            </div>
          </div>
          <div class="col">
            <upload-image show-results="true"
                          ratio=5/6
                          result-width="300"
                          result-height="360"
                          @uploadNewImages="uploadNewImages($event)"/>
          </div>
        </div>
        <div class="row">
          <button class="btn btn-primary btn-block" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
            Create
          </button>
        </div>
      </Form>
    </div>
  </div>
</template>

<script>
import {ErrorMessage, Field, Form} from "vee-validate";
import * as yup from "yup";
import CategoryService from "../services/category.service"
import UploadImage from "@/views/UploadImage";
import AuctionService from "../services/auction.service";
import Datetimepicker from 'vue3-date-time-picker';
import 'vue3-date-time-picker/dist/main.css'
import Editor from "../components/Editor";

export default {
  name: "CreateAuctionPage",
  components: {
    Form,
    Field,
    ErrorMessage,
    UploadImage,
    Datetimepicker,
    Editor,
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
      isCharity: false,
      descriptionValue: "",
    };
  },
  methods: {
    handleDescriptionInput(event) {
      this.descriptionValue = event;
    },
    createAuction(data) {
      this.successful = false;
      this.loading = true;
      if (this.images.length == 0) {
        this.$notify({
          text: 'You should add an image!',
          type: 'error'
        });
        return;
      }

      const auction = {
        title: data.title,
        description: this.descriptionValue,
        startPrice: data.startPrice,
        finishPrice: data.finishPrice,
        categoryIds: this.selectedCategories,
        images: this.images,
        userId: this.$store.state.auth.user.userDto.id,
        startDate: this.startDate,
        finishDate: this.finishDate,
        auctionType: this.getAuctionTypeFromInput(),
        charityPercent: 10
      }

      AuctionService.create(auction).then(
          () => {
            this.successful = true;
            this.loading = false;
            this.$router.push("/auctions");
          },
          (error) => {
            this.$notify({
              type: 'error',
              text: error.response.data.errorMessage
            });
          }
      )
      this.loading = false;
    },
    getAuctionTypeFromInput() {
      console.log(this.isCharity);
      if (this.isCharity) {
        return 'CHARITY';
      } else if (!this.isCharity) {
        return 'COMMERCIAL';
      }
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