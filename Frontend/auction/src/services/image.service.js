import axios from 'axios';

const CLOUD_URL = 'https://api.cloudinary.com/v1_1/dxn6dcenz/upload';

class ImageService {
    upload(image) {
        return axios
            .post(CLOUD_URL , {
                    "file" : image,
                    "upload_preset" : 'jhjwl2sq'
                })
            .then(response => {
                return response.data;
            });
    }
}

export default new ImageService();
