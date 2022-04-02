import axios from 'axios';
// import authHeader from './auth-header';
import properties from "@/properties";

const API_URL = properties.API_URL + '/api/payment/';

export class PaymentService {
    getPaymentsWithAuctionByUserId(userId, page, perPage) {
        return axios.get(API_URL + 'send/' + userId, {
            params: {
                page: page,
                perPage: perPage
            }
        });
    }

    getReceiveOrderWithAuctionByUserId(userId, page, perPage) {
        return axios.get(API_URL + 'receive/' + userId, {
            params: {
                page: page,
                perPage: perPage
            }
        });
    }

    executeSuccessPay(paymentId, payerId, token) {
        return axios.post(API_URL + 'success', {},
            {
                params: {
                    paymentId: paymentId,
                    PayerID: payerId,
                    token: token,
                }
            })
    }

}

export default new PaymentService();
