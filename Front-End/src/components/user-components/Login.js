import React, {useState} from "react";
import {Link} from "react-router-dom"

export default function Login({LoginFunction, error}){

    /**
     * @author Pete To
     * @version 1.0
     * This React state hook will hold the login details of the user input
     * it is for later retrieval when calling the login function
     */
    const[loginForm, setLoginForm] = useState({
        email:"",
        password:"",
        rememberMe: false
    })

    /**
     * @author Pete To
     * @version 1.0
     * This function will be called whenever the user change the login form
     * all the user's input will be tracked and saved to the local state "LoginForm"
     * @param event
     */
    function handleChange(event){
        const {value, name, type, checked} = event.target
        setLoginForm(prevLoginForm => {
            return{
                ...prevLoginForm,
                [name]: type === "checkbox" ?  checked : value
            }
        })
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will be called when user clicks the login button
     * it will then trigger the login function passed as props from the main component to fetch with the API
     * for authentication of login details - state hook (loginForm)
     * @param event
     */
    function handleSubmit(event){
        event.preventDefault()
        LoginFunction(loginForm)
    }

    /**
     * @author Pete To
     * @version 1.0
     * The Login component will display a form component for the user to input their information
     */
    return(
        <section className="login">
            <div id="login" className="login-form-container">

                <Link
                    to="/home"
                    className="fas fa-times login_icon form-close"
                >
                </Link>

                <form onSubmit={handleSubmit}>
                    <h3>login</h3>
                    <input
                        type="email"
                        className="box"
                        placeholder="enter your email"
                        name="email"
                        onChange={handleChange}
                        value={loginForm.email}
                        required
                    />
                    <input
                        type="password"
                        className="box"
                        placeholder="enter your password"
                        name="password"
                        onChange={handleChange}
                        value={loginForm.password}
                        required
                    />
                    <button className="btn">Login</button>
                    {error && <h5 className="warnings">{error}</h5>}
                    <input
                        type="checkbox"
                        id="remember"
                        name="rememberMe"
                        onChange={handleChange}
                        checked={loginForm.rememberMe}
                    />
                    <label htmlFor="remember">remember me</label>

                    <p>forget password?<Link to="/forget-password" >click here</Link></p>
                    <p>dont' have an account yet?<Link to="/register" >register here</Link></p>
                </form>
            </div>
        </section>
    )
}