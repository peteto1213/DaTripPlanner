import GlobalConfig from "./GlobalConfig";
import axios from "axios";
//URL links to fetch with the Rest API related to plan
const PLAN_REST_API_URL = `${GlobalConfig.api_ip_address()}/tripPlan`;

const CREATE_PLAN_URL = `${PLAN_REST_API_URL}/createPlan`;
const VIEW_ALL_PLAN_URL = `${PLAN_REST_API_URL}/viewPlan`;
const VIEW_PLAN_DETAILS_URL = `${PLAN_REST_API_URL}/viewPlanDetail`;
const DELETE_PLAN_URL = `${PLAN_REST_API_URL}/deletePlan`;
const CHANGE_PLAN_NAME_URL = `${PLAN_REST_API_URL}/changePlanName`;

/**
 * @author Pete To
 * @version 1.0
 * This class holds the functions for:
 * fetching the Rest API for the GET, POST and DELETE request for the plan manipulation,
 */
class PlanService{

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to post a plan to the database, according to an object parameter "plan"
     * @param plan
     * @returns {AxiosPromise}
     */
    createPlan(plan){
        let planJson = JSON.stringify(plan)

        axios.defaults.withCredentials = true;

        return axios({
            method: "post",
            url: CREATE_PLAN_URL,
            data: planJson,
            headers: {
                'Content-Type': 'application/json'
            }
        });
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to get all plans created by a user, according to 2 parameters
     * @param page
     * @param size
     * @returns {Promise<AxiosResponse<any>>}
     */
    viewAllPlan(page, size){
        axios.defaults.withCredentials = true;
        return axios.get(`${VIEW_ALL_PLAN_URL}?page=${page}&size=${size}`);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to get specified plan details according to te planId
     * @param planId
     * @returns {Promise<AxiosResponse<any>>}
     */
    viewPlanDetails(planId){
        axios.defaults.withCredentials = true;
        return axios.get(`${VIEW_PLAN_DETAILS_URL}?planId=${planId}`);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to delete a specified plan according to the planId
     * @param planId
     * @returns {Promise<AxiosResponse<any>>}
     */
    deletePlan(planId){
        axios.defaults.withCredentials = true;
        return axios.delete(`${DELETE_PLAN_URL}?planId=${planId}`);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return an Axios promise to change a specified planName of a plan
     * @param planId
     * @param planName
     * @returns {AxiosPromise}
     */
    changePlanName(planId, planName){
        let planNameJson = JSON.stringify({
            planId: planId,
            planName: planName
        })

        axios.defaults.withCredentials = true;
        return axios({
            method: 'put',
            url: CHANGE_PLAN_NAME_URL,
            data: planNameJson,
            headers: {
                'Content-Type': 'application/json'
            }
        })
    }
}

export default new PlanService();