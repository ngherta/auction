import axios from 'axios';
// import authHeader from './auth-header';
import properties from "@/properties";

const API_URL = properties.API_URL + '/api/settings/';

export class SettingsService {
    getSettings(userId) {
        return axios.get(API_URL + userId);
    }

    update(type, userId, value) {
        return axios.put(API_URL + 'notification', {
            type: type,
            userId: userId,
            value: value
        })
    }
}

export default new SettingsService();
