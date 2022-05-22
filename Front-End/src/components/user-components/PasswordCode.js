import React, {useState} from "react";
import UserService from "../../service/UserService";
import {useHistory} from "react-router-dom";

export default function PasswordCode(){

    const history = useHistory();//for pushing user to login page after resetting password
    //hold user's information
    const[form, setForm] = useState({
        code: "",
        email: "",
        newPassword: "",
        repeatPassword: ""
    })
    //hold errorMessages sent while fetching data
    const[errorMessage, setErrorMessage] = useState("")

    /**
     * @author Pete To
     * @version 1.0
     * This function will call fetching function from userService to post user's input to reset password
     * @param event
     */
    function handleSubmit(event){
        event.preventDefault();
        UserService.changePasswordByEmailCode(form)
            .then((res) => {
                if(res.data.code === 200){
                    alert("you have reset your password successfully!")
                    history.push('/login')
                }else{
                    setErrorMessage("Password Reset unsuccessful, reason: " + res.data.message)
                }
            })
            .catch(error => {
                console.log(error)})
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will be called whenever user tries to change the input field value
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

    return(
        <section className="register">
            <h1 className="heading">Reset your password here</h1>

            <form>
                <h3>Reset Code (sent to your email)</h3>
                <input
                    type="text"
                    placeholder='reset code'
                    onChange={handleChange}
                    name="code"
                    value={form.code}
                    required
                />

                <h3>Email used for registration</h3>
                <input
                    type="email"
                    placeholder='email'
                    onChange={handleChange}
                    name="email"
                    value={form.email}
                    required
                />

                <h3>New Password</h3>
                <input
                    type="text"
                    placeholder='new password'
                    onChange={handleChange}
                    name="newPassword"
                    value={form.newPassword}
                    required
                />

                <h3>Confirm new password</h3>
                <input
                    type="text"
                    placeholder='confirm new password'
                    onChange={handleChange}
                    name="repeatPassword"
                    value={form.repeatPassword}
                    required
                />
                {errorMessage && <h4 className="warnings">{errorMessage}</h4>}
                <button onClick={handleSubmit} className='btn'>reset password</button>

            </form>
        </section>
    )
}