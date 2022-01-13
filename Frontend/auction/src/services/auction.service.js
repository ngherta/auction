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

    getAll(page, perPage) {
        return axios.get('http://localhost:8080/api/auction', {
            params: {
                page: page,
                perPage: perPage
            }
        });
    }

    search(text, page, perPage) {
        return axios.get(API_URL + 'search', {
            params: {
                page: page,
                perPage: perPage,
                text: text
            }
        })
    }

    filter(filter, page, perPage) {
        return axios.get(API_URL + 'filter', {
            params: {
                page: page,
                perPage: perPage,
                filter: filter
            }
        })
    }
}

export default new AuctionsService();
