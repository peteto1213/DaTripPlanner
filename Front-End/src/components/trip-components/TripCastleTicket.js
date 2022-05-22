import React, {useEffect, useState} from "react";
import {Link, useHistory} from "react-router-dom"
import CastleService from "../../service/CastleService";
import DateFunction from "../../CustomFunction/DateFunction";

export default function TripCastleTicket(){
    const history = useHistory(); //push to other components

    //save available ticket objects Array after fetching from API
    const[ticketDetails, setTicketDetails] = useState([{}]);

    //set error message when total tickets amount exceeds 5
    const[errorMessage, setErrorMessage] = useState("")

    //hold total amount of tickets
    const[chosenQuantity, setChosenQuantity] = useState(0)

    //no data: disable = true, data-received: disable = false
    const[buttonState, setButtonState] = useState(true)

    /**
     * @author Pete To
     * @version 1.0
     * This effect will render once to fetch data from the API to get the Axios response
     * to get the ticket Details according to date, time and castleId
     */
    useEffect(() => {
        //set localStorage (castleTickets) to empty string
        localStorage.setItem("castleTickets", "")

        //get date by converting localStorage time from yyyy-mm-dd to yyyymmdd
        let date = DateFunction.DateOrTimeToNumber(localStorage.getItem("date"));
        //get time from localStorage
        let time = localStorage.getItem("time");
        //get castleId from localStorage
        let castleId = localStorage.getItem("castleId");

        //fetch API to get all available ticket types and price
        CastleService.getCastleTicketPriceById(date, time, castleId)
            .then((res) => {
                if(res.data.code === 200){
                    console.log(res)
                    setTicketDetails(res.data.data);
                    setButtonState(false) //data-received: disable = false
                }else{
                    alert(res.data.code + ": " + res.data.message);
                }
            })
            .catch(error => {
                alert("Failed to get tickets from server: " + error);
            })
    },[])

    /**
     * @author Pete To
     * @version 1.0
     * This variable will display the ticket details accordingly by returning JSX elements
     * @type {ticketDetails[]}
     */
    const displayTicketDetails = ticketDetails.map(element =>
        <div key={element.castleTicketId} className="ticket-row">
            <div className="left">
                <h3>{element.type}</h3>
            </div>
            <div className="right">
                <h3><i className="fa-solid fa-sterling-sign"/> {element.price}</h3>
                <div className="ticket-quantity">
                    <button onClick={handleMinus} disabled={buttonState}>-</button>
                    <h3>{chosenQuantity}</h3>
                    <button onClick={handleAdd} disabled={buttonState}>+</button>
                </div>
            </div>
        </div>
    )

    /**
     * @author Pete To
     * @version 1.0
     * This function will be triggered when the add button is clicked
     * the ticket quantity will be added one
     */
    function handleAdd(){
        setChosenQuantity(prevState => prevState + 1);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will be triggered when the add button is clicked
     * the ticket quantity will minus one
     */
    function handleMinus(){
        setChosenQuantity(prevState => prevState - 1);
    }

    /**
     * @author Pete To
     * @version 1.0
     * This effect will render whenever the chosenQuantity is changed to restrict user's selection of ticket quantity
     */
    useEffect(() => {
        if(chosenQuantity > 5){
            setChosenQuantity(5)
        }
        if(chosenQuantity < 0){
            setChosenQuantity(0)
        }
    },[chosenQuantity])

    /**
     * @author Pete To
     * @version 1.0
     * This function will store the user's selection of tickets as a JSON object at local storage
     */
    function handleSubmit(){
        let array = ticketDetails;

        for(let i = 0; i < array.length; i++){
            array[i].quantity = chosenQuantity;
        }

        localStorage.setItem("castleTickets", JSON.stringify(array));
        localStorage.setItem("totalTicketQuantity", chosenQuantity.toString());

        if(chosenQuantity === 0 || chosenQuantity > 5){
            setErrorMessage("ticket quantity should be between 1 and 5")
        }else{
            history.push("/trip-transportation-inbound")
        }
    }

    return(
        <section className="trip-section">
            <h1 className="heading">Castle Tickets (Max: 5)</h1>

                {errorMessage && <h4 className="warnings">{errorMessage}</h4>}
                <div className="ticket-row">
                    <h3 className="header">Ticket Type</h3>
                    <div className="right">
                        <h3 className="header">Price</h3>
                        <h3 className="header">Quantity</h3>
                    </div>
                </div>

                {displayTicketDetails}

                <div className="ticket-btn">
                    <Link to="/trip-date-time" className="btn cancel-btn"><i className="fa-solid fa-circle-arrow-left"/> previous</Link>
                    <button onClick={function(){handleSubmit()}} className="btn">next <i className="fa-solid fa-circle-arrow-right"/></button>
                </div>

        </section>
    )
}