<template>
  <div class="container mt-5 mb-5">
    <h1 class="h1 mb-4">Create auction</h1>
    <div>
      <Form @submit="createAuction" :validation-schema="schema">
        <div v-if="!successful" class="row creation-block mb-3">
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
              <div class="input-group">
                <Field name="startPrice" id="startPrice" type="number" class="form-control"/>
                <div class="input-group-append">
                  <span class="input-group-text" id="basic-addon3">$</span>
                </div>
              </div>
              <ErrorMessage name="startPrice" class="error-feedback"/>
            </div>
            <div class="mb-3">
              <div class="custom-control custom-checkbox">
                <input @click="toggle()" type="checkbox" checked class="custom-control-input" id="customCheck1">
                <label class="custom-control-label" for="customCheck1">Do you want to have maximum price for
                  auction?</label>
              </div>
              <div v-if="isCheckedFinishPrice" class="mt-2">
                <label for="finishPrice">FINISH PRICE:</label>
                <div class="input-group">
                  <Field name="finishPrice" id="finishPrice" type="number" class="form-control"/>
                  <div class="input-group-append">
                    <span class="input-group-text" id="basic-addon4">$</span>
                  </div>
                </div>
                <ErrorMessage name="finishPrice" class="error-feedback"/>
              </div>
            </div>
            <div class="mb-3">
              <label for="categories">CATEGORIES:</label>
              <Multiselect
                  id="categories"
                  v-model="categoryValue"
                  placeholder="Select category"
                  :options="categoriesOptions"
                  :loading="true"
                  :search="true"
                  :hideSelectedTag="true"
                  mode="multiple"
                  :closeOnSelect="false"
                  :hideSelected="false"
                  :searchable="true"
              />
            </div>
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
            <label for="finishDate">Upload an image:</label>
            <upload-image :showResults="true"
                          ratio=5/6
                          result-width="300px"
                          result-height="360px"
                          @uploadNewImages="uploadNewImages($event)"/>
            <div v-if="images.length > -1" class="d-flex">
              <section v-for="(result, index) in images" :key="result">
                <img class="mr-2" :src="result" height="100" :alt="'image number ' + index"/>
              </section>
            </div>
          </div>
        </div>
        <div class="row">
          <button class="btn btn-primary btn-block" :disabled="loading ||images.length == 0">
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
import Datetimepicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import Editor from "../components/Editor";
import Multiselect from '@vueform/multiselect';
import '@vueform/multiselect/themes/default.css';

export default {
  name: "CreateAuctionPage",
  components: {
    Form,
    Field,
    ErrorMessage,
    UploadImage,
    Datetimepicker,
    Editor,
    Multiselect
  },
  data() {
    const schema = yup.object().shape({});
    return {
      successful: false,
      loading: false,
      message: "",
      schema,
      isCheckedFinishPrice: true,
      images: [],
      startDate: new Date(),
      finishDate: new Date(),
      isCharity: false,
      descriptionValue: "",
      categoriesOptions: [],
      categoryValue: null
    };
  },
  methods: {
    handleDescriptionInput(event) {
      this.descriptionValue = event;
    },
    getCategories() {
      CategoryService.getCategoriesForCreateAuction().then(
          (response) => {
            this.categories = response.data;
            this.categoriesOptions = [];
            for (let i = 0; i < this.categories.length; i++) {
              this.categoriesOptions.push({
                label: this.categories[i].mainCategory.categoryName,
                value: this.categories[i].mainCategory.id,
                disabled: true
              });
              for (let q = 0; q < this.categories[i].listSubCategories.length; q++) {
                this.categoriesOptions.push({
                  label: this.categories[i].listSubCategories[q].categoryName,
                  value: this.categories[i].listSubCategories[q].id
                });
              }
            }
          }
      )
    },
    createAuction(data) {
      this.successful = false;
      console.log(this.startDate.getFullYear() + '-' + this.startDate.getMonth() + '-' + this.startDate.getDay() + 'T' + this.startDate.getHours() + ':' + this.startDate.getMinutes());
      console.log(this.finishDate)
      this.loading = true;
      if (this.images.length == 0) {
        this.$notify({
          text: 'You should add an image!',
          type: 'error'
        });
        return;
      }
      let charityPercentValue = 0;
      let type = this.getAuctionTypeFromInput();
      if (type != 'COMMERCIAL') {
        charityPercentValue = 10;
      }
      const auction = {
        title: data.title,
        description: this.descriptionValue,
        startPrice: data.startPrice,
        finishPrice: data.finishPrice,
        categoryIds: this.categoryValue,
        images: this.images,
        userId: this.$store.state.auth.user.userDto.id,
        startDate: this.startDate,
        finishDate: this.finishDate,
        auctionType: type,
        charityPercent: charityPercentValue
      }

      AuctionService.create(auction).then(
          () => {
            this.successful = true;
            this.loading = false;
            this.$router.push("/auctions");
          },
          (error) => {
            console.log(error.response)
            this.$notify({
              type: 'error',
              text: error.response.data.errorMessage
            });
          },
      )
      this.loading = false;
    },
    getAuctionTypeFromInput() {
      if (this.isCharity) {
        return 'CHARITY';
      } else  {
        return 'COMMERCIAL';
      }
    },
    toggle() {
      this.isCheckedFinishPrice = !this.isCheckedFinishPrice;
    },
    uploadNewImages(event) {
      this.images.push(event);
    }
  },
  mounted() {
    this.getCategories();
  }
}
</script>

<style scoped>
@media only screen and (max-width: 769px) {
  .creation-block {
    flex-direction: column;
  }
}

.category {
  background-color: #9c9c9c;
}
</style>