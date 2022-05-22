import React, {useEffect, useState} from "react";
import {Link, useHistory} from "react-router-dom"
import DateFunction from "../../CustomFunction/DateFunction";
import TripService from "../../service/TripService";

export default function TripDateAndTime(){

    const history = useHistory(); //push to other components

    //get today's date, format: yyyy-mm-dd
    let today = DateFunction.getToday();

    /**
     * @author Pete To
     * @version 1.0
     * Check if user has an unfinished drafted date/time or not,
     * yes: get it and display it when user return to this page
     * no: 1.set date to "today" 2.set time to null
     */
    const[form, setForm] = useState({
        date: localStorage.getItem("date") ? localStorage.getItem("date") : today,
        time: localStorage.getItem("time") ? DateFunction.convertTime(localStorage.getItem("time")) : "7:00"
    });

    const[warnings, setWarnings] = useState(""); //hold error message
    const[max, setMax] = useState(); //hold the max date user can choose from
    const [timeSlots, setTimeSlots] = useState([]); //hold list of timeSlots from API after user select the date

    /**
     * @author Pete To
     * @version 1.0
     * Check if the user has chosen both date and time
     * 1. yes: save user's selection to localStorage for later retrieval and redirect user to choose castle tickets
     * 2. no: display Warning message to prompt user to select both parameters
     */
    function handleSubmit(event){
        event.preventDefault();
        localStorage.setItem("date", form.date);
        localStorage.setItem("time", DateFunction.DateOrTimeToNumber(form.time));
        if(localStorage.getItem("time") > 1401 || localStorage.getItem("time") < 700 || !localStorage.getItem("time")){
            setWarnings("The start time should be between 7:00 and 14:00");
        }else{
            setWarnings("")
            history.push("/trip-castle-ticket")
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * This effect will render once
     * 1. Try to fetch range of dates that can be selected from API
     * - min: today, max: 15th date from today
     * 2. Catch any error detected to 1.alert to user 2.redirect user to previous section
     */
    useEffect(() => {
        TripService.getCastleDate(localStorage.getItem("castleId"))
            .then((res) => {
                if(res.data.code === 200){
                    setMax(res.data.data[14]); //max = the 15th date from today
                }else{
                    alert("no available dates for this castle, please select another one"); //alert user
                    history.push('/trip-castle'); //redirect user to choose castle
                }
            })
            .catch(error => {
                alert("Server Error, reason: " + error);
                history.push('/trip-castle');
            })
    },[])

    /**
     * @author Pete To
     * @version 1.0
     * This effect will render when user change the selection of date
     * 1. Try to fetch lists of available timeslots from API by passing 2 parameters
     * @param castleId
     * @param date
     * 2. Catch any error
     * - no available timeslots: alert to user to prompt them to select another date
     * - server error: alert user of server failure
     */
    useEffect(() => {
        TripService.getCastleTime(localStorage.getItem("castleId"), DateFunction.DateOrTimeToNumber(form.date))
            .then((res) => {
                if(res.data.code === 200){
                    setTimeSlots(res.data.data);
                }else{
                    alert("No available timeslots for this date, please choose another date");
                }
            })
            .catch (error => {
                alert("Server failure: " + error);
            })
    },[form.date])


    //map time data from api and display available timeslots
    const getTimeSlots = timeSlots.map(element =>
        <option value={element}>{element}</option>
    );

    /**
     * @author Pete To
     * @version 1.0
     * Whenever user change date/time, this function will be triggered
     * @param event(formData)
     * formData includes: 1. date 2. time
     */
    function handleChange(event){
        const{name, value} = event.target;
        setForm(prevForm => {
            return{
                ...prevForm,
                [name]: value
            }
        });
    }

    /**
     * @author Pete To
     * @version 1.0
     * TripDateAndTIme Component returns two main parts
     * 1. Date Selection (range: 15 days from today)
     * 2. Time Selection (range: according to selected date)
     * The two data will be stored to localStorage when user finished selection
     */
    return(
        <section className="trip-section">
            <h1 className="heading">Trip Date and Time</h1>

            <div className="row">

                <form>

                    <div className="inputBox">
                        <h3>Choose your trip date</h3>
                        <input
                            type="date"
                            min={today}
                            max={DateFunction.convertDate(max)}
                            name="date"
                            value={form.date}
                            onChange={handleChange}
                            required
                        />

                    </div>

                    <div className="inputBox">
                        <h3>Choose your start time</h3>
                        <input
                            name="time"
                            type="time"
                            onChange={handleChange}
                            value={form.time}
                            min="7:00"
                            max="1400"
                            required
                        >
                        </input>
                    </div>

                    {warnings && <h4 className="warnings">{warnings}</h4>}

                    <Link to="/trip-castle" className="btn cancel-btn"><i className="fa-solid fa-circle-arrow-left"/> previous</Link>
                    <button onClick={handleSubmit} className="btn">next <i className="fa-solid fa-circle-arrow-right"/></button>
                </form>

            </div>
        </section>
    )
}