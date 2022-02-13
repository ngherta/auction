<template>
  <div class="container">
    <div class="">
      <div class="search mt-5 mb-5">
        <Form @submit="searchFromInput">
          <div class="d-flex justify-content-center">
            <div class="w-50 mr-5">
              <label for="search" hidden>Search</label>
              <Field name="search" id="search" type="text" class="form-control"/>
            </div>
            <div class="">
              <button class="btn btn-primary btn-block" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
                Search
              </button>
            </div>
          </div>
        </Form>
      </div>
    </div>
    <div class="d-flex mb-5">
      <div>
        <router-link class="btn btn-success" to="/auction/create">Create new auction</router-link>
      </div>
      <div class="w-20 ml-auto mr-4">
        <Multiselect
            @select="getAuctions()"
            @deselect="getAuctions()"
            v-model="categoryValue"
            placeholder="Filter by category"
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
      <div class="w-20">
        <Multiselect
            @select="getAuctions()"
            @deselect="getAuctions()"
            v-model="statusValue"
            placeholder="Filter by status"
            :options="statusesOptions"
            :loading="true"
            :search="true"
            :hideSelectedTag="true"
            mode="multiple"
            :closeOnSelect="false"
            :hideSelected="false"
        />
      </div>
    </div>
    <div class="d-flex flex-wrap mb-5">
      <div class="m-auto"
           v-if="this.loadingAuctions == true && this.searchResultEmpty == false">
        <div class="spinner-border mt-5 mb-5" style="width: 4rem; height: 4rem;" role="status">
          <span class="sr-only">Loading...</span>
        </div>
      </div>
      <h2 v-else-if="this.searchResultEmpty" class="h2 text-center">No auctions found</h2>
      <AuctionItem v-for="(auction,index) in auctions"
                   class="mr-5 mb-5"
                   :key="index"
                   :id="auction.id"
                   :title="auction.title"
                   :finish-price="auction.finishPrice"
                   :image="auction.images[0]"
                   :last-bid="getLastBidById(auction.id)"/>
    </div>
  </div>
  <nav v-if="this.loadingAuctions && this.searchResultEmpty"
       aria-label="Page navigation example"
       class="d-flex justify-content-center mt-5 mb-3">
    <ul class="pagination ">
      <li class="page-item list-unstyled">
        <router-link class="page-link" to="/auctions"
                     @click="changePage(this.page-1)"
                     aria-label="Previous">
          <span aria-hidden="true">&laquo;</span>
        </router-link>
      </li>
      <li v-for="index in countOfPages" :key="index"
          :class="{active: this.page == index,
                  'd-none': !isPageForRender(index)}"
          :aria-current="{page: this.page == index}"
          class="page-item">
        <router-link @click="changePage(index)"
                     class="page-link"
                     to="/auctions">{{ index }}
        </router-link>
      </li>
      <li class="page-item">
        <router-link class="page-link" to="/auctions"
                     @click="changePage(this.page+1)"
                     aria-label="Next">
          <span aria-hidden="true">&raquo;</span>
        </router-link>
      </li>
    </ul>
  </nav>
</template>

<script>
import {Field, Form} from "vee-validate";
import AuctionService from '../services/auction.service';
import AuctionItem from '../components/AuctionItem'
import BettingService from '../services/betting.service';
import CategoryService from '../services/category.service';
import Multiselect from '@vueform/multiselect';
import '@vueform/multiselect/themes/default.css';


export default {
  name: "AuctionsPage",
  components: {
    Field,
    Form,
    AuctionItem,
    Multiselect,
  },
  data() {
    return {
      successful: false,
      loading: false,
      searchResultEmpty: false,
      auctions: [],
      loadingAuctions: null,
      categoryValue: [],
      statusValue: [],
      searchTitle: "",
      page: 1,
      perPage: 5,
      countOfPages: '',
      lastBids: [{
        auctionId: '',
        bid: ''
      }],
      categories: [],
      statusesOptions: ['EXPECTATION', 'ACTIVE', 'FINISHED'],
      categoriesOptions: []
    }
  },
  methods: {
    searchFromInput(input) {
      console.log(input);
      this.searchTitle = input.search;
      this.getAuctions();
    },
    isPageForRender(index) {
      //index < this.page - 4 || index > this.page + 5 }
      if (this.page < 5) {
        if (index < 9) {
          return true;
        } else {
          return false;
        }
      } else {
        if (index < this.page - 4 || index > this.page + 5) {
          return false;
        } else {
          return true;
        }
      }
    },
    changePage(index) {
      this.page = index;
      this.getAuctions();
      window.scrollTo(0, 0);
    },
    getLastBidById(auctionId) {
      let bid = 0;
      for (let i = 0; i < this.lastBids.length; i++) {
        if (this.lastBids[i].auctionId == auctionId) {
          bid = this.lastBids[i].bid;
        }
      }
      return bid;
    },
    getCategories() {
      CategoryService.getCategoriesForCreateAuction().then(
          (response) => {
            this.categories = response.data;
            console.log(this.categories);
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
    getAuctions() {
      this.auctions = [];
      this.loadingAuctions = true;
      AuctionService
          .filter(this.page,
              this.perPage,
              this.categoryValue.toString(),
              this.searchTitle,
              this.statusValue.toString())
          .then(
              (response) => {
                if (response.data.totalElements != 0) {
                  this.searchResultEmpty = false;
                  this.auctions = response.data.content;
                  this.countOfPages = response.data.totalPages;
                  this.getLastBids();
                } else if (response.data.totalElements == 0) {
                  this.auctions = [];
                  this.searchResultEmpty = true;
                }
                this.loadingAuctions = false;
              },
              (error) => {
                // Add router to not found page
                this.$notify({
                  text: error.response.data.errorMessage,
                  type: 'error'
                });
                this.loadingAuctions = false;
              }
          );
    }
    ,
    getLastBids() {
      let auctionIds = [];
      for (let i = 0; i < this.auctions.length; i++) {
        auctionIds.push(this.auctions[i].id);
      }

      BettingService.getLastBids(auctionIds).then(
          (response) => {
            this.lastBids = response.data;
          },
          (error) => {
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            })
          }
      );
    }
  },
  mounted() {
    this.getAuctions();
    this.getCategories();
  }
}
</script>

<style scoped>
.w-20 {
  width: 20%;
}
</style>