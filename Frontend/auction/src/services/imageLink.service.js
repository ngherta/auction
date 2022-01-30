import axios from 'axios';

const API_URL = 'http://localhost:8080/api/links/';


class ImageLinkService {
    getAllByType(type) {
        return axios.get(API_URL + type);
    }

    create(request) {
        return axios.post(API_URL, {
            url : request.url,
            imageLink : request.imageLink,
            type : request.type
        })
    }
}

export default new ImageLinkService();
