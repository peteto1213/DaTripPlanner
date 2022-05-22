import React, {useState} from "react";
import UserService from "../../service/UserService";
import {Link, useHistory} from "react-router-dom";

export default function ForgetPassword(){

    const history = useHistory();

    /**
     * @author Pete To
     * @version 1.0
     * This React state hook will hold the user's email used for registration
     * it is for later retrieval when the handleSubmit function is called
     */
    const[form, setForm] = useState({
        email: ""
    });

    /**
     * @author Pete To
     * @version 1.0
     * This function will be called whenever the user tries to change the value of the input field
     * @param event
     */
    function handleChange(event){
        const{name, value} = event.target;
        setForm(prevForm => {
            return{
                ...prevForm,
                [name]: value
            }
        })
    }

    //holding the change of state of error message to be displayed
    const[errorMessage, setErrorMessage] = useState("");

    /**
     * @author Pete To
     * @version 1.0
     * This function will be called when the user press the send button, and will do the followings:
     * 1. check if the email is a correct formation
     * - if not, display a warning message "invalid format"
     * 2. fetch with the API to authenticate the email
     * - if not registered: display a message of "not found"
     * 3. If there is any error caught, display it in the console log
     * @param event
     */
    function handleSubmit(event){
        event.preventDefault();
        if(!(form.email.includes("ac.uk"))){
            setErrorMessage("login email should belong to the domain @xxx.ac.uk")
        }else{
            //fetch api to request for sending email for changing password
            try{
                UserService.sendForgetPasswordCode(form)
                    .then((res) => {
                        if(res.data.code === 200){
                            console.log(res.data)
                            setErrorMessage("")
                            alert("email sent to your registered email address for password changing")
                            history.push("/password-code")
                        }else{
                            console.log(res.data.message)
                            setErrorMessage("User email not found")
                        }
                    })
            }
            catch(error){
                console.log(error);
                setErrorMessage(error);
            }
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * The ForgetPassword component will display a single input field for the user to input his registered email
     * for retrieval of reset password link
     */
    return(
        <section className="register">
            <h1 className="heading"><i className="fa-solid fa-lock button-icon"/> Forget Password?</h1>
            <form>
                <h3>registered email</h3>
                <input
                    type="email"
                    placeholder='please enter your email used for registration'
                    onChange={handleChange}
                    name="email"
                    value={form.email}
                    required
                />
                {errorMessage && <h4 className="warnings">{errorMessage}</h4>}
                <button onClick={handleSubmit} className='btn'>get reset code</button>
                <Link classname="code-link" to="/password-code">reset your password here</Link>
            </form>
        </section>
    )
}