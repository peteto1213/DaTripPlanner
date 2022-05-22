import React, {useState} from "react";
import {Link, useHistory} from "react-router-dom"

export default function TripName(){
    /**
     * @author Pete To
     * @version 1.0
     * Check if user has an unfinished drafted name or not,
     * yes: get it and display it when user return to this page
     * no: set it to null
     */
    const[tripName, setTripName] = useState(localStorage.getItem("tripName"));

    //useHistory hook to push to other components
    const history = useHistory();

    //Whenever a user input, get the input and save in local state "tripName"
    function handleChange(event){
        setTripName(event.target.value);
    }

    /**
     * @author Pete To
     * @version 1.0
     * @param event(form)
     * when the next button is clicked,
     * 1. direct user to choose castle
     * 2. store the "tripName" state to localStorage for later retrieval
     */
    function handleSubmit(event){
        event.preventDefault();
        localStorage.setItem("tripName", tripName);
        history.push("/trip-castle");
    }


    /**
     * @author Pete To
     * @version 1.0
     * TripName Component
     * 1. provide an input field to get trip name
     */
    return(
        <section className="trip-section">
            <h1 className="heading">Let's create a name for your trip!</h1>

            <div className="row">

                <form onSubmit={handleSubmit}>
                    <div className="inputBox">
                        <h3>Enter the name of your trip</h3>
                        <input
                            type="text"
                            name="name"
                            placeholder="enter trip name here"
                            onChange={handleChange}
                            required
                            value={tripName}
                        />
                    </div>
                    <Link to="/" className="btn cancel-btn"><i className="fa-solid fa-circle-xmark"/> cancel</Link>
                    <button className="btn">next <i className="fa-solid fa-circle-arrow-right"/></button>
                </form>

            </div>
        </section>
    )
}