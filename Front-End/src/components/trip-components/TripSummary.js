import React, {useEffect, useState} from "react";
import {Link, useHistory, useLocation} from "react-router-dom";
import DateFunction from "../../CustomFunction/DateFunction";
import PlanService from "../../service/PlanService";
import SystemFunction from "../../CustomFunction/SystemFunction";

export default function TripSummary(){

    const history = useHistory(); //hook created to push to other components

    //used to hold the castle ticket object array from localStorage
    const[castleTickets, setCastleTickets] = useState([]);

    //used to hold the inbound transportation ticket object array from localStorage
    const[transportTickets, setTransportTickets] = useState([]);

    //used to hold the outbound transportation ticket object array from localStorage
    const[transportTicketsOutbound, setTransportTicketsOutbound] = useState([]);

    //used to calculate the total price of all the tickets, default: 0
    const[price, setPrice] = useState(0);

    const lastLocation = useLocation();//record user's location at this point

    /**
     * @author Pete To
     * @version 1.0
     * The effect will render once, to do the following:
     * 1. save castleTicket from localStorage to local state
     * 2. save inboundTransportationTicket from localStorage to local state
     * 3. save outboundTransportationTicket from localStorage to local state
     * 4. calculate to the total price of all tickets, save to local sate
     */
    useEffect(() => {
        let total = 0;
        if(localStorage.getItem("castleTickets")){
            let array = JSON.parse(localStorage.getItem("castleTickets"));
            setCastleTickets(array);
            array.map(element => {
                total = total + element.price * element.quantity;
            });
        }

        if(localStorage.getItem("transportTickets")){
            let array = JSON.parse(localStorage.getItem("transportTickets"));
            setTransportTickets(array);
            array.map(element => {
                total = total + element.price * element.ticketQuantity;
            })
        }

        if(localStorage.getItem("transportTicketsOutbound")){
            let array = JSON.parse(localStorage.getItem("transportTicketsOutbound"));
            setTransportTicketsOutbound(array);
            array.map(element => {
                total = total + element.price * element.ticketQuantity;
            })
        }

        setPrice(total);
    },[])

    /**
     * @author Pete To
     * @version 1.0
     * use the map method to display the parameters of castleTickets saved to the local state
     * @type {castleTickets[]}
     */
    const showCastleTickets = castleTickets.map(element =>
        element.quantity !== 0 &&
        <div className="card">
            <h3>Type: <span>{element.type}</span></h3>
            <h3>Date: <span>{localStorage.getItem("date")}</span></h3>
            <h3>Price: <span>{element.price === 0 ? "free" : element.price}</span></h3>
            <h3>Quantity: <span>{element.quantity}</span></h3>
        </div>
    )

    /**
     * @author Pete To
     * @version 1.0
     * use the map method to display the parameters of transportTickets saved to the local state
     * @type {transportTickets[]}
     */
    const showTransportTickets = transportTickets.map(element =>
        element.ticketQuantity !== 0 &&
        <div className="card">
            <h3>Type: <span>{element.type} Ticket</span></h3>
            <h3>Route/Operator: <span>{element.operator} Ticket</span></h3>
            <h3></h3>
            <h3>Date: <span>{localStorage.getItem("date")}</span></h3>
            <h3>Price: <span>{element.price === 0 ? "free" : element.price}</span></h3>
            <h3>Quantity: <span>{element.ticketQuantity}</span></h3>
        </div>
    )

    /**
     * @author Pete To
     * @version 1.0
     * use the map method to display the parameters of outbound transportTickets saved to the local state
     * @type {transportTicketsOutbound[]}
     */
    const showTransportTicketsOutbound = transportTicketsOutbound.map(element =>
        element.ticketQuantity !== 0 &&
        <div className="card">
            <h3>Type: <span>{element.type} Ticket</span></h3>
            <h3>Route/Operator: <span>{element.operator} Ticket</span></h3>
            <h3>Date: <span>{localStorage.getItem("date")}</span></h3>
            <h3>Price: <span>{element.price === 0 ? "free" : element.price}</span></h3>
            <h3>Quantity: <span>{element.ticketQuantity}</span></h3>
        </div>
    )

    /**
     * @author Pete To
     * @version 1.0
     * This function transfer the data saved in the local state from Object Array to Map
     * return a new object with all the plan details ready to be passed to Rest API
     * {
     *     planName: String,
     *     castleTicketNum: Map,
     *     busTicketNum: Map,
     *     trainTicketNum: Map,
     * }
     * @returns {{busTicketNum: Map<any, any>, castleTicketNum: Map<any, any>, trainTicketNum: Map<any, any>}}
     */
    function transferData(){
        let castleTicketNum = new Map();
        castleTickets.map(element => {
            castleTicketNum.set(element.castleTicketId, parseInt(element.quantity))
        });

        let busTicketNum = new Map();
        transportTickets.map(element => {
            if(element.type === "bus")
            busTicketNum.set(element.ticketId, parseInt(element.ticketQuantity))
        });
        transportTicketsOutbound.map(element => {
            if(element.type === "bus")
                busTicketNum.set(element.ticketId, parseInt(element.ticketQuantity))
        });

        let trainTicketNum = new Map();
        transportTickets.map(element => {
            if(element.type === "train")
                trainTicketNum.set(element.ticketId, parseInt(element.ticketQuantity))
        });
        transportTicketsOutbound.map(element => {
            if(element.type === "train")
                trainTicketNum.set(element.ticketId, parseInt(element.ticketQuantity))
        });

        return {castleTicketNum: castleTicketNum, busTicketNum: busTicketNum, trainTicketNum: trainTicketNum};
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function checks if the user has logged in or not
     * 1. Not Logged in: redirect user to the login page
     * 2. Logged in:
     * - pass the transferred Data {planDetails} to API to create a plan and save to database
     * - if successful: alert user "plan have saved successfully", redirect the user to view all of their created plans
     * - if not successful: alert the user "saving action not successful" and display the error message
     */
    function switchDestination(){
        if(localStorage.getItem("email")){
            //save user email
            let email = localStorage.getItem("email")

            //create planDetails holding all the info required to passed to backend
            let planDetails = {
                planName: localStorage.getItem("tripName"),
                castleTicketNum: Object.fromEntries(transferData().castleTicketNum),
                busTicketNum: Object.fromEntries(transferData().busTicketNum),
                trainTicketNum: Object.fromEntries(transferData().trainTicketNum)
            }

            console.log(planDetails)
            console.log(JSON.stringify(planDetails))

            //post the required planDetails to the api
            PlanService.createPlan(planDetails)
                .then((res) => {
                    if(res.data.code === 200){
                        console.log(res)
                        alert("Plan saved successfully")

                        //delete all the localStorage data
                        localStorage.clear()
                        localStorage.setItem("email", email)

                        history.push("/created-trips")
                    }else{
                        alert("Plan Save NOT successful")
                        console.log(res.data.message)
                    }
                })
                .catch(error => {
                    console.log(error)
                })
        }else{
            //redirect the user to login page
            history.push(`/login?redirectTo=${lastLocation.pathname}`);
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * TripSummary Component
     * 1. Display tripDetails
     * 2. calculate estimation price total of the trip
     */
    return(
        <section className="trip-section">
            <h1 className="heading">Trip Summary</h1>

            <div className="trip-summary-table">
                <div className="box">
                    <p>Name of your trip</p>
                    <h3>{localStorage.getItem("tripName")}</h3>
                </div>
                <div className="box">
                    <p>Targeted Castle</p>
                    <h3>{localStorage.getItem("castleName")} Castle</h3>
                </div>
                <div className="box">
                    <p>Trip Date</p>
                    <h3>{localStorage.getItem("date")}</h3>
                </div>
                <div className="box">
                    <p>Start time</p>
                    <h3>{DateFunction.convertTime(localStorage.getItem("time"))}</h3>
                </div>
                <div className="box">
                    <p>Castle tickets</p>
                    <div className="card-container">
                        {showCastleTickets}
                    </div>
                </div>
                <div className="box">
                    <p>Inbound Transportation tickets</p>
                    <div className="card-container">
                        {showTransportTickets}
                    </div>
                </div>
                <div className="box">
                    <p>Return time</p>
                    <h3>{DateFunction.convertTime(localStorage.getItem("returnTime"))}</h3>
                </div>
                <div className="box">
                    <p>Outbound Transportation tickets</p>
                    <div className="card-container">
                        {showTransportTicketsOutbound}
                    </div>
                </div>
                <div className="box">
                    <p>Est. Price Total:</p>
                    <h3><i className="fa-solid fa-sterling-sign"/> {Math.round(price * 100) / 100}</h3>
                </div>
            </div>

            <Link to="/trip-transport-outbound" className="btn cancel-btn"><i className="fa-solid fa-circle-arrow-left"/> previous</Link>
            <button onClick={switchDestination} className="btn">save and continue <i className="fa-solid fa-floppy-disk"/></button>


        </section>
    )
}