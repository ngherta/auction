import axios from 'axios';
import authHeader from './auth-header';

const API_URL = 'http://localhost:8080/api/users/';

export class UserService {
    isAdmin() {
        console.log(this.getUser().userDto);
        return true;
    }

    getUserBoard() {
        return axios.get(API_URL + 'user', {headers: authHeader()});
    }

    update(user) {
        console.log("NGH-service = lastName: " + user.lastName);
        return axios.put(API_URL, {
            id: user.id,
            email: user.email,
            firstName: user.firstName,
            lastName: user.lastName,
            enabled: user.enabled,
            birthday: user.birthday
        })
    }

    delete(id) {
        return axios.delete(API_URL + id, {
            headers: authHeader()
        });
    }

    getAllUsers() {
        return axios.get(API_URL, {headers: authHeader()});
    }

    getUser() {
        return JSON.parse(localStorage.getItem('user'));
    }

    resetPassword(email) {
        return axios.post(API_URL + 'reset/password/' + email);
    }

    disableUserAfterPasswordReset(code) {
        return axios.post(API_URL + 'disable/code/' + code);
    }

    changePasswordAfterReset(code, password) {
        console.log(code + " : " + password);
        return axios.post(API_URL + 'update/password/' + code, {
            password: password,
        });
    }
}

export default new UserService();
