
export const image = {
    namespaced: true,
    state: {
        imageLink: '',
    },
    actions: {
        uploadImage({commit}, image) {
            return commit('saveImageLink', image);
        },
    },
    mutations: {
        saveImageLink(image) {
            this.state.imageLink = image;
        }
    }
};
