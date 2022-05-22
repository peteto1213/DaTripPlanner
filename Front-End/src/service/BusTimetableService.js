import axios from "axios"
import GlobalConfig from "./GlobalConfig";

const BUS_TIMETABLE = `${GlobalConfig.api_ip_address()}/route/getCastleTicketAvgPrice`
class BusTimetableService {


    getAllCastles(){
        return axios.get(CASTLE_BASE_REST_API_URL)
    }
    
}