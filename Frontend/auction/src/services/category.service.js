import axios from 'axios';

const API_URL = 'http://localhost:8080/api/category';


class CategoryService {
    getCategoriesForCreateAuction() {
        return axios.get(API_URL);
    }

    createNewCategory(requests) {
        return axios.post(API_URL, requests)
    }
}

export default new CategoryService();
