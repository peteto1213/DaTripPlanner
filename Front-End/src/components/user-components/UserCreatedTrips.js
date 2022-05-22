import React, {useEffect, useRef, useState} from "react";
import {Link, useHistory} from "react-router-dom";
import PlanService from "../../service/PlanService";
import DateFunction from "../../CustomFunction/DateFunction";
import OrderService from "../../service/OrderService";
import CastleService from "../../service/CastleService";

export default function UserCreatedTrips(props){

    const[tripInfo, setTripInfo] = useState([])
    const history = useHistory();
    const[emptyMessage, setEmptyMessage] = useState("")

    /**
     * @author Pete To
     * @version 1.0
     * This function will push user to view the plan's details
     * the planId will be set to the localStorage for later retrieval
     * @param planId
     */
    const viewPlan = (planId) => {
        localStorage.setItem("currentPlanId", planId)
        history.push("/user-plan-details")
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will fetch the API for deleting of a specified plan
     * the plan is identified by the planId passing in
     * @param planId
     */
    const deletePlan = (planId) => {
        PlanService.deletePlan(planId)
            .then(res => {
                if(res.data.code === 200){
                    alert("plan deleted successfully");
                    history.push('/profile');
                }else{
                    alert("Failed to delete plan, reason: " + res.data.message)
                }
            })
            .catch(error => {
                alert("Server connection error: " + error)
            })

    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will push user to view the order's details
     * the orderId will be set to the localStorage for later retrieval
     * @param orderId
     */
    const viewOrder = (orderId) => {
        localStorage.setItem("currentOrderId", orderId);
        history.push("/user-order-details");
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will fetch the API for deleting of a specified order
     * the order is identified by the orderId passing in
     * @param orderId
     */
    const deleteOrder = (orderId) => {
        OrderService.deleteOrder(orderId)
            .then(res => {
                if(res.data.code === 200){
                    alert("Order deleted successfully")
                    history.push("/profile")
                }else{
                    alert("Fail to delete order: " + res.data.message)
                }
            })
            .catch(error => {
                alert(error)
            })
    }

    /**
     * @author Pete To
     * @version 1.0
     * @param res
     * @param errorMessage
     * This function is called after making a request to the API
     * based on the response type (plan/order), we set different information to the local state "tripInfo"
     * we also display different errorMessages based on the type of response
     */
    function handleData(res, errorMessage){
        if(res.data.code === 200){
            console.log(res)
            setTripInfo(res.data.data)
        }else{
            console.log(res)
            setEmptyMessage(errorMessage)
        }
        if(res.data.data.length){
            setEmptyMessage("")
        }else{
            setEmptyMessage(errorMessage)
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * The useEffect hook will render once, according the type
     * 1. plan - fetch "viewAllPlan" to get all plans created
     * 2. order - fetch "viewAllOrder" to get all orders created
     */
    useEffect(() => {
        //Handling data for Plan section
        if(props.status === "unpaid"){
            PlanService.viewAllPlan(0,10)
                .then((res) => {
                    handleData(res, "You haven't created any plans yet!")
                })
                .catch(error => {
                    setEmptyMessage("No plans to view")
                })
        }

        //Handling data for Order section
        else{
            OrderService.viewAllOrder(0,10)
                .then((res) => {
                    handleData(res, "You haven't checkout any plan yet!")
                })
                .catch(error => {
                    setEmptyMessage("No orders to view")
                })
        }
    },[])

    /**
     * @author Pete To
     * @version 1.0
     * This function will show the changePlanName input box onClick
     */
    function changePlanName(){
        setEditBox(true)
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function handle the change of plan name and fetch to the API for "PUT" method
     */
    function handlePlanName(id){
        PlanService.changePlanName(id, planNameRef.current.value)
            .then((res) => {
                if(res.data.code === 200){
                    console.log(res)
                    alert("Plan name changed successfully")
                }
                else{
                    alert("Change unsuccessful, reason: " + res.data.message)
                }
            })
            .catch(error => {
                console.log(error)
            })
        setEditBox(false)
    }

    const[editBox, setEditBox] = useState(false)

    const planNameRef = useRef();
    /**
     * @author Pete To
     * @version 1.0
     * This variable will hold all plans created by the user
     * the key of the elements is planId
     * @type {plan[]}
     */
    const showTripInfo = tripInfo.map(element =>
        <div className="box" key={element.planId}>
            {/*{props.status === "paid" ?*/}
            {/*    <h2 className="trip-status-paid"><i className="fa-solid fa-circle"/> Status: <span>{props.status}</span></h2>*/}
            {/*    :*/}
            {/*    <h2 className="trip-status"><i className="fa-solid fa-circle"/> Status: <span>{props.status}</span></h2>*/}
            {/*}*/}
            <div className="image">
                <img src={CastleService.switchPhotoByCastleName(element.castleName)} alt=""/>
            </div>
            <div className="content">
                {!editBox?
                    <h3 className="trip-name">
                        Trip Name: {element.planName} <i onClick={changePlanName} className="fa-solid fa-pen-to-square edit-icon"/>
                    </h3>
                    :
                    <div className="trip-name">
                        <input
                            type="text"
                            placeholder="trip name"
                            className="name-input"
                            ref={planNameRef}
                        />
                        {/* Put PlanName */}
                        <button><i onClick={() => {handlePlanName(element.planId)}} className="fa-solid fa-check edit-icon"/></button>
                    </div>
                }
                <div className="details">
                    <h3><i className="fa-solid fa-location-dot"/> <span>{element.castleName}</span></h3>
                    <h3><i className="fa-solid fa-house"/> <span>Newcastle City Centre</span></h3>
                    <h3><i className="fa-solid fa-calendar-check"/> <span>{DateFunction.convertDate(element.date)}</span></h3>
                    {/*<h3><i className="fa-solid fa-note-sticky"/> <span>Some notes here</span></h3>*/}
                </div>
                <div className="actions">
                    {props.status === "paid"?
                        <button onClick={viewOrder} className="btn"><i className="fa-solid fa-calendar-day"/> view</button>
                        :
                        <button onClick={() => viewPlan(element.planId)} className="btn"><i className="fa-solid fa-calendar-day"/> view</button>
                    }
                    <button onClick={() => deletePlan(element.planId)} className="btn cancel-btn"><i className="fa-solid fa-trash"/> delete</button>
                </div>
            </div>
        </div>
    )

    /**
     * @author Pete To
     * @version 1.0
     * This variable will hold all orders created by the user
     * the key of the elements is orderId
     * @type {order[]}
     */
    const showOrderInfo = tripInfo.map(element =>
        <div className="box" key={element.orderId}>
            {props.status === "paid" ?
                <h2 className="trip-status-paid"><i className="fa-solid fa-circle"/> Status: <span>{props.status}</span></h2>
                :
                <h2 className="trip-status"><i className="fa-solid fa-circle"/> Status: <span>{props.status}</span></h2>
            }
            <div className="image">
                <img src={CastleService.switchPhotoByCastleName(element.castleName)} alt=""/>
            </div>
            <div className="content">
                <h3 className="trip-name">Trip Name: {element.planName}</h3>
                <div className="details">
                    <h3><i className="fa-solid fa-location-dot"/> <span>{element.castleName}</span></h3>
                    <h3><i className="fa-solid fa-house"/> <span>{element.startPoint}</span></h3>
                    <h3><i className="fa-solid fa-calendar-check"/> <span>{DateFunction.convertDate(element.date)}</span></h3>
                    {/*<h3><i className="fa-solid fa-note-sticky"/> <span>Some notes here</span></h3>*/}
                </div>
                {props.status === "paid"?
                    <div className="actions">
                        <button onClick={() => viewOrder(element.orderId)} className="btn"><i className="fa-solid fa-calendar-day"/> view</button>
                        <button onClick={() => deleteOrder(element.orderId)} className="btn cancel-btn"><i className="fa-solid fa-trash"/> delete</button>
                    </div>
                    :
                    <div className="actions">
                        <button onClick={() => viewPlan(element.planId)} className="btn"><i className="fa-solid fa-calendar-day"/> view</button>
                        <button onClick={() => deletePlan(element.planId)} className="btn cancel-btn"><i className="fa-solid fa-trash"/> delete</button>
                    </div>
                }
            </div>
        </div>
    )

    /**
     * @author Pete To
     * @version 1.0
     * The display contents of UserCreatedTrips component based on the type of the user is querying
     * if it is a plan: contents based on the variable "showTripInfo"
     * if it is an order: contents based on the variable "showOrderInfo"
     */
    return(
        <section className="created-trips">

            <div className="top">
                {props.status === "paid" ?
                    <h1 className="heading">Orders created (Latest 10 orders)</h1>
                    :
                    <h1 className="heading">Plans created (Latest 10 plans)</h1>
                }

                {props.status === "unpaid" ?
                    <Link to="/trip-name" className="created-trips--newTrips"><i className="fa-solid fa-calendar-plus"/>New Trip</Link>
                    :
                    <Link to="/profile" className="created-trips--newTrips"><i className="fa-solid fa-shopping-cart"/>View Profile</Link>
                }
            </div>

            {emptyMessage &&
                <div className="empty-container">
                    <h1 className="heading empty-heading">{emptyMessage}</h1>
                </div>
            }

            <div className="bottom">
                <div className="box-container">

                    {props.status === "unpaid" && showTripInfo}

                    {props.status === "paid" && showOrderInfo}


                </div>

            </div>
        </section>
    )
}