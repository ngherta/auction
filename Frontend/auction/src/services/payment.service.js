import axios from 'axios';
// import authHeader from './auth-header';
import properties from "@/properties";
import authHeader from "@/services/auth-header";

const API_URL = properties.API_URL + '/api/payment/';

export class PaymentService {
    getPaymentsWithAuctionByUserId(userId, page, perPage) {
        return axios.get(API_URL + 'send/' + userId, {
            params: {
                page: page,
                perPage: perPage
            },
            headers:
                authHeader()
        });
    }

    getReceiveOrderWithAuctionByUserId(userId, page, perPage) {
        return axios.get(API_URL + 'receive/' + userId, {
            params: {
                page: page,
                perPage: perPage
            },
            headers:
                authHeader()
        });
    }

    executeSuccessPay(paymentId, payerId, token) {
        return axios.post(API_URL + 'success', {},
            {
                params: {
                    paymentId: paymentId,
                    PayerID: payerId,
                    token: token,
                },
                headers:
                    authHeader()
            })
    }

}

export default new PaymentService();
