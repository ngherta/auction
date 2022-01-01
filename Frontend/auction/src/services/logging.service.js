import axios from 'axios';

const API_URL = 'http://localhost:8080/actuator/loggers/';

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
