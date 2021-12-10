import { createStore } from "vuex";
import { auth } from "./auth.module";
import { auctions } from "./auctions.module";
import { qr } from "./qr.module"

const store = createStore({
  modules: {
    auth,
    auctions,
    qr,
  },
});

export default store;
