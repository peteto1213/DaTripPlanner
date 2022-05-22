import React, {useEffect, useRef, useState} from "react";
import CastleImages from "../../data/CastleImages";
import CastleInfo from "../../data/CastleInfo";
import {useHistory, Link} from "react-router-dom";
import PlanService from "../../service/PlanService";
import DateFunction from "../../CustomFunction/DateFunction";
import CastleService from "../../service/CastleService";
import OrderService from "../../service/OrderService";

export default function UserTripDetails(props){

    const history = useHistory();

    //state hook to hold the plan details passed from the API
    const[planDetail, setPlanDetail] = useState({});
    const[priceTotal, setPriceTotal] = useState(0);

    //state hook to hold the order details passed from the API
    const[orderDetail, setOrderDetail] = useState({});
    //state hook to hold the castle information contained in the order details
    const[orderCastle, setOrderCastle] = useState({});

    /**
     * @author Pete To
     * @version 1.0
     * This function will return corresponding mapLink according to the castleName of the plan/order details
     * @param castleName
     * @returns {mapLink} @type - String
     */
    function getMapLink(castleName){
        switch (castleName){
            case "Alnwick Castle":
                return CastleInfo[0].mapLink;

            case "Auckland Castle":
                return CastleInfo[1].mapLink;

            case "Bamburgh Castle":
                return CastleInfo[2].mapLink;

            case "Barnard Castle":
                return CastleInfo[3].mapLink;

            default:
                return CastleInfo[0].mapLink;
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return corresponding image path according to the castleId of the plan/order details
     * @param castleId
     * @returns {image}
     */
    function getImage(castleId){
        switch (castleId){
            case 1:
                return CastleImages[0].images[1];

            case 2:
                return CastleImages[1].images[1];

            case 3:
                return CastleImages[2].images[1];

            case 4:
                return CastleImages[3].images[1];

            default:
                return CastleImages[0].images[1];
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return a to-do list around the castle based on the castleId parameter
     * @param castleId
     * @returns {[todoList]}
     */
    function getTodo(castleId){
        switch (castleId){
            case 1:
                return CastleInfo[0].to_do;

            case 2:
                return CastleInfo[1].to_do;

            case 3:
                return CastleInfo[2].to_do;

            case 4:
                return CastleInfo[3].to_do;

            default:
                return CastleInfo[0].to_do;
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will push the user back to select another plan to view
     * if the user is at the view plan detail section
     */
    function toCreatedPlans(){
        history.push("/created-trips")
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will push the user back to select another order to view
     * if the user is at the view order detail section
     */
    function toCreatedOrders(){
        history.push("/created-orders")
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will push user to the checkout page
     * if the user is at the view plan detail section
     * total price for the plan will be stored at local storage for later retrieval
     */
    function toCheckout(){
        localStorage.setItem("planTotal", priceTotal);
        history.push('/checkout');
    }

    /**
     * @author Pete To
     * @version 1.0
     * If the response type is plan, this function will be called to:
     * 1. set the local state variable (PlanDetail) according to the data from the response
     * 2. display any errors if any errors found/the res status code is not 200
     * 3. if res.code is 200, calculate the total price for the incoming plan dynamically according to castleTicket, busticket and
     * @param res
     */
    function handlePlanData(res){
        if(res.data.code === 200){
            setPlanDetail(res.data.data)

            console.log(res.data.data)

            //Add up total
            setPriceTotal(res.data.data.castleTicketPrice);
        }
        if(res.data.data.busPlanDetails){
            res.data.data.busPlanDetails.map(element => {
                setPriceTotal(prevTotal => prevTotal + element.busTicketPrice * element.busPlanQuantity);
            })
        }
        if(res.data.data.trainPlanDetails){
            res.data.data.trainPlanDetails.map(element => {
                setPriceTotal(prevTotal => prevTotal + element.trainTicketPrice * element.trainPlanQuantity);
            })
        }else{
            console.log(res.data.message)
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * If the response type is order details, this function will be called
     * 1. set the state variable(OrderDetail) with the incoming response data
     * 2. set the state variable(orderCastle) with the orderDetail castle information object
     * @param res
     */
    function handleOrderData(res){
        if(res.data.code === 200){
            setOrderDetail(res.data.data)
            setOrderCastle(res.data.data.castleOrderInfoResponse)
        }else{
            console.log(res.data.message)
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * This effect will render once
     * According to the required type, different fetching function from the Plan/Order Service will be called
     * 1. plan - call viewPlanDetails function
     * 2. order - call viewOrderDetails function
     * 3. any errors detected will be displayed to the user with the corresponding
     */
    useEffect(() => {
        if(props.type === "plan"){
            //call viewPlanDetails function
            if(localStorage.getItem("currentPlanId")){
                PlanService.viewPlanDetails(localStorage.getItem("currentPlanId"))
                    .then((res) => {
                        handlePlanData(res)
                    })
                    .catch(error => {
                        console.log(error)
                    })
            }else{
                alert("please select your plan again")
                history.push('/created-trips')
            }
        }
        //call viewOrderDetails function
        else{
            if(localStorage.getItem("currentOrderId")){
                OrderService.viewOrderDetails(localStorage.getItem("currentOrderId"))
                    .then((res) => {
                        console.log(res.data.data)
                        handleOrderData(res)
                    })
                    .catch(error => {
                        console.log(error)
                    })
            }else{
                alert("please select your order again")
                history.push('/created-orders')
            }
        }
    },[])

    /**
     * @author Pete To
     * @version 1.0
     * The content of the UserTripDetails component depends on the type of user actions
     * 1. Order - show order details and checkout option
     * 2. Plan - only show plan details
     */
    return(
        <>
            {props.type === "plan" ?
                //Plan content starts here
                <section className="user-trip-details">
                    <h1 className="heading">Trip Name: {planDetail.planName}</h1>

                    <div className="column first-column">
                        <div className="column-cells column-cells-left">
                            <h3 className="trip-details">Targeted Castle: <span>{planDetail.castleName}</span></h3>
                            <h3 className="trip-details">Date: <span>{DateFunction.convertDate(planDetail.castleTravelDate)}</span></h3>
                            <h3 className="trip-details">Number of Guests: <span>{planDetail.guestNumber}</span></h3>
                            <h3 className="trip-details">Order Status: <span>unpaid</span></h3>

                        </div>

                        <div className="column-cells">
                            <h3>Position of {planDetail.castleName}</h3>
                            <iframe
                                src={getMapLink(planDetail.castleName)}
                                width="600" height="450"
                            />
                        </div>
                    </div>

                    <div className="wrapper">
                        <div className="column">
                            <h1><Link to={CastleService.moreInformationRouter(planDetail.castleId)}>{planDetail.castleName}</Link></h1>
                        </div>

                        <div className="column">
                            <div className="column-cells">
                                <img src={getImage(planDetail.castleId)} alt=""/>
                            </div>

                            <div className="column-cells">
                                <div className="cell-content">
                                    <h3>Castle Description</h3>
                                    <p>{planDetail.castleDescription}</p>
                                </div>

                                <div className="cell-content">
                                    <h3>Things to do in {planDetail.castleName}</h3>
                                    {getTodo(planDetail.castleId).map(element =>
                                        <li><a href={element.link}>{element.name}</a></li>
                                    )}
                                </div>
                            </div>
                        </div>
                    </div>


                    <div className="wrapper">
                        <div className="column">
                            <h1><i className="fa-brands fa-fort-awesome" /> Castle Ticket details</h1>
                        </div>

                        <div className="column ticket-column">

                            <div className="ticket-box">
                                <div className="left">
                                    <div className="ticket-box-cell">
                                        <p>Castle Name</p>
                                        <h3>{planDetail.castleName}</h3>
                                    </div>

                                    <div className="ticket-box-cell">
                                        <p>Date</p>
                                        <h3>{DateFunction.convertDate(planDetail.date)}</h3>
                                    </div>

                                    <div className="ticket-box-cell">
                                        <p>Ticket Type</p>
                                        <h3>{planDetail.castleTicketType}</h3>
                                    </div>
                                </div>

                                <div className="right">
                                    <div className="ticket-box-cell">
                                        <p>Ticket id</p>
                                        <h3>{planDetail.castleTicketId}</h3>
                                    </div>
                                    <div className="ticket-box-cell">
                                        <p>Quantity</p>
                                        <h3>{planDetail.guestNumber}</h3>
                                    </div>
                                    {props.type === "order" &&
                                        <div className="ticket-box-cell">
                                            <button className="btn">download</button>
                                        </div>
                                    }
                                </div>
                            </div>

                        </div>
                    </div>

                    <div className="wrapper">
                        <div className="column">
                            <h1><i className="fa-solid fa-bus" /> Transportation Ticket details</h1>
                        </div>

                        <div className="column ticket-column">

                            {/* Map any bus ticket here according to type */}
                            {planDetail.busPlanDetails &&
                                planDetail.busPlanDetails.map(element =>
                                    <div className="ticket-box">
                                        <div className="left">
                                            <div className="ticket-box-cell">
                                                <p>Route no./Operator</p>
                                                <h3>{element.busName}/{element.busOperator}</h3>
                                            </div>

                                            <div className="ticket-box-cell">
                                                <p>Date</p>
                                                <h3>{DateFunction.convertDate(element.busTravelDate)}</h3>
                                            </div>

                                            <div className="ticket-box-cell">
                                                <p>Time</p>
                                                <h3>{DateFunction.convertTime(element.busTravelTime)}</h3>
                                            </div>

                                            <div className="ticket-box-cell">
                                                <p>Ticket Type</p>
                                                <h3>{element.busTicketType}</h3>
                                            </div>
                                        </div>

                                        <div className="right">
                                            <div className="ticket-box-cell">
                                                <p>Ticket id</p>
                                                <h3>{element.busTicketId}</h3>
                                            </div>
                                            <div className="ticket-box-cell">
                                                <p>Quantity</p>
                                                <h3>{element.busPlanQuantity}</h3>
                                            </div>
                                            <div className="ticket-box-cell">
                                                <p>boarding point</p>
                                                <h3>{element.startPoint}</h3>
                                            </div>
                                            <div className="ticket-box-cell">
                                                <p>takeoff point</p>
                                                <h3>{element.endPoint}</h3>
                                            </div>
                                            {props.type === "order" &&
                                                <div className="ticket-box-cell">
                                                    <button className="btn">download</button>
                                                </div>
                                            }
                                        </div>
                                    </div>
                                )
                            }

                            {(!planDetail.busPlanDetails && !planDetail.trainPlanDetails) &&
                                <div className="empty-heading-container">
                                    <h1 className="heading empty-heading">You haven't added any transportation ticket</h1>
                                </div>
                            }

                            {/* Map any train ticket here according to type */}
                            {planDetail.trainPlanDetails &&
                                planDetail.trainPlanDetails.map(element =>
                                    <div className="ticket-box">
                                        <div className="left">
                                            <div className="ticket-box-cell">
                                                <p>Route no./Operator</p>
                                                <h3>{element.trainName}/{element.trainOperator}</h3>
                                            </div>

                                            <div className="ticket-box-cell">
                                                <p>Date</p>
                                                <h3>{DateFunction.convertDate(element.trainTravelDate)}</h3>
                                            </div>

                                            <div className="ticket-box-cell">
                                                <p>Time</p>
                                                <h3>{DateFunction.convertTime(element.trainTravelTime)}</h3>
                                            </div>

                                            <div className="ticket-box-cell">
                                                <p>Ticket Type</p>
                                                <h3>{element.trainTicketType}</h3>
                                            </div>
                                        </div>

                                        <div className="right">
                                            <div className="ticket-box-cell">
                                                <p>Ticket id</p>
                                                <h3>{element.trainTicketId}</h3>
                                            </div>
                                            <div className="ticket-box-cell">
                                                <p>Quantity</p>
                                                <h3>{element.trainPlanQuantity}</h3>
                                            </div>
                                            <div className="ticket-box-cell">
                                                <p>boarding point</p>
                                                <h3>{element.startPoint}</h3>
                                            </div>
                                            <div className="ticket-box-cell">
                                                <p>takeoff point</p>
                                                <h3>{element.endPoint}</h3>
                                            </div>
                                            {props.type === "order" &&
                                                <div className="ticket-box-cell">
                                                    <button className="btn">download</button>
                                                </div>
                                            }
                                        </div>
                                    </div>
                                )
                            }
                        </div>
                    </div>

                    {props.type === "plan" &&
                        <div className="wrapper">
                            <div className="column">
                                <h1><i className="fa-solid fa-bag-shopping"/> Invoice details</h1>
                            </div>

                            {/*  invoice here   */}
                            <table className="table">
                                <thead>
                                <tr>
                                    <th>Item</th>
                                    <th>Type</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Line Total</th>
                                </tr>
                                </thead>

                                <tbody>
                                {/*  Map here with multiple tickets */}
                                <tr>
                                    <td>{planDetail.castleTicketType}</td>
                                    <td>Castle Ticket</td>
                                    <td><i className="fa-solid fa-sterling-sign"/>{planDetail.castleTicketPrice}</td>
                                    <td>{planDetail.guestNumber}</td>
                                    <td>{Math.round(planDetail.castleTicketPrice * planDetail.guestNumber * 100)/100}</td>
                                </tr>


                                {/* Map any bus ticket here according to type */}
                                {planDetail.busPlanDetails &&
                                    planDetail.busPlanDetails.map(element =>
                                        <tr>
                                            <td>{element.busName} ({element.busOperator})</td>
                                            <td>{element.busTicketType}</td>
                                            <td><i className="fa-solid fa-sterling-sign"/>{element.busTicketPrice}</td>
                                            <td>{element.busPlanQuantity}</td>
                                            <td>{Math.round(element.busTicketPrice * element.busPlanQuantity * 100)/100}</td>
                                        </tr>
                                    )
                                }

                                {/* Map any train ticket here according to type */}
                                {planDetail.trainPlanDetails &&
                                    planDetail.trainPlanDetails.map(element =>
                                        <tr>
                                            <td>{element.trainName} ({element.trainOperator})</td>
                                            <td>{element.trainTicketType}</td>
                                            <td><i className="fa-solid fa-sterling-sign"/>{element.trainTicketPrice}</td>
                                            <td>{element.trainPlanQuantity}</td>
                                            <td>{Math.round(element.trainTicketPrice * element.trainPlanQuantity * 100) / 100}</td>
                                        </tr>
                                    )
                                }
                                {/* Map ends here */}

                                </tbody>
                            </table>
                            {/*  invoice ends here   */}

                            <div className="price-row">
                                {/* Round up to the 2 decimal places */}
                                <h3>Price Total: <span><i className="fa-solid fa-sterling-sign"/> {Math.round(priceTotal * 100) / 100}</span></h3>
                            </div>
                        </div>
                    }


                    <div className="button-column">
                        {props.type === "plan" ?
                            <button onClick={toCreatedPlans} className="btn back-btn"><i className="fa-solid fa-arrow-rotate-left"/> Back to created plans</button>
                            :
                            <button onClick={toCreatedOrders} className="btn back-btn"><i className="fa-solid fa-arrow-rotate-left"/> Back to created orders</button>
                        }
                        {props.type === "plan" &&
                            <button onClick={toCheckout} className="btn checkout-btn"><i className="fa-solid fa-cart-shopping"/> checkout</button>
                        }
                    </div>

                </section>
                //Plan content ends here
                :
                //Order content starts here
                <section className="user-trip-details">
                    <h1 className="heading">trip name: {orderDetail.planName}</h1>

                    <div className="column first-column">
                        <div className="column-cells column-cells-left">
                            <h3 className="trip-details">Targeted Castle: <span>{orderCastle.castleName}</span></h3>
                            <h3 className="trip-details">Date: <span>{DateFunction.convertDate(orderCastle.castleTicketTravelDate)}</span></h3>
                            <h3 className="trip-details">Number of Guests: <span>{orderCastle.guestNumber}</span></h3>
                            <h3 className="trip-details">Order Status: <span>Paid</span></h3>
                        </div>

                        <div className="column-cells">
                            <h3>Position of {orderCastle.castleName}</h3>
                            <iframe
                                src={getMapLink(orderCastle.castleName)}
                                width="600" height="450"
                            />
                        </div>
                    </div>

                    <div className="wrapper">
                        <div className="column">
                            <h1><Link to={CastleService.switchLocationByCastleName(orderCastle.castleName)}>{orderCastle.castleName}</Link></h1>
                        </div>

                        <div className="column">
                            <div className="column-cells">
                                <img src={CastleService.switchPhotoByCastleName(orderCastle.castleName)} alt=""/>
                            </div>

                            <div className="column-cells">
                                <div className="cell-content">
                                    <h3>Castle Description</h3>
                                    <p>{CastleInfo[CastleService.getCastleIdByCastleName(orderCastle.castleName) - 1].castleDescription}</p>
                                </div>

                                <div className="cell-content">
                                    <h3>Things to do in {orderCastle.castleName}</h3>
                                    {getTodo(CastleService.getCastleIdByCastleName(orderCastle.castleName)).map(element =>
                                        <li><a href={element.link}>{element.name}</a></li>
                                    )}
                                </div>
                            </div>
                        </div>
                    </div>


                    <div className="wrapper">
                        <div className="column">
                            <h1><i className="fa-brands fa-fort-awesome" /> Castle Ticket details</h1>
                        </div>

                        <div className="column ticket-column">

                            <div className="ticket-box">
                                <div className="left">
                                    <div className="ticket-box-cell">
                                        <p>Castle Name</p>
                                        <h3>{orderCastle.castleName}</h3>
                                    </div>

                                    <div className="ticket-box-cell">
                                        <p>Date</p>
                                        <h3>{DateFunction.convertDate(orderCastle.castleTicketTravelDate)}</h3>
                                    </div>

                                    <div className="ticket-box-cell">
                                        <p>Ticket Type</p>
                                        <h3>{orderCastle.castleTicketType}</h3>
                                    </div>
                                </div>

                                <div className="right">
                                    <div className="ticket-box-cell">
                                        <p>Quantity</p>
                                        <h3>{orderCastle.guestNumber}</h3>
                                    </div>
                                    {props.type === "order" &&
                                        <div className="ticket-box-cell">
                                            <button className="btn">download</button>
                                        </div>
                                    }
                                </div>
                            </div>

                        </div>
                    </div>

                    <div className="wrapper">
                        <div className="column">
                            <h1><i className="fa-solid fa-bus" /> Transportation Ticket details</h1>
                        </div>

                        <div className="column ticket-column">
                            {/* Map any bus ticket here according to type */}
                            {orderDetail.busPlanDetails &&
                                orderDetail.busPlanDetails.map(element =>
                                    <div className="ticket-box">
                                        <div className="left">
                                            <div className="ticket-box-cell">
                                                <p>Route no./Operator</p>
                                                <h3>{element.busName}/{element.busOperator}</h3>
                                            </div>

                                            <div className="ticket-box-cell">
                                                <p>Date</p>
                                                <h3>{DateFunction.convertDate(element.busTravelDate)}</h3>
                                            </div>

                                            <div className="ticket-box-cell">
                                                <p>Time</p>
                                                <h3>{DateFunction.convertTime(element.busTravelTime)}</h3>
                                            </div>

                                            <div className="ticket-box-cell">
                                                <p>Ticket Type</p>
                                                <h3>{element.busTicketType}</h3>
                                            </div>
                                        </div>

                                        <div className="right">
                                            <div className="ticket-box-cell">
                                                <p>Ticket id</p>
                                                <h3>{element.busTicketId}</h3>
                                            </div>
                                            <div className="ticket-box-cell">
                                                <p>Quantity</p>
                                                <h3>{element.busOrderQuantity}</h3>
                                            </div>
                                            <div className="ticket-box-cell">
                                                <p>boarding point</p>
                                                <h3>{element.startPoint}</h3>
                                            </div>
                                            <div className="ticket-box-cell">
                                                <p>takeoff point</p>
                                                <h3>{element.endPoint}</h3>
                                            </div>
                                            {props.type === "order" &&
                                                <div className="ticket-box-cell">
                                                    <button className="btn">download</button>
                                                </div>
                                            }
                                        </div>
                                    </div>
                                )
                            }

                            {/* Map any train ticket here according to type */}
                            {orderDetail.trainPlanDetails &&
                                orderDetail.trainPlanDetails.map(element =>
                                    <div className="ticket-box">
                                        <div className="left">
                                            <div className="ticket-box-cell">
                                                <p>Route no./Operator</p>
                                                <h3>{element.trainName}/{element.trainOperator}</h3>
                                            </div>

                                            <div className="ticket-box-cell">
                                                <p>Date</p>
                                                <h3>{DateFunction.convertDate(element.trainTravelDate)}</h3>
                                            </div>

                                            <div className="ticket-box-cell">
                                                <p>Time</p>
                                                <h3>{DateFunction.convertTime(element.trainTravelTime)}</h3>
                                            </div>

                                            <div className="ticket-box-cell">
                                                <p>Ticket Type</p>
                                                <h3>{element.trainTicketType}</h3>
                                            </div>
                                        </div>

                                        <div className="right">
                                            <div className="ticket-box-cell">
                                                <p>Ticket id</p>
                                                <h3>{element.trainTicketId}</h3>
                                            </div>
                                            <div className="ticket-box-cell">
                                                <p>Quantity</p>
                                                <h3>{element.trainOrderQuantity}</h3>
                                            </div>
                                            <div className="ticket-box-cell">
                                                <p>boarding point</p>
                                                <h3>{element.startPoint}</h3>
                                            </div>
                                            <div className="ticket-box-cell">
                                                <p>takeoff point</p>
                                                <h3>{element.endPoint}</h3>
                                            </div>
                                            {props.type === "order" &&
                                                <div className="ticket-box-cell">
                                                    <button className="btn">download</button>
                                                </div>
                                            }
                                        </div>
                                    </div>
                                )
                            }

                            {!orderDetail.trainPlanDetails &&
                                <div className="empty-heading-container">
                                    <h1 className="heading empty-heading">You didn't have any transportation ticket for your order</h1>
                                </div>
                            }

                        </div>
                    </div>

                    {/* Invoice section */}
                    {props.type === "plan" &&
                        <div className="wrapper">
                            <div className="column">
                                <h1><i className="fa-solid fa-bag-shopping"/> Invoice details</h1>
                            </div>

                            {/*  invoice here   */}
                            <table className="table">
                                <thead>
                                <tr>
                                    <th>Item</th>
                                    <th>Type</th>
                                    <th>Price</th>
                                    <th>Quantity</th>
                                    <th>Line Total</th>
                                </tr>
                                </thead>

                                <tbody>
                                {/*  Map here with multiple tickets */}
                                <tr>
                                    <td>{planDetail.castleTicketType}</td>
                                    <td>Castle Ticket</td>
                                    <td><i className="fa-solid fa-sterling-sign"/>{planDetail.castleTicketPrice}</td>
                                    <td>{planDetail.guestNumber}</td>
                                    <td>{Math.round(planDetail.castleTicketPrice * planDetail.guestNumber * 100)/100}</td>
                                </tr>


                                {/* Map any bus ticket here according to type */}
                                {planDetail.busPlanDetails &&
                                    planDetail.busPlanDetails.map(element =>
                                        <tr>
                                            <td>{element.busName} ({element.busOperator})</td>
                                            <td>{element.busTicketType}</td>
                                            <td><i className="fa-solid fa-sterling-sign"/>{element.busTicketPrice}</td>
                                            <td>{element.busPlanQuantity}</td>
                                            <td>{Math.round(element.busTicketPrice * element.busPlanQuantity * 100)/100}</td>
                                        </tr>
                                    )
                                }

                                {/* Map any train ticket here according to type */}
                                {planDetail.trainPlanDetails &&
                                    planDetail.trainPlanDetails.map(element =>
                                        <tr>
                                            <td>{element.trainName} ({element.trainOperator})</td>
                                            <td>{element.trainTicketType}</td>
                                            <td><i className="fa-solid fa-sterling-sign"/>{element.trainTicketPrice}</td>
                                            <td>{element.trainPlanQuantity}</td>
                                            <td>{Math.round(element.trainTicketPrice * element.trainPlanQuantity * 100) / 100}</td>
                                        </tr>
                                    )
                                }
                                {/* Map ends here */}

                                </tbody>
                            </table>
                            {/*  invoice ends here   */}

                            <div className="price-row">
                                {/* Round up to the 2 decimal places */}
                                <h3>Price Total: <span><i className="fa-solid fa-sterling-sign"/> {Math.round(priceTotal * 100) / 100}</span></h3>
                            </div>
                        </div>
                    }


                    <div className="button-column">
                        {props.type === "plan" ?
                            <button onClick={toCreatedPlans} className="btn back-btn"><i className="fa-solid fa-arrow-rotate-left"/> Back to created plans</button>
                            :
                            <button onClick={toCreatedOrders} className="btn back-btn"><i className="fa-solid fa-arrow-rotate-left"/> Back to created orders</button>
                        }
                        {props.type === "plan" &&
                            <button onClick={toCheckout} className="btn checkout-btn"><i className="fa-solid fa-cart-shopping"/> checkout</button>
                        }
                    </div>

                </section>
                //Order content ends here
            }

        </>
    )
}