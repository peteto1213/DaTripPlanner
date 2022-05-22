import React, {useState} from "react";
import DateFunction from "../CustomFunction/DateFunction";

export default function BusTimetable(){

    let today = DateFunction.getToday();

    const[form, setForm] = useState({
        startPoint: "",
        castle: "",
        date: ""
    })

    function handleChange(event){
        const {name, value} = event.target
        setForm(prevForm => {
            return{
                ...prevForm,
                [name]: value
            }
        })
    }

    //take things from api
    async function handleSubmit(event){
        event.preventDefault()
        console.log(form)
    }

    return(
        <section className="bus-timetable">
            <h1 className="heading"><i className="fa-solid fa-bus"/> Bus Timetable</h1>

            <div className="row">

                <form onSubmit={handleSubmit}>
                    <select
                        onChange={handleChange}
                        name="startPoint"
                        value={form.startPoint}
                    >
                        <option value="">===Choose a start point===</option>
                        <option value="Haymarket">Haymarket</option>
                        <option value="Eldon Square">Eldon Square</option>
                    </select>

                    <select
                        onChange={handleChange}
                        name="castle"
                        value={form.castle}
                    >
                        <option value="">===Choose a castle===</option>
                        <option value="Alnwick Castle">Alnwick Castle</option>
                        <option value="Auckland Castle">Auckland Castle</option>
                        <option value="Bamburgh Castle">Bamburgh Castle</option>
                        <option value="Barnard Castle">Barnard Castle</option>
                    </select>

                    <input
                        type="date"
                        name="date"
                        min={today}
                        value={form.date}
                        onChange={handleChange}
                    />

                    <button className="btn">check now</button>
                </form>

                {/* Change corresponding to the form onsubmit*/}
                <div className="timetable">

                    <div className="timetable-header">
                        <h1>From: <span>{form.startPoint}</span> To: <span>{form.castle}</span></h1>
                        <h1>Date: <span>{form.date}</span></h1>
                    </div>

                    <div className="timetable-details">

                        <div className="timetable-details-row">
                            <h3>Operator: <span>xxx</span></h3>
                            <h3>Service Number: <span>X12</span></h3>
                            <div className="timetable-block">
                                <h3>11:30</h3>
                                <div className="arrow">
                                    <i className="fa-solid fa-arrow-right-long icon-arrow"/>
                                    <h4>1hr 35mins</h4>
                                </div>
                                <h3>13:15</h3>
                            </div>
                        </div>

                        <div className="timetable-details-row">
                            <h3>Operator: <span>xxx</span></h3>
                            <h3>Service Number: <span>X12</span></h3>
                            <div className="timetable-block">
                                <h3>11:30</h3>
                                <div className="arrow">
                                    <i className="fa-solid fa-arrow-right-long icon-arrow"/>
                                    <h4>1hr 35mins</h4>
                                </div>
                                <h3>13:15</h3>
                            </div>
                        </div>

                    </div>

                </div>

            </div>
        </section>
    )
}