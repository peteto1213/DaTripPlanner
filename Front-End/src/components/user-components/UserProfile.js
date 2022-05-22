import React, {useEffect, useState} from 'react'
import {Link, useHistory} from "react-router-dom";
import UserService from "../../service/UserService";

export default function UserProfile() {
    /**
     * @version 1.0
     * @author Pete To
     * This React state hook will hold the user's profile information passing from the API after a successful login
     */
    const[user, setUser] = useState({
        firstName: "",
        lastName: "",
        email: localStorage.getItem("email")
    })

    const history = useHistory();

    /**
     * @version 1.0
     * @author Pete To
     * This React side effects will render once to GetUserInfo
     * no parameters needed to be passed, parameters are passed from cookie after user login
     */
    useEffect(() => {
        UserService.getUserInfo().then((res) => {
            if(res.data.code === 200){
                console.log(res)
                setUser(res.data.data)
            }else if(res.data.code === 401){
                alert("please check your email for verification")
            }else{
                console.log(res.data.message)
            }
        }).catch((error) => {
            console.log(error)
            alert("Something went wrong, You may need to login again")
            localStorage.setItem("email", "")
            history.push("/login")
        })
    },[])

    /**
     * @version 1.0
     * @author Pete To
     * The UserProfile Component will display the user's basic information fetched from API after successful login
     * 1. first name (from API)
     * 2. last name (from API)
     * 3. email (from localStorage)
     */
    return (
        <section className='userProfile'>
            <h1 className='heading'>Account Settings</h1>
            <h3 className='username'>Username</h3>

            <table className='user-info-table'>
                <tr>
                    <td className='table-header'><i className="fa-solid fa-user table-icon"/>First Name</td>
                    <td className="table-content">{user.firstName}</td>
                </tr>

                <tr>
                    <td className='table-header'><i className="fa-solid fa-user table-icon"/>Last Name </td>
                    <td className="table-content">{user.lastName}</td>
                </tr>

                <tr>
                    <td className='table-header'><i className="fa-solid fa-envelope table-icon"/>email</td>
                    <td className="table-content">{user.email}</td>
                </tr>
            </table>

            <div className='actions'>
                <Link to="/register">
                    <button className='btn'><i className="fa-solid fa-pen-to-square button-icon"/>Edit Profile</button>
                </Link>

                <Link to="/change-password">
                    <button className='btn'><i className="fa-solid fa-lock button-icon"/>Change Password</button>
                </Link>

                <Link to="/created-trips">
                    <button className='btn'><i className="fa-solid fa-calendar button-icon"/>View plans</button>
                </Link>

                <Link to="/created-orders">
                    <button className='btn'><i className="fa-solid fa-clipboard-list button-icon"/>View Order</button>
                </Link>
            </div>

        </section>
    )
}
