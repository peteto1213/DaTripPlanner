import React, {useState} from "react";
import UserService from "../../service/UserService";

export default function ChangePassword(){

    /**
     * @author: Pete To
     * @version 1.0
     * This React state hook will hold the form value for changing password
     * it is for later retrieval when the api is called
     */
    const[form, setForm] = useState({
        oldPassword: "",
        newPassword: "",
        repeatNewPassword: ""
    });

    //hook to hold the error message to display
    const[errorMessage, setErrorMessage] = useState("");

    /**
     * @author: Pete To
     * @version 1.0
     * This function will be called whenever the user tries to change the value of the form input field
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

    /**
     * @author: Pete To
     * @version 1.0
     * This function will be called when the user tries to submit a request to change password
     * 1. check if the input fields are null or not
     * - if the input fields are not null, fetch with the API for authentication of old password
     * - if any of them is null, display a message of "please complete all the fields"
     * @param event
     */
    function handleSubmit(event){
        event.preventDefault();
        if(form.newPassword === form.repeatNewPassword && form.oldPassword && form.newPassword && form.repeatNewPassword){
            setErrorMessage("");
            //fetch api to change password
            try{
                UserService.changePasswordByOldPassword(form)
                    .then((res) => {
                        if(res.data.code === 200){
                            setErrorMessage("");
                            alert("password changed successfully")
                        }else{
                            setErrorMessage("old password not matching")
                            alert(res.data.message)
                        }
                    })
            }
            catch(error){
                console.log(error);
                setErrorMessage(error);
            }

        }else if(form.newPassword !== form.repeatNewPassword){
            setErrorMessage("The two times you enter the new password should be the same");
        }else{
            setErrorMessage("Please complete all the required fields");
        }
    }

    /**
     * @author: Pete To
     * @version 1.0
     * The ChangePassword component will display a form for the user to change their password
     */
    return(
        <section className="register">
            <h1 className="heading"><i className="fa-solid fa-lock button-icon"/> Change Password</h1>
            <form>
                <h3>old password</h3>
                <input
                    type="password"
                    placeholder='please enter your previous password'
                    onChange={handleChange}
                    name="oldPassword"
                    value={form.oldPassword}
                    required
                />
                <h3>new password</h3>
                <input
                    type="password"
                    placeholder='please enter your new password'
                    onChange={handleChange}
                    name="newPassword"
                    value={form.newPassword}
                    required
                />
                <h3>confirm new password</h3>
                <input
                    type="password"
                    placeholder='please enter again your new password'
                    onChange={handleChange}
                    name="repeatNewPassword"
                    value={form.repeatNewPassword}
                    required
                />
                {errorMessage && <h4 className="warnings">{errorMessage}</h4>}
                <button onClick={handleSubmit} className='btn'>change password</button>

            </form>
        </section>
    )
}