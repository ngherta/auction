import UserService from "../services/user.service";

export const users = {
    namespaced: true,
    actions: {
        resetPassword({ commit }, email) {
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
        disableUserAfterReset({ commit }, code) {
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
        updatePassword({ commit }, request) {
            console.log(request.code + " : " + request.password)
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
    }
};
