import axios from 'axios';
import properties from "@/properties";
import authHeader from "@/services/auth-header";

const API_URL = properties.API_URL + '/actuator/loggers/';

class LoggingService {
    changeLogLevel(loggerName, logLevel) {
        return axios.post(API_URL + loggerName,
            {"configuredLevel" : logLevel},
            {
                headers:
                    authHeader()
            });
    }

    getLogLevel() {
        return axios.get(API_URL, {
            headers:
                authHeader()
        });
    }
}

export default new LoggingService();
