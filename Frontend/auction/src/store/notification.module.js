import NotificationService from "../services/notification.service";

const user = JSON.parse(localStorage.getItem('user'));
const initialState = { status: { connected: false }, user };

export const notification = {
    namespaced: true,
    state: initialState,
    actions: {
        connect({ commit }) {
            return NotificationService.connect().then(
                notification => {
                    commit('connectSuccess', notification);
                    return Promise.resolve(notification);
                },
                error => {
                    commit('loginFailure');
                    return Promise.reject(error);
                }
            );
        },
    },
    mutations: {
        connectSuccess(state) {
            state.status.connected = true;
        },
    }
};
