import axios from 'axios';

const API_URL = 'http://localhost:8080/api/complaint/';

class ComplaintService {
    sendComplaint(data) {
        return axios.post(API_URL, {
            userId: data.userId,
            auctionEventId: data.auctionEventId,
            message: data.message
        });
    }

    getAll(page, perPage) {
        return axios.get(API_URL, {
            params: {
                page: page,
                perPage: perPage
            }
        });
    }
}

export default new ComplaintService();
