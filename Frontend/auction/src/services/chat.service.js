import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auction/chat/';


class ChatService {
    getMessagesByAuctionId(id) {
        return axios.get(API_URL + id);
    }
}

export default new ChatService();
