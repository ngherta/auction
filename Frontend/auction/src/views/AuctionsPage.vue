<template>
  <div class="container">
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
  <div>
    <ul>
      <li v-for="(category, index) in categories" :key="index">
        {{ category.mainCategory.categoryName }}
        <ul>
          <li v-for="(subCategory, index) in category.listSubCategories"
              :key="index">
            {{ subCategory.categoryName }}
          </li>
        </ul>
      </li>
    </ul>
  </div>
  <div class="d-flex">
    <AuctionItem v-for="(auction,index) in auctions"
                 :key="index"
                 :id="auction.id"
                 :title="auction.title"
                 :finish-price="auction.finishPrice"
                 :image="auction.images[0]"
                 :last-bid="getLastBidById(auction.id)"/>
  </div>
  <nav aria-label="Page navigation example">
    <ul class="pagination">
      <li class="page-item">
        <a class="page-link" href="#" aria-label="Previous">
          <span aria-hidden="true">&laquo;</span>
        </a>
      </li>
      <li v-for="index in countOfPages" :key="index"
          :class="{active: this.page == index}"
          :aria-current="{page: this.page == index}"
          class="page-item">
        <a @click="changePage(index)"
           class="page-link"
           href="#">{{ index }}</a>
      </li>
      <li class="page-item">
        <a class="page-link" href="#" aria-label="Next">
          <span aria-hidden="true">&raquo;</span>
        </a>
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

export default {
  name: "AuctionsPage",
  components: {
    Field,
    Form,
    AuctionItem,
  },
  data() {
    return {
      successful: false,
      loading: false,
      searchResultEmpty: true,
      auctions: [],
      page: 1,
      perPage: 5,
      countOfPages: '',
      searchInput: null,
      lastBids: [{
        auctionId: '',
        bid: ''
      }],
      categories: [],
    }
  },
  methods: {
    searchFromInput(input) {
      this.searchInput = input.search;
      this.search(this.searchInput);
      this.$router.push("/auctions/search/" + this.searchInput);
    },
    search(text) {
      AuctionService.search(text).then(
          (response) => {
            console.log(response.data);
            if (response.data.totalElements != 0) {
              this.searchResultEmpty = false;
              this.auctions = response.data.content;
              this.countOfPages = response.data.totalPages;
              this.getLastBids();
            } else if (response.data.totalElements == 0) {
              this.auctions = [];
              this.searchResultEmpty = true;
            }
          },
          (error) => {
            this.$notify({
              text: error.message,
              type: 'error'
            })
          }
      )
    },
    changePage(index) {
      this.page = index;
      this.getAuctions();
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
            console.log(response.data);
            this.categories = response.data;
          }
      )
    },
    getAuctions() {
      AuctionService.getAll(this.page, this.perPage).then(
          (response) => {
            console.log(response.data);
            this.auctions = response.data.content;
            this.countOfPages = response.data.totalPages;
            this.getLastBids();
          },
          (error) => {
            this.$notify({
              text: error.message,
              type: 'error'
            });
          }
      );
    },
    getLastBids() {
      let auctionIds = [];
      console.log(this.auctions.length)
      console.log(this.auctions[0].id)
      for (let i = 0; i < this.auctions.length; i++) {
        auctionIds.push(this.auctions[i].id);
      }

      BettingService.getLastBids(auctionIds).then(
          (response) => {
            this.lastBids = response.data;
          },
          (error) => {
            this.$notify({
              text: error.message,
              type: 'error'
            })
          }
      );
    }
  },
  mounted() {
    if (this.$route.path.includes("/auctions/search/")) {
      this.searchInput = this.$route.params.text;
      console.log(this.searchInput);
      this.search(this.searchInput);
    }
    else {
      this.getAuctions();
    }
    this.getCategories();
  }
}
</script>

<style scoped>

</style>