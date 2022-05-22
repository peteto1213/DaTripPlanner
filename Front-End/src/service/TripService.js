import axios from "axios";
import GlobalConfig from "./GlobalConfig";

//URL links to fetch with the Rest API related to trip plan creation procedures
const GET_CASTLE_INFO_URL =`${GlobalConfig.api_ip_address()}/castle/getAllCastleInfo`;
const GET_CASTLE_DATE_URL = `${GlobalConfig.api_ip_address()}/castle/getCastleAvailableDateById?castleId=`;
const GET_CASTLE_TIME_URL = `${GlobalConfig.api_ip_address()}/castle/getAvailableTimeByCastleId?`
const GET_ROUTE_DATA_URL = `${GlobalConfig.api_ip_address()}/route/getRoute`;
const GET_RETURN_ROUTE_DATA_URL = `${GlobalConfig.api_ip_address()}/route/getReturnRoute`;

/**
 * @author Pete To
 * @version 1.0
 * This class holds the functions for:
 * fetching the Rest API for the GET requests while the user is creating a trip plan,
 */
class TripService{

    /**
     * @author Lei Chen
     * @version 1.0
     * Edited by Pete To
     * This method can get information from back-end by controller of castle.
     * It will return an Axios promise to return data of all castles.
     * @returns {Promise<AxiosResponse<any>>}
     */
    getAllCastleInfo(){
        axios.defaults.withCredentials = true;
        return axios.get(GET_CASTLE_INFO_URL);

    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to get a list of dates according to castleId chosen by the user
     * @param castleId
     * @returns {Promise<AxiosResponse<any>>}
     */
    getCastleDate(castleId){
        axios.defaults.withCredentials = true;
        return axios.get(`${GET_CASTLE_DATE_URL}${castleId}`);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to get all possible timeslots according to two parameters
     * @param castleId
     * @param date
     * @returns {Promise<AxiosResponse<any>>}
     */
    getCastleTime(castleId, date){
        axios.defaults.withCredentials = true;
        return axios.get(`${GET_CASTLE_TIME_URL}castleId=${castleId}&date=${date}`)
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to get all possible inbound route from the database according to 4 parameters
     * @param castleId
     * @param date
     * @param departureTime
     * @param ticketQuantity
     * @returns {AxiosPromise}
     */
    getRouteData(castleId, date,departureTime,ticketQuantity){
        axios.defaults.withCredentials = true;
        return axios.get(`${GET_ROUTE_DATA_URL}?castleId=${castleId}&date=${date}&departureTime=${departureTime}&ticketQuantity=${ticketQuantity}`);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to get all possible outbound route from the database according to 4 parameters
     * @param castleId
     * @param date
     * @param departureTime
     * @param ticketQuantity
     * @returns {AxiosPromise}
     */
    getReturnRouteData(castleId, date, departureTime, ticketQuantity) {
        axios.defaults.withCredentials = true;
        return axios.get(`${GET_RETURN_ROUTE_DATA_URL}?castleId=${castleId}&date=${date}&departureTime=${departureTime}&ticketQuantity=${ticketQuantity}`);
    }
}

export default new TripService();

