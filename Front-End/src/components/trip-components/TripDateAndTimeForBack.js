import React, {useEffect, useState} from "react";
import {Link, useHistory} from "react-router-dom"
import TripService from "../../service/TripService";
import DateFunction from "../../CustomFunction/DateFunction";

export default function TripDateAndTimeForBack(){

    const history = useHistory()

    const [returnTime, setReturnTime] = React.useState("");

    /**
     * @author Pete To
     * @version 1.0
     * Once render this page:
     * 1. setReturnTime if there is any previous local storage
     */
    useEffect(() => {
        //1. setReturnTime
        if(localStorage.getItem("returnTime")){
            setReturnTime(DateFunction.convertTime(localStorage.getItem("returnTime")));
        }

    },[])

    const[warnings, setWarnings] = useState("")

    /**
     * @author Pete To
     * @version 1.0
     * This function will push user to select outbound transportation if the return time is valid
     * warnings will be displayed if the return time is not valid
     */
    function handleSubmit(event){
        event.preventDefault();
        localStorage.setItem("returnTime", DateFunction.DateOrTimeToNumber(returnTime))

        if(localStorage.getItem("returnTime") < localStorage.getItem("time") || !localStorage.getItem("returnTime")){
            setWarnings("Please select a valid return time")
        }
        else{
            setWarnings("")
            history.push("/trip-transport-outbound")
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * this function will change the value of the return time state whenever the user change the input field
     * @param event
     */
    function handleChange(event){
        const{ value } = event.target;
        setReturnTime(value);
    }

    return(
        <section className="trip-section">

            <h1 className="heading">Choose your return time</h1>
            <div className="row">
                <form>
                    <div className="inputBox">
                        <h3>Trip Start Date (Chosen)</h3>
                        <input
                            type="date"
                            name="date"
                            value={localStorage.getItem("date")}
                            disabled
                        />
                    </div>

                    <div className="inputBox">
                        <h3>Trip Start Time (Chosen)</h3>
                        <input
                            type="startTime"
                            name="startTime"
                            value={DateFunction.convertTime(localStorage.getItem("time"))}
                            disabled
                        />
                    </div>

                    <div className="inputBox">
                        <h3>Choose your return time</h3>
                        <input
                            name="time"
                            type="time"
                            onChange={handleChange}
                            value={returnTime}
                            required
                        >
                        </input>
                    </div>

                    {warnings && <h4 className="warnings">{warnings}</h4>}

                    <Link to="/trip-transportation-inbound" className="btn cancel-btn"><i className="fa-solid fa-circle-arrow-left"/> previous</Link>
                    <button onClick={handleSubmit} className="btn">next <i className="fa-solid fa-circle-arrow-right"/></button>
                </form>
            </div>
        </section>
    )
}