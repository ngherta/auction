import axios from "axios";
import authHeader from "@/services/auth-header";


const API_URL = 'http://localhost:8080/api/bids/';

class BettingService {
    getBidsForByAuction(auctionId) {
        return axios.get(API_URL + auctionId, { headers: authHeader() });
    }
}

export default new BettingService();
