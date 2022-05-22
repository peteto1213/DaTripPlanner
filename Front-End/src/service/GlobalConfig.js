/**
 * @author Pete To
 * @version 1.0
 * This class holds all the function related to global variables
 */
class GlobalConfig{

    /**
     * @author Pete To
     * @version 1.0
     * This function will return the server url for fetching the all Rest API
     * @returns {string}
     */
    api_ip_address(){
        return "http://43.131.56.241:8080/team3-v1";
        // return "http://localhost:8080";
    }
}

export default new GlobalConfig();