import axios from 'axios';
import authHeader from './auth-header';
import properties from "@/properties";

const API_URL = properties.API_URL;

class AuthService {
    getActuatorInfo() {
        return axios.get(API_URL + 'actuator/info', {headers: authHeader()});
    }

    getActuatorHealth() {
        return axios.get(API_URL + 'actuator/health', {headers: authHeader()});
    }

    getActuatorMetrics(url) {
        return axios.get(API_URL + 'actuator/metrics/' + url, {headers: authHeader()});
    }

}

export default new AuthService();
