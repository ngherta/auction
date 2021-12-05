import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/auction';

class AuctionsService {
    getAuctions() {
        return axios.get(API_URL, { headers: authHeader() , params: {perPage: 15}});
    }
}

export default new AuctionsService();
