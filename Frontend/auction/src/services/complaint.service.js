import axios from 'axios';
import properties from "@/properties";
import authHeader from "@/services/auth-header";

const API_URL = properties.API_URL + '/api/complaint';

class ComplaintService {
    sendComplaint(data) {
        return axios.post(API_URL, {
            userId: data.userId,
            auctionEventId: data.auctionEventId,
            message: data.message
        },{
            headers:
                authHeader()
        });
    }

    sendResponse(data) {
        return axios.post(API_URL + '/answer', {
            adminId : data.admin,
            complaintId : data.complaintId,
            status : data.status
        })
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
