import React from "react";
import UserService from "../service/UserService";

export default function Contact(){

    /**
     * @author Pete To
     * @version 1.0
     * This React state will hold the user's input of the contact form as an object
     * this is later to be passed to the API
     */
    const[formData, setFormData] = React.useState({
        name:"",
        email:"",
        contactNumber:"",
        subject:"",
        message:""
    })

    /**
     * @author Pete To
     * @version 1.0
     * This function will be called whenever the user tries to change the contact form input value
     * it will track ths user's input and stored it in the local state "formData"
     * @param event
     */
    function handleChange(event){
        const {name, value} = event.target
        setFormData(prevFormData => {
            return{
                ...prevFormData,
                [name]: value
            }
        })
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will be called when the user submit the form
     * 1. successful delivery of contact form: user will receive an alert message of "successful"
     * 2. non-successful delivery of contact form: user will receive an alert message of "unsuccessful"
     * @param event
     */
    function handleSubmit(event){
        event.preventDefault()
        //Send to email
        UserService.sendContactForm(formData);
    }

    /**
     * @author Pete To
     * @version 1.0
     * The Contact component will display a form for the user to input their contact content
     */
    return(
        <>
            <section className="contact" id="contact">
                <h1 className="heading">Contact us</h1>
                <div className="row">

                    <div className="image">
                        <img src="https://www.nixonhire.co.uk/static/cms/news/images/thumb/here-to-help-2-alt-2.jpg"/>
                    </div>

                    <form onSubmit={handleSubmit}>
                        <div className="inputBox">
                            <input 
                                type="text" 
                                placeholder="name" 
                                name="name"
                                value={formData.name}
                                onChange= {handleChange}
                                required
                            />

                            <input 
                                type="email" 
                                placeholder="email" 
                                name="email"
                                value={formData.email}
                                onChange= {handleChange}
                                required
                            />
                        </div>

                        <div className="inputBox">
                            <input 
                                type="tel" 
                                placeholder="UK contact no." 
                                name="contactNumber"
                                value={formData.contactNumber}
                                onChange= {handleChange}
                                required
                                pattern="[0-9]{10}"
                            />
                            
                            <input 
                                type="text" 
                                placeholder="subject" 
                                name="subject"
                                value={formData.subject}
                                onChange= {handleChange}
                                required
                            />
                        </div>

                        <textarea 
                            placeholder="message" 
                            name="message"
                            cols="30" 
                            rows="10"
                            value={formData.message}
                            onChange ={handleChange}
                            required
                        >
                        </textarea>

                        <button className="btn">Send Message</button>

                    </form>
                </div>
            </section>
        </>
    )
}