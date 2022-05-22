import React, { useState } from 'react'
import UserService from '../../service/UserService';
import {useHistory} from "react-router-dom";


export default function Register() {

    const[warnings, setWarnings] = useState("");
    const[errorMessage, setErrorMessage] = useState("");
    const history = useHistory();

    const userEmail = localStorage.getItem("email");

    /**
     * @author Pete To
     * @version 1.0
     * This React Hook will hold the form content as an object to be passed for REGISTER USER INFO
     */
    const[registerForm, setRegisterForm] = useState({
        firstName:"",
        lastName:"",
        email:"",
        password:"",
        repeatPassword:""
    })

    /**
     * @author Pete To
     * @version 1.0
     * This function will be triggered when the user press the register button and do the followings:
     * 1. check if the email for registration is in valid format
     * - if invalid: display a warning message
     * - if valid: fetch with the API and pass the register form for registration
     * 2. on successful registration, the user will receive an alert message of "successful"
     * 3. on unsuccessful registration, the user will receive an alert message of "failed"
     * @param event
     */
    function handleRegister(event){
        event.preventDefault();
        if(!(registerForm.email.includes("ac.uk"))){
            setWarnings("you must use your university email to proceed");
        }
        else{
            setWarnings("")

            UserService.registerUser(registerForm)
                .then((res) => {
                    console.log(registerForm)
                    if(res.data.code === 200){
                        setWarnings("");
                        setErrorMessage("");
                        alert("you have successfully registered!");
                    }else{
                        console.log(res.data.message);
                        setErrorMessage("Registration failed, reason:  " + res.data.message);
                    }
                })
                .catch(error => {
                    console.log(error)
                })
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * The React state hook will hold a form to be passed for EDIT USER INFO
     */
    const[updateForm, setUpdateForm] = useState({
        firstName: "",
        lastName: ""
    })

    /**
     * @author Pete To
     * @version 1.0
     * The handleUpdate function is called when the user is logged in and try to change his info
     * if both firstName and lastName is not null, it will when fetch with the API to pass the updateForm to the API
     * if the update action is successful, the user will receive an alert of "successful update"
     * if not, a warning alert of "failed" will be shown
     * @param event
     */
    function handleUpdate(event){
        event.preventDefault();
        console.log(updateForm)
        if(updateForm.firstName && updateForm.lastName){
            setErrorMessage("");
            //Fetch API editUserInfo here with try-catch block
            UserService.editUserInfo(updateForm)
                .then((res) => {
                    if(res.data.code === 200){
                        setErrorMessage("")
                        alert("update user information successfully!");
                        history.push('/profile')
                    }else{
                        setErrorMessage("Update failed, reason: " + res.data.message);
                    }
                }).catch(error => {
                setErrorMessage("Update failed, reason: " + error);
            })

        }else{
            setErrorMessage("Please complete all the requited fields to proceed")
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will be called whenever the user tries to change the input field
     * this function will update both registerForm and updateForm hook at local state
     * @param event
     */
    function handleChange(event){
        const{name, value} = event.target;
        setRegisterForm(prevLoginForm => {
            return{
                ...prevLoginForm,
                [name]: value
            }
        })
        setUpdateForm(prevForm => {
            return{
                ...prevForm,
                [name]: value
            }
        })
    }

    /**
     * @author Pete To
     * @version 1.0
     * the Register component will display two types of content based on the user status
     * 1. if the user is logged in, an update form with field 1.firstName 2.lastName will show, target API: update
     * 2. if user is not logged in, a registration form with field
     * - firstName, lastName, email, password, confirmPassword will show, targetAPI: register
     */
    return (
        <section className='register'>
            {userEmail ?
                <h1 className='heading'>
                    <i className="fa-solid fa-address-card"/>
                    Update Profile
                </h1>
            :
                <h1 className='heading'>
                    <i className="fa-solid fa-address-card"/>
                    User Registration
                </h1>
            }

            <form>

                <h3>first name</h3>
                <input
                    type="text"
                    placeholder='please enter you first name'
                    onChange={handleChange}
                    name="firstName"
                    value={registerForm.firstName}
                />

                <h3>last name</h3>
                <input
                    type="text"
                    placeholder='please enter you last name'
                    onChange={handleChange}
                    name="lastName"
                    value={registerForm.lastName}
                />

                {!userEmail &&
                    <>
                        <h3>email <span>(for login)</span>{warnings && <p className="warnings">{warnings}</p>}</h3>
                        <input
                            type="email"
                            placeholder='please enter you school email'
                            onChange={handleChange}
                            name="email"
                            value={registerForm.email}
                            required
                        />

                        <h3>password</h3>
                        <input
                            type="password"
                            placeholder='please enter you password'
                            onChange={handleChange}
                            name="password"
                            value={registerForm.password}
                            required
                        />

                        <h3>confirm password</h3>
                        <input
                            type="password"
                            placeholder='enter your password again'
                            onChange={handleChange}
                            name="repeatPassword"
                            value={registerForm.repeatPassword}
                            required
                        />
                    </>
                }

                {errorMessage && <h4 className="warnings">{errorMessage}</h4>}

                {userEmail ?
                    <button onClick={handleUpdate} className='btn'>update</button>
                    :
                    <button onClick={handleRegister} className='btn'>register</button>
                }
            </form>
        </section>
    )
}
