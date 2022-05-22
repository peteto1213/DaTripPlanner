import axios from "axios";
import GlobalConfig from "./GlobalConfig";

//URL links to fetch with the Rest API related to user components
const USER_BASE_REST_API_URL = `${GlobalConfig.api_ip_address()}/user`;

const REGISTER_URL = `${USER_BASE_REST_API_URL}/register`;
const LOGIN_URL = `${USER_BASE_REST_API_URL}/login`;
const GET_USER_INFO_URL = `${USER_BASE_REST_API_URL}/getUserInfo`;
const CHANGE_PASSWORD_BY_OLD_PASSWORD_URL = `${USER_BASE_REST_API_URL}/changePasswordByOldPassword`;
const SEND_CODE_TO_EMAIL_URL = `${USER_BASE_REST_API_URL}/sendCode`;
const EDIT_USER_INFO_URL = `${USER_BASE_REST_API_URL}/editUserInfo`;
const LOGOUT_USER_URL = `${USER_BASE_REST_API_URL}/logout`;
const CHANGE_PASSWORD_BY_EMAIL_CODE_URL = `${USER_BASE_REST_API_URL}/changePasswordByEmailCode`;

/**
 * @author Pete To
 * @version 1.0
 * This class holds the functions for:
 * fetching the Rest API for the GET and POST requests related to user components,
 */
class UserService{
    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to post(create) a user to the database by an object parameter
     * @param user
     * @returns {AxiosPromise}
     */
    registerUser(user){
        let formData = new FormData();

        formData.append("email", user.email);
        formData.append("password", user.password);
        formData.append("repeatPassword", user.repeatPassword);
        formData.append("firstName", user.firstName);
        formData.append("lastName", user.lastName);

        axios.defaults.withCredentials = true;

        return axios({
            method: "post",
            url: REGISTER_URL,
            data: formData,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to post user's login information for authentication during login process
     * This is according to an object parameter, Later the parameter will be changed to FormData structure fetching with the API
     * @param user
     * @returns {AxiosPromise}
     */
    loginUser(user){
        let formData = new FormData();

        formData.append("email", user.email);
        formData.append("password", user.password);
        axios.defaults.withCredentials = true;
        return axios({
            method: "post",
            url: LOGIN_URL,
            data: formData,
            headers: {
                'Content-Type': 'multipart/form-data'
            },
        });
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to logout a user according to the cookie section on the client side after the user has logged in
     * @returns {Promise<AxiosResponse<any>>}
     */
    logoutUser(){
        axios.defaults.withCredentials = true;
        return axios.put(LOGOUT_USER_URL);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to get user information according to the cookie section on the client side after the user has logged in
     * @returns {Promise<AxiosResponse<any>>}
     */
    getUserInfo(){
        axios.defaults.withCredentials = true;

        return axios.get(GET_USER_INFO_URL);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to post the user's updated information according to an object parameter
     * @param user
     * @returns {AxiosPromise}
     */
    editUserInfo(user){
        axios.defaults.withCredentials = true;

        let userJson = JSON.stringify(user);

        return axios({
            method: "post",
            url: EDIT_USER_INFO_URL,
            data: userJson,
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to post the user's old password and new password for authentication according to an object parameter
     * Later the parameter will be changed to FormData structure fetching with the API
     * @param user
     * @returns {AxiosPromise}
     */
    changePasswordByOldPassword(user){
        let formData = new FormData();

        formData.append("oldPassword", user.oldPassword);
        formData.append("newPassword", user.newPassword);
        formData.append("repeatNewPassword", user.repeatNewPassword);

        axios.defaults.withCredentials = true;
        return axios({
            method: "post",
            url: CHANGE_PASSWORD_BY_OLD_PASSWORD_URL,
            data: formData,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to post the user's registered email for authentication according to an object parameter
     * The parameter will changed to formData structure when fetching
     * @param user
     * @returns {AxiosPromise}
     */
    sendForgetPasswordCode(user){
        let formData = new FormData();

        formData.append("email", user.email);

        axios.defaults.withCredentials = true;
        return axios({
            method: "post",
            url: SEND_CODE_TO_EMAIL_URL,
            data: formData,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an AJAX promise to post user's contact form to team3NU@outlook.com
     * successful: alert "successful" message, unsuccessful: alert "network error"
     * @param contactForm
     */
    sendContactForm(contactForm){
        fetch("https://formsubmit.co/ajax/team3NU@outlook.com", {
            method: "POST",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                    name: contactForm.name,
                    email: contactForm.email,
                    contactNumber: contactForm.contactNumber,
                    subject: contactForm.subject,
                    message: contactForm.message
            })
        })
            .then(response => response.json())
            .then(data => console.log(data))
            .then(() => alert("Message sent successfully"))
            .catch(error => alert("Network error: " + error))
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an AJAX promise to reset user's password by email code, according to an object parameter
     * @param user
     * @returns {AxiosPromise}
     */
    changePasswordByEmailCode(user){
        let formData = new FormData();

        formData.append("code", user.code);
        formData.append("email", user.email);
        formData.append("newPassword", user.newPassword);
        formData.append("repeatPassword", user.repeatPassword);

        axios.defaults.withCredentials = true;
        return axios({
            method: "post",
            url: CHANGE_PASSWORD_BY_EMAIL_CODE_URL,
            data: formData,
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        });
    }


}

export default new UserService();