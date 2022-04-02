import axios from 'axios';
import properties from "@/properties";

const API_URL = properties.API_URL + '/api/category';


class CategoryService {
    getCategoriesForCreateAuction() {
        return axios.get(API_URL);
    }

    createNewCategory(requests) {
        return axios.post(API_URL, requests)
    }
}

export default new CategoryService();
