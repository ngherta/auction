import axios from 'axios';

const API_URL = 'http://localhost:8080/api/iot/';

class IotService {
    connect(userId) {
        return axios.put(API_URL + userId);
    }


}

export default new IotService();
