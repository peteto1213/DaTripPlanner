import GlobalConfig from "./GlobalConfig";
import axios from "axios";

//URL links to fetch with the Rest API related to order
const ORDER_SERVICE_REST_API_URL = `${GlobalConfig.api_ip_address()}/order`;

const GENERATE_ORDER_URL = `${ORDER_SERVICE_REST_API_URL}/generateOrder`;
const DELETE_ORDER_URL = `${ORDER_SERVICE_REST_API_URL}/deleteOrder`;
const VIEW_ORDER_DETAIL_URL = `${ORDER_SERVICE_REST_API_URL}/viewSingleOrderInfo`;
const VIEW_ALL_ORDER_URL = `${ORDER_SERVICE_REST_API_URL}/viewOrderInfo`;

/**
 * @author Pete To
 * @version 1.0
 * This class holds the functions for:
 * fetching the Rest API for the GET, POST and DELETE request for the order manipulation,
 */
class OrderService{

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to post a new order to the database
     * @param paymentDetails
     * This required parameter is an object and will later be transferred to a FormData Structure
     * @returns {AxiosPromise}
     */
    createOrder(paymentDetails){
        let formData = new FormData();

        let total = Math.round(paymentDetails.transactionAmount * 100)/100;

        formData.append("cardNumber", paymentDetails.cardNumber);
        formData.append("expireDate", paymentDetails.expireDate);
        formData.append("ownerName", paymentDetails.ownerName);
        formData.append("planId", paymentDetails.planId);
        formData.append("securityCode", paymentDetails.securityCode);
        formData.append("transactionAmount", total.toString());

        axios.defaults.withCredentials = true;

        return axios({
            method: "post",
            url: GENERATE_ORDER_URL,
            data: formData,
            headers: {
                'Content-Type': 'multipart/form-data'
            },
        });
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise for getting all order created by a user according to two parameters
     * @param page
     * @param size
     * @returns {Promise<AxiosResponse<any>>}
     */
    viewAllOrder(page, size){
        axios.defaults.withCredentials = true;
        return axios.get(`${VIEW_ALL_ORDER_URL}?page=${page}&size=${size}`);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise for getting specific order details by a user according to orderId
     * @param orderId
     * @returns {Promise<AxiosResponse<any>>}
     */
    viewOrderDetails(orderId){
        axios.defaults.withCredentials = true;
        return axios.get(`${VIEW_ORDER_DETAIL_URL}?orderId=${orderId}`);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise for deleting the specified order according to orderId
     * @param orderId
     * @returns {Promise<AxiosResponse<any>>}
     */
    deleteOrder(orderId){
        axios.defaults.withCredentials = true;
        return axios.delete(`${DELETE_ORDER_URL}?orderId=${orderId}`);
    }

}

export default new OrderService();
