import axios from 'axios';
// import authHeader from './auth-header';
import properties from "@/properties";
import authHeader from "@/services/auth-header";

const API_URL = properties.API_URL + '/api/statistic/';

export class StatisticService {
    getStatisticData() {
        return axios.get(API_URL, {
            headers:
                authHeader()
        });
    }
}

export default new StatisticService();
