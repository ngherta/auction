import axios from 'axios';
import properties from "@/properties";

const API_URL = properties.API_URL + '/actuator/loggers/';

class LoggingService {
    changeLogLevel(loggerName, logLevel) {
        return axios.post(API_URL + loggerName,
            {"configuredLevel" : logLevel});
    }

    getLogLevel() {
        return axios.get(API_URL);
    }
}

export default new LoggingService();
