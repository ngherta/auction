import UserService from "../services/user.service";

export const users = {
    namespaced: true,
    actions: {
        update({commit}, user) {
            return UserService.update(user).then(
                response => {
                    commit('updateSuccess', response);
                    return Promise.resolve(response);
                },
                error => {
                    commit('updateFailure');
                    return Promise.reject(error);
                }
            )
        },
        resetPassword({commit}, email) {
            return UserService.resetPassword(email).then(
                response => {
                    commit('resetSuccess', response);
                    return Promise.resolve(response);
                },
                error => {
                    commit('resetFailure');
                    return Promise.reject(error);
                }
            );
        },
        disableUserAfterReset({commit}, code) {
            return UserService.disableUserAfterPasswordReset(code).then(
                response => {
                    commit('resetSuccess', response);
                    return Promise.resolve(response);
                },
                error => {
                    commit('resetFailure');
                    return Promise.reject(error);
                }
            );
        },
        updatePassword({commit}, request) {
            return UserService.changePasswordAfterReset(request.code, request.password).then(
                response => {
                    return Promise.resolve(response);
                },
                error => {
                    commit('updateFailure');
                    return Promise.reject(error);
                }
            );
        }
    },
    mutations: {
        updateSuccess(state, user) {
            state.status.updated = true;
            state.user = user;
        },
        updateFailure(state) {
            state.status.updated = false;
        }
    }
};
