import axios from 'axios';
import properties from "@/properties";
import authHeader from "@/services/auth-header";

const API_URL = properties.API_URL + '/api/links/';


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
        }, {
            headers:
                authHeader()
        })
    }
}

export default new ImageLinkService();
