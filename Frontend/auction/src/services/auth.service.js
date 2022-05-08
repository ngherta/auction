import axios from 'axios';
import properties from "@/properties";

const API_URL = properties.API_URL + '/api/auth/';

class AuthService {
  login(user) {
    return axios
      .post(API_URL + 'signin', {
        email: user.email,
        password: user.password
      })
      .then(response => {
        if (response.data.token) {
          localStorage.setItem('user', JSON.stringify(response.data));
        }

        return response.data;
      });
  }

  logout() {
    localStorage.removeItem('user');
  }

  register(user) {
    return axios.post(API_URL + 'signup', {
      firstName: user.firstName,
      lastName: user.lastName,
      email: user.email,
      password: user.password,
      birthday: user.birthday,
    });
  }

  verify(code) {
    return axios.post(API_URL + 'confirm', {}, {
      params : {
        code : code
      }
    })
  }
}

export default new AuthService();
