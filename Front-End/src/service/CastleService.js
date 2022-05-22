import axios from "axios"
import GlobalConfig from "./GlobalConfig";
import CastleImages from "../data/CastleImages";

//URL links to fetch with the Rest API
const CASTLE_BASE_REST_API_URL = `${GlobalConfig.api_ip_address()}/castle/getAllCastleInfo`;
const CASTLE_AVG_PRICE_BY_CASTLEID = `${GlobalConfig.api_ip_address()}/castle/getCastleTicketByDateAndTime?`;
const CASTLE_STUDENT_PRICE_URL = `${GlobalConfig.api_ip_address()}/castle/getCastleTicketPrice?`;

/**
 * @author Pete To
 * @version 1.0
 * This class holds the functions for
 * 1. fetching the Rest API for the GET, POST request,
 * 2. get castle information by castleId/castleName
 */
class CastleService{

    /**
     * @author Pete To
     * @version 1.0
     * This function will return anx Axios promise, for the purpose of retrieving all castles' information
     * @returns {Promise<AxiosResponse<any>>}
     */
    getAllCastles(){
        axios.defaults.withCredentials = true;
        return axios.get(CASTLE_BASE_REST_API_URL);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return anx Axios promise, for the purpose of retrieving all tickets type based on the following parameters
     * @param date
     * @param time
     * @param castleId
     * @returns {Promise<AxiosResponse<any>>}
     */
    getCastleTicketPriceById(date, time, castleId){
        axios.defaults.withCredentials = true;
        return axios.get(`${CASTLE_AVG_PRICE_BY_CASTLEID}date=${date}&time=${time}&castleId=${castleId}`);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return anx Axios promise, for the purpose of getting castle student ticket price
     * @param castleId
     * @returns {Promise<AxiosResponse<any>>}
     */
    getStudentPriceById(castleId){
        axios.defaults.withCredentials = true;
        return axios.get(`${CASTLE_STUDENT_PRICE_URL}castleId=${castleId}`);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return a route path to the webpage based on the castleId when the "explore more" button is clicked
     * @param id
     * @returns {string|null}
     */
    exploreMoreRouter(id){
        switch(id){
            case 1:
                return "/castle-alnwick";

            case 2:
                return "/castle-auckland";

            case 3:
                return "/castle-bamburgh";

            case 4:
                return "/castle-barnard";

            default:
                return null;
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return a route path to the webpage based on the castleId when the "more information" button is clicked
     * @param id
     * @returns {string|null}
     */
    moreInformationRouter(id){
        switch(id){
            case 1:
                return "/alnwick";

            case 2:
                return "/auckland";

            case 3:
                return "/bamburgh";

            case 4:
                return "/barnard";

            default:
                return null;
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return a castle image based on the castleName
     * @param castleName
     * @returns {*}
     */
    switchPhotoByCastleName(castleName){
        switch(castleName){
            case "Alnwick Castle":
                return CastleImages[0].images[1];

            case "Auckland Castle":
                return CastleImages[1].images[1];

            case "Bamburgh Castle":
                return CastleImages[2].images[1];

            case "Barnard Castle":
                return CastleImages[3].images[1];

            default:
                return CastleImages[0].images[1];
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return a routing path to the webpage based on the castleName
     * @param castleName
     * @returns {string}
     */
    switchLocationByCastleName(castleName){
        switch(castleName){
            case "Alnwick Castle":
                return "/alnwick";

            case "Auckland Castle":
                return "/auckland";

            case "Bamburgh Castle":
                return "/bamburgh";

            case "Barnard Castle":
                return "/barnard";

            default:
                return "/alnwick";
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return castleId based on the castleName
     * @param castleName
     * @returns {number}
     */
    getCastleIdByCastleName(castleName){
        switch(castleName){
            case "Alnwick Castle":
                return 1;

            case "Auckland Castle":
                return 2;

            case "Bamburgh Castle":
                return 3;

            case "Barnard Castle":
                return 4;

            default:
                return 1;
        }
    }

}

export default new CastleService();