import axios from 'axios';

const API_URL = 'http://localhost:8080/api/winner/';

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

    addAddress(data, auctionId) {
        return axios.post(API_URL + auctionId + '/address',  {
            auctionId : auctionId,
            country: data.country,
            city: data.city,
            address: data.address,
        })
    }
}

export default new WinnerService();
