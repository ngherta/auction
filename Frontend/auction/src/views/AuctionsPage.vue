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
  <div class="border d-flex">
    <ul>
      <li v-for="(category, index) in categories" :key="index">
        <div class="d-flex align-items-center" @click="handleClickOnCategory(category.mainCategory.id)">
          {{ category.mainCategory.categoryName }}
          <Icon name="row" :class="{ rotate : !this.clickedCategories.includes(category.mainCategory.id) }"></Icon>
        </div>
        <ul v-if="this.clickedCategories.includes(category.mainCategory.id)">
          <li v-for="(subCategory, index) in category.listSubCategories"
              class="d-flex justify-content-between"
              :key="index">
            <label class="form-check-label" :for="subCategory.id">
              {{ subCategory.categoryName }}
            </label>
            <input
                @click="addOrRemoveSubCategory(subCategory.id)"
                class="form-check-input"
                type="checkbox"
                :id="subCategory.id"
                :value="subCategory.id"
                :checked="isActiveSubCategory(subCategory.id)">
          </li>
        </ul>
      </li>
    </ul>
  </div>
  <div class="d-flex container">
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
import FilterService from '../services/filter.service';
import Icon from '../components/Icon';

export default {
  name: "AuctionsPage",
  components: {
    Field,
    Form,
    AuctionItem,
    Icon,
  },
  data() {
    return {
      successful: false,
      loading: false,
      searchResultEmpty: true,
      auctions: [],
      page: 1,
      perPage: 1,
      countOfPages: '',
      filter: '',
      lastBids: [{
        auctionId: '',
        bid: ''
      }],
      categories: [],
      activeCategories: [],
      clickedCategories: [],
    }
  },
  methods: {
    searchFromInput(input) {
      this.filter = FilterService.equals(this.filter, 'title', input.search);
      this.getAuctions();
      this.$router.push("/auctions/" + this.filter);
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
    }
    ,
    getLastBidById(auctionId) {
      let bid = 0;
      for (let i = 0; i < this.lastBids.length; i++) {
        if (this.lastBids[i].auctionId == auctionId) {
          bid = this.lastBids[i].bid;
        }
      }
      return bid;
    }
    ,
    isActiveSubCategory(subCategory) {
      for (let i = 0; i < this.activeCategories.length; i++) {
        if (subCategory == this.activeCategories[i]) {
          return true;
        }
      }
      return false;
    }
    ,
    getCategories() {
      CategoryService.getCategoriesForCreateAuction().then(
          (response) => {
            this.categories = response.data;
          }
      )
    }
    ,
    handleClickOnCategory(categoryId) {
      if (this.clickedCategories.includes(categoryId)) {
        let index = this.clickedCategories.indexOf(categoryId);
        this.clickedCategories.splice(index, 1);
      } else {
        this.clickedCategories.push(categoryId);
      }
    }
    ,
    addOrRemoveSubCategory(subCategory) {
      const cb = document.querySelector("[id='" + subCategory + "']");

      if (!cb.checked) {
        this.removeSubCategoryFromFilter(subCategory);
      } else {
        this.addSubCategoryToFilter(subCategory);
      }
      this.getAuctions();
      this.$router.push("/auctions/" + this.filter);

    }
    ,
    addSubCategoryToFilter(subCategory) {
      this.filter = FilterService.in(this.filter, 'categories', subCategory);
    }
    ,
    removeSubCategoryFromFilter(subCategory) {
      this.filter = FilterService.removeSubCategory(this.filter, subCategory);
    }
    ,
    getAuctions() {
      let filter = FilterService.prepareFilterForRequest(this.filter);
      AuctionService.filter(filter, this.page, this.perPage).then(
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
          },
          (error) => {
            // Add router to not found page
            this.$notify({
              text: error.response.data.errorMessage,
              type: 'error'
            });
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
    if (this.$route.params.filter) {
      this.filter = this.$route.params.filter;
      this.activeCategories = FilterService.getCategories(this.filter);
      console.log(this.activeCategories);
    }

    this.getAuctions();
    this.getCategories();
  }
}
</script>

<style scoped>
.rotate {
  transform: rotate(180deg);
}
</style>