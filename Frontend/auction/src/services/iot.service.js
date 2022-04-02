import axios from 'axios';
import properties from "@/properties";

const API_URL = properties.API_URL + '/api/iot/';

class IotService {
    connect(userId, auctionId) {
        return axios.put(API_URL + 'connect', {
            userId: userId,
            auctionId: auctionId
        });
    }

    disconnect(userId) {
        return axios.put(API_URL + 'disconnect', {}, {params: {
                userId: userId
            }});
    }

    isConnected(auctionId, userId) {
        return axios.get(API_URL + 'auction', {
            params: {
                userId: userId,
                auctionId: auctionId
            }
        })
    }

}

export default new IotService();
