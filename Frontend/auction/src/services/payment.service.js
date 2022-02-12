import axios from 'axios';
// import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/payment/';

export class PaymentService {
    getPaymentsWithAuctionByUserId(userId, page, perPage) {
        return axios.get(API_URL + 'user/' + userId, {
            params: {
                page: page,
                perPage: perPage
            }
        });
    }

    executeSuccessPay(paymentId, payerId, token) {
        return axios.post(API_URL + 'success', {
            params: {
                paymentId : paymentId,
                payerId : payerId,
                token : token,
            }
        })
    }
}

export default new PaymentService();
