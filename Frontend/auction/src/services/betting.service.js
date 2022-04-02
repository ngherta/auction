import axios from "axios";
import authHeader from "@/services/auth-header";
import properties from "@/properties";

const API_URL = properties.API_URL + '/api/bids/';

class BettingService {
    getBidsForByAuction(auctionId) {
        return axios.get(API_URL + auctionId, {headers: authHeader()});
    }

    getLastBids(listOfIds) {
        return axios.get(API_URL + 'last' + `?${listOfIds.map((n) => `auctionIds=${n}`).join('&')}`);
    }
}

export default new BettingService();
