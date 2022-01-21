import { createStore } from "vuex";
import { auth } from "./auth.module";
import { auctions } from "./auctions.module";
import { notification } from "./notification.module"
import { users } from "./users.module"
import { image } from "./image.module";

const store = createStore({
  modules: {
    auth,
    auctions,
    notification,
    users,
    image,
  },
});

export default store;
