import axios from 'axios';

const API_URL = 'http://localhost:8080/api/category/';


class CategoryService {
   getCategoriesForCreateAuction() {
       return axios.get(API_URL);
   }
}

export default new CategoryService();
