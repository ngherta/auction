<template>
  <div class="">
    <h3 v-if="this.$store.state.auth.status.loggedIn" class="text-center mb-4">MAKE A BID</h3>
    <h3 v-if="!this.$store.state.auth.status.loggedIn" class="text-center mb-4">List of bids:</h3>
    <div>
      <Form v-if="this.$store.state.auth.status.loggedIn" @submit="handleBet" :validation-schema="schema">
        <div class="row">
          <div class="col input-group">
            <Field @input="checkBetForChangeColor"
                   v-model="betInput"
                   name="bid"
                   id="bid"
                   type="number"
                   class="form-control"/>
            <div class="input-group-append">
              <div class="input-group-text"
                   :class="{
                  'text-red' : this.isWrongBet == true,
                  'text-green' : this.isWrongBet == false
                     }"
              >$
              </div>
            </div>
          </div>

          <div class="d-flex col" style="flex-basis: min-content">
            <button class="btn btn-primary btn-block mt-0 mr-3"
                    :disabled="loading || isWrongBet">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"></span>
              BID
            </button>
            <button class="btn btn-primary btn-block mt-0" :disabled="loading">
              <span
                  v-show="loading"
                  class="spinner-border spinner-border-sm"
              ></span>
              BUY NOW
            </button>
          </div>
        </div>
      </Form>
      <div class="custom-table mt-3">
        <table class="table table-bordered table-striped">
          <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Name</th>
            <th scope="col">Date</th>
            <th scope="col">Bid</th>
          </tr>
          </thead>
          <tbody class="">
          <tr v-for="(bid,index) in bids.slice().reverse()"
              :key="index"
              class="border-top border-bottom"
              v-bind:class="{
                  firstTableRow : index == 0
                }">
            <td>{{ bids.length - index }}</td>
            <td>{{ bid.user.firstName + ' ' + bid.user.lastName }}</td>
            <td>{{ bid.genDate }}</td>
            <th scope="row">{{ bid.bid }} USD</th>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
import {Field, Form} from "vee-validate";
import * as yup from "yup";

export default {
  name: "BettingRoom",
  props: ['bids', 'auctionId', 'stompClient'],
  components: {
    Form,
    Field,
    // ErrorMessage,
  },
  data() {
    const schema = yup.object().shape({
      bid: yup
          .number()
          .required("Bid is required!")
    });
    return {
      betInput: null,
      isWrongBet: null,
      loading: false,
      successful: false,
      schema,
      bid: "",
    }
  },
  methods: {
    checkBetForChangeColor() {
      if (this.bids.length !== 0) {
        if (this.betInput <= this.bids.findLast(x => x).bid * 1.05) {
          this.isWrongBet = true;
        } else {
          this.isWrongBet = false;
        }
      } else {
        if (this.betInput <= this.content.startPrice * 1.05) {
          this.isWrongBet = true;
        } else {
          this.isWrongBet = false;
        }
      }
    },
    handleBet() {
      this.loading = true;
      if (this.stompClient && this.stompClient.connected) {
        this.stompClient.send("/app/betting/" + this.auctionId, JSON.stringify({
          'auctionId': String(this.auctionId),
          'userId': this.$store.state.auth.user.userDto.id,
          'bet': this.betInput
        }));
      }
      this.loading = false;
    },
  }
}
</script>

<style scoped>

.text-green {
  color: green;
}

.text-red {
  color: red;
}

.custom-table {
  max-height: 300px;
}

.custom-table {
  position: relative;
  height: 200px;
  overflow: auto;
  display: block;
}

.firstTableRow {
  background-color: #90EE90 !important;
}
</style>