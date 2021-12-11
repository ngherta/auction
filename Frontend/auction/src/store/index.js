import { createStore } from "vuex";
import { auth } from "./auth.module";
import { auctions } from "./auctions.module";

const store = createStore({
  modules: {
    auth,
    auctions,
  },
});

export default store;
