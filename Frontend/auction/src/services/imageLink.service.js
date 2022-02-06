import axios from 'axios';

const API_URL = 'http://localhost:8080/api/links/';


class ImageLinkService {
    getAllByType(type) {
        return axios.get(API_URL + type);
    }

    create(request, type) {
        return axios.post(API_URL, {
            url : request.url,
            imageLink : request.imageLink,
            sequence : request.sequence,
            type : type
        })
    }
}

export default new ImageLinkService();
