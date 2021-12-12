import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auction/';

class AuctionsService {
    getAuctions(perPage) {
        return axios.get(API_URL, { params: {perPage: perPage}});
    }

    getAuctionById(auctionId) {
        return axios.get(API_URL + auctionId);
    }
}

export default new AuctionsService();
