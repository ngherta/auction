import axios from 'axios';
import properties from "@/properties";

const API_URL = properties.API_URL + '/api/winner/';

export class WinnerService {
    useDefaultAddress(auctionId) {
        return axios.post(API_URL + auctionId + '/address/default');
    }

    getWinner(userId, page, perPage) {
        return axios.get(API_URL + 'user/' + userId, {
            params: {
                page: page,
                perPage: perPage
            }
        });
    }

    getWinnerForAuctionCreator(userId, page, perPage) {
        return axios.get(API_URL + 'user/creator/' + userId, {
            params: {
                page: page,
                perPage: perPage
            }
        });
    }

    addAddress(data, auctionId) {
        return axios.post(API_URL + auctionId + '/address',  {
            auctionId : auctionId,
            country: data.country,
            city: data.city,
            address: data.address,
            saveAsDefault: data.saveAsDefault,
        })
    }

    addTrackNumber(trackNumber, auctionId) {
        return axios.post(API_URL + auctionId + '/track/' + trackNumber);
    }

    delivered(auctionId) {
        return axios.put(API_URL + auctionId + '/delivered');
    }
}

export default new WinnerService();
