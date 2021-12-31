import axios from 'axios';
import authHeader from "@/services/auth-header";

const API_URL = 'http://localhost:8080/api/auction/';

class AuctionsService {
    getAuctions(perPage) {
        return axios.get(API_URL, {params: {perPage: perPage}});
    }

    getAuctionById(auctionId) {
        return axios.get(API_URL + auctionId);
    }

    update(auction) {
        return axios.put(API_URL + auction.id, {
            auction,
            headers:
                authHeader()
        });
    }

    delete(auctionId) {
        return axios.delete(API_URL + auctionId, {
            headers:
                authHeader()
        });
    }

    getAll() {
        return axios.get(API_URL + "all");
    }
}

export default new AuctionsService();
