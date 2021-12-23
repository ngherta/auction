import { createStore } from "vuex";
import { auth } from "./auth.module";
import { auctions } from "./auctions.module";
import { notification } from "./notification.module"
import { users } from "./users.module"

const store = createStore({
  modules: {
    auth,
    auctions,
    notification,
    users,
  },
});

export default store;
