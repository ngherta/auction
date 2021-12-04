import axios from 'axios';

const API_URL = 'http://localhost:8080/api/auth/';

class AuthService {
  login(user) {
    console.log("try!! " + user);
    return axios
      .post(API_URL + 'signin', {
        email: user.email,
        password: user.password
      })
      .then(response => {
        console.log(response);
        if (response.data.token) {
          localStorage.setItem('user', JSON.stringify(response.data));
          console.log(response.data.token);
          console.log(JSON.stringify(response.data));
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
      password: user.password
    });
  }
}

export default new AuthService();
