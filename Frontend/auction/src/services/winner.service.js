import axios from 'axios';
import properties from "@/properties";
import authHeader from "@/services/auth-header";

const API_URL = properties.API_URL + '/api/winner/';

export class WinnerService {
    useDefaultAddress(auctionId) {
        return axios.post(API_URL + auctionId + '/address/default', {}, {
            headers:
                authHeader()
        });
    }

    getWinner(userId, page, perPage) {
        return axios.get(API_URL + 'user/' + userId, {
            params: {
                page: page,
                perPage: perPage
            },
            headers:
                authHeader()
        });
    }

    getWinnerForAuctionCreator(userId, page, perPage) {
        return axios.get(API_URL + 'user/creator/' + userId, {
            params: {
                page: page,
                perPage: perPage
            },
            headers:
                authHeader()
        });
    }

    addAddress(data, auctionId) {
        return axios.post(API_URL + auctionId + '/address', {
                auctionId: auctionId,
                country: data.country,
                city: data.city,
                address: data.address,
                saveAsDefault: data.saveAsDefault,
            },
            {
                headers:
                    authHeader()
            })
    }

    addTrackNumber(trackNumber, auctionId) {
        return axios.post(API_URL + auctionId + '/track/' + trackNumber, {}, {
            headers:
                authHeader()
        });
    }

    delivered(auctionId) {
        return axios.put(API_URL + auctionId + '/delivered', {}, {
            headers:
                authHeader()
        });
    }
}

export default new WinnerService();
