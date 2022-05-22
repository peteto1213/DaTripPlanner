import React, {useEffect, useRef, useState} from "react";
import Chip from '../../photo/chip.png';
import Visa from '../../photo/visa.png';
import Master from '../../photo/master.png';
import {useHistory} from "react-router-dom";
import OrderService from "../../service/OrderService";

export default function Checkout(){

    /**
     * @author Pete To
     * @version 1.0
     * This React state hook will hold the user's input of credit card information
     * it is for later retrieval when fetching with the API when the pay button is clicked
     */
    const[form, setForm] = useState({
        cardNumber: "################",
        holderName: "FULL NAME IN CAPITAL",
        month: "05",
        year: "22",
        cvv: ""
    })

    //state hook to hold the plan payment total stored in localStorage
    const[total, setTotal] = useState(localStorage.getItem("planTotal"));
    //logo will be changed when different credit card number is input, default: Visa
    const [logo, setLogo] = useState(Visa);
    const history = useHistory();

    //5 useRef hooks to track the user's input of credit card information
    const cardNumberRef = useRef();
    const holderNameRef = useRef();
    const monthRef = useRef();
    const yearRef = useRef();
    const cvvRef = useRef();

    /**
     * @author Pete To
     * @version 1.0
     * This effect will be triggered everytime there is a value change in the form state
     * corresponding value held in the 5 useRef hooks will be displayed in a credit card animation
     */
    useEffect(() => {
        cardNumberRef.current.innerText = form.cardNumber;
        holderNameRef.current.innerText = form.holderName;
        monthRef.current.innerText = form.month;
        yearRef.current.innerText = form.year;
        cvvRef.current.innerText = form.cvv;
        setLogo(form.cardNumber.charAt(0) === "4" ? Visa : Master);
        setTotal(localStorage.getItem("planTotal"))
    },[form])

    /**
     * @author Pete To
     * @version 1.0
     * This function will be triggered whenever there is a change in the input field of the credit card form
     * @param event
     */
    function handleChange(event){
        const {name, value} = event.target;
        setForm(prevForm => {
            return{
                ...prevForm,
                [name]: value
            }
        })
    }

    /**
     * @author Pete To
     * @version 1.0
     * @param event
     * This function will send payment details to backend
     * For Generating a new order
     */
    function handleSubmit(event){
        //Needs verification here
        event.preventDefault();
        console.log(form)
        //save payment details as local object
        let paymentDetails = {
            cardNumber: form.cardNumber,
            expireDate: `${form.month}/${form.year}`,
            ownerName: form.holderName.toUpperCase(),
            planId: localStorage.getItem("currentPlanId"),
            securityCode: form.cvv,
            transactionAmount: total
        }
        console.log(paymentDetails)

        //pass this object to the API - createOrder
        OrderService.createOrder(paymentDetails)
            .then((res) => {
                if(res.data.code === 200){
                    alert("order created successfully")
                    history.push("/profile")
                }else{
                    alert(res.data.message)
                }
            })
            .catch(error => {
                alert(error)
            })

    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will be triggered when the user press the cancel button
     * the user will be pushed to the view plan section
     */
    function handleCancel(){
        history.push("/created-trips")
    }

    /**
     * @author Pete To
     * @version 1.0
     * These 2 useRef hooks will track the display of credit card animation
     * 1. Hover on "cvv" input field: change to card back (changeBack function)
     * 2. Other fields: change to card front (changeFront function)
     */
    const frontRef = useRef();
    const backRef = useRef();

    /**
     * @author Pete To
     * @version 1.0
     * This function refers to the useRef hooks action
     */
    function changeBack(){
        frontRef.current.style.transform = "perspective(1000px) rotateY(-180deg)";
        backRef.current.style.transform = "perspective(1000px) rotateY(0deg)";
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function refers to the useRef hooks action
     */
    function changeFront(){
        frontRef.current.style.transform = "perspective(1000px) rotateY(0deg)";
        backRef.current.style.transform = "perspective(1000px) rotateY(180deg)";
    }

    /**
     * @author Pete To
     * @version 1.0
     * The checkOut component will display a credit card form for the user to pay for their plan
     */
    return(
        <section className="checkout">

            <h1 className="heading">Checkout</h1>

            <div className="card-container">

                <div className="front" ref={frontRef}>
                    <div className="image">
                        <img src={Chip} alt="chip"/>
                        <img src={logo} alt="card_logo"/>
                    </div>
                    <div
                        className="card-number-box"
                        ref={cardNumberRef}
                    >
                        ################
                    </div>
                    <div className="flexbox">
                        <div className="box">
                            <span>card holder</span>
                            <div
                                className="card-holder-name"
                                ref={holderNameRef}
                            >full name</div>
                        </div>
                        <div className="box">
                            <span>expires</span>
                            <div className="expiration">
                                <span className="exp-month" ref={monthRef}>mm</span>
                                <span>/</span>
                                <span className="exp-year" ref={yearRef}>yy</span>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="back" ref={backRef}>
                    <div className="stripe">_</div>
                    <div className="box">
                        <span>cvv</span>
                        <div className="cvv-box" ref={cvvRef}>***</div>
                        <img src={logo} alt="cardLogo"/>
                    </div>
                </div>

            </div>

            <form onSubmit={handleSubmit}>
                <div className="inputBox">
                    <span>card number</span>
                    <input
                        type="text"
                        maxLength="16"
                        className="card-number-input"
                        name="cardNumber"
                        required
                        onChange={handleChange}
                    />
                </div>

                <div className="inputBox">
                    <span>card holder name</span>
                    <input
                        type="text"
                        className="card-holder-input"
                        name="holderName"
                        required
                        onChange={handleChange}
                    />
                </div>

                <div className="flexbox">
                    <div className="inputBox">
                        <span>expiry month</span>
                        <select
                            id="" className="month-input"
                            name="month"
                            required
                            onChange={handleChange}
                        >
                            <option value="month" disabled>month</option>
                            <option value="01" >01</option>
                            <option value="02" >02</option>
                            <option value="03" >03</option>
                            <option value="04" >04</option>
                            <option value="05" >05</option>
                            <option value="06" >06</option>
                            <option value="07" >07</option>
                            <option value="08" >08</option>
                            <option value="09" >09</option>
                            <option value="10" >10</option>
                            <option value="11" >11</option>
                            <option value="12" >12</option>
                        </select>
                    </div>

                    <div className="inputBox">
                        <span>expiry year</span>
                        <select
                            id="" className="year-input"
                            name="year"
                            required
                            onChange={handleChange}
                        >
                            <option value="year" disabled>year</option>
                            <option value="22" >2022</option>
                            <option value="23" >2023</option>
                            <option value="24" >2024</option>
                            <option value="25" >2025</option>
                            <option value="26" >2026</option>
                            <option value="27" >2027</option>
                            <option value="28" >2028</option>
                            <option value="29" >2029</option>
                            <option value="30" >2030</option>
                            <option value="31" >2031</option>
                            <option value="32" >2032</option>
                            <option value="33" >2033</option>
                            <option value="34" >2034</option>
                            <option value="35" >2035</option>
                        </select>
                    </div>

                    <div className="inputBox">
                        <span>cvv</span>
                        <input
                            type="text"
                            maxLength="3"
                            className="cvv-input"
                            name="cvv"
                            required
                            onChange={handleChange}
                            onMouseEnter={changeBack}
                            onMouseLeave={changeFront}
                        />
                    </div>
                </div>

                <h3 className="total">Payment total: <span>{Math.round(total*100)/100}</span> GBP</h3>
                <button onClick={handleSubmit} className="btn submit-btn">Pay</button>
                <button onClick={handleCancel} className="btn cancel-btn">cancel</button>

            </form>


        </section>
    )
}