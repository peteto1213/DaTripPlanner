import React, {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";

export default function TripTransportationDetails(props){
    const history = useHistory();
    //props.details = route (object)
    //props.location = either inbound-route / outbound-route
    console.log(props)
    let ticketArray = props.transportTickets;
    let ticketData = props.ticketData;

    /**
     * write by lei ,
     * This is for handle the submit button.
     *
     */
    function handleSubmit(){
        let currentRouteId = localStorage.getItem("currentRouteId");
        if (currentRouteId !== props.currentRouteId) {
            ticketArray = [];
        }
        //build ticketInfo obj
        let ticketInfo = {
            ticketId: "",
            ticketQuantity: "",
            type: "",
            price: "",
            operator: ""
        };

        ticketInfo.ticketId = ticketData.ticketId;
        ticketInfo.ticketQuantity = quantity;
        ticketInfo.type = ticketData.routeType;
        ticketInfo.price = ticketData.price;
        ticketInfo.operator = ticketData.transportName;

        if (ticketArray === null){
            ticketArray = [];
        }

        //Handle outbound ticketArray
        if(props.type === "outbound"){
            for (let ele of ticketArray) {
                if (ele.ticketId === ticketData.ticketId){
                    ele.ticketQuantity = quantity;
                    return;
                }
            }
            if(localStorage.getItem("transportTicketsOutbound")){
                ticketArray = JSON.parse(localStorage.getItem("transportTicketsOutbound"));
            }
            ticketArray.push(ticketInfo);
            localStorage.setItem("currentRouteId",props.currentRouteId)
            localStorage.setItem("transportTicketsOutbound",JSON.stringify(ticketArray))
            alert("ticket quantity changed successfully")

            history.push("/trip-transport-outbound")
        }
        //Handle inbound ticketArray
        else{
            for (let ele of ticketArray) {
                if (ele.ticketId === ticketData.ticketId){
                    ele.ticketQuantity = quantity;
                    return;
                }
            }
            if(localStorage.getItem("transportTickets")){
                ticketArray = JSON.parse(localStorage.getItem("transportTickets"));
            }
            ticketArray.push(ticketInfo);
            localStorage.setItem("currentRouteId",props.currentRouteId)
            localStorage.setItem("transportTickets",JSON.stringify(ticketArray))
            alert("ticket quantity changed successfully")

            history.push("/trip-transportation-inbound")
        }
    }

    function handleChange(event){
        setQuantity(event.target.value)
    }

    const[quantity, setQuantity] = useState(0);

    useEffect(() => {
        if(quantity > 5){
            setQuantity(5);
        }else if(quantity < 0){
            setQuantity(0);
        }
    },[quantity])

    return(
        <div className="detail-form-container">

            <div className="form-container">
                <h3 className="heading">Please choose your tickets</h3>
                <table className="table">
                    <thead>
                    <tr>
                        <th>No./Operator</th>
                        <th>Bus Code</th>
                        <th>Ticket Type</th>
                        <th>Price</th>
                        <th>Select Quantity</th>
                        <th>Date</th>
                        <th>Time</th>
                    </tr>
                    </thead>

                    <tbody>
                    {/*  Map here if multiple types of tickets per stop */}
                    <tr>
                        <td data-label="No./Operator:">{ticketData.operator}</td>
                        <td data-label="Bus Code:">{ticketData.transportName}</td>
                        <td data-label="Ticket Type:">{ticketData.ticketType}</td>
                        <td data-label="Price:"><i className="fa-solid fa-sterling-sign"/> {ticketData.price}</td>
                        <td data-label="Select Quantity:">
                            <input
                                type="number"
                                name="quantity"
                                max="5"
                                min="0"
                                value={quantity}
                                placeholder="0"
                                onChange={handleChange}
                            />
                        </td>
                        <td data-label="Date:">{ticketData.date}</td>
                        <td data-label="Time:">{ticketData.time}</td>
                    </tr>
                    {/* Map ends here */}

                    </tbody>
                </table>

                <button onClick={handleSubmit} className="btn submit-btn">submit</button>

            </div>
        </div>
    )
}