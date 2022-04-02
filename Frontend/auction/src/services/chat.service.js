import axios from 'axios';
import properties from "@/properties";

const API_URL = properties.API_URL + '/api/auction/chat/';


class ChatService {
    getMessagesByAuctionId(id) {
        return axios.get(API_URL + id);
    }
}

export default new ChatService();
