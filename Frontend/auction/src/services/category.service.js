import axios from 'axios';
import properties from "@/properties";
import authHeader from "@/services/auth-header";

const API_URL = properties.API_URL + '/api/category';


class CategoryService {
    getCategoriesForCreateAuction() {
        return axios.get(API_URL, {
            headers:
                authHeader()
        });
    }

    createNewCategory(requests) {
        return axios.post(API_URL, requests, {
            headers:
                authHeader()
        })
    }
}

export default new CategoryService();
