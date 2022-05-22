import React, {useEffect, useState} from "react"
import {Switch, Route, withRouter, Redirect, useLocation} from "react-router-dom"
import{useHistory} from "react-router-dom";

//Basic components imports
import Header from "./Header";
import Home from "./Home";
import Castle from "./Castle";
import Contact from "./Contact";
import Footer from "./Footer";
import Map from "./Map"
import Reference from "./Reference";

//User components imports
import UserProfile from "./user-components/UserProfile";
import UserCreatedTrips from "./user-components/UserCreatedTrips";
import UserTripDetails from "./user-components/UserTripDetails";
import Checkout from "./user-components/Checkout";
import Login from "./user-components/Login";
import Register from "./user-components/Register";
import ChangePassword from "./user-components/ChangePassword";
import ForgetPassword from "./user-components/ForgetPassword";
import PasswordCode from "./user-components/PasswordCode";

//Trip components imports
import TripName from "./trip-components/TripName";
import TripCastle from "./trip-components/TripCastle";
import TripDateAndTime from "./trip-components/TripDateAndTime";
import TripCastleTicket from "./trip-components/TripCastleTicket";
import TripTransportationInbound from "./trip-components/TripTransportationInbound";
import TripSummary from "./trip-components/TripSummary";
import TripTransportationDetails from "./trip-components/TripTransportationDetails";
import TripDateAndTimeForBack from "./trip-components/TripDateAndTimeForBack";
import TripTransportationOutbound from "./trip-components/TripTransportationOutbound";


//Castle components imports
import UserService from "../service/UserService";
import CastleExploration from "./castle-components/CastleExploration";
import * as queryString from "query-string";
import SystemFunction from "../CustomFunction/SystemFunction";


function Main() {
    //React hooks for routing to different functional components
    const history = useHistory();

    //storing of user input email for login
    const [user, setUser] = useState({
        email: ""
    })

    //storing of error message
    const [error, setError] = useState("")

    const location = useLocation();

    function handleSuccessLogin(){
        const { redirectTo } = queryString.parse(location.search);
        history.push(redirectTo == null ? "/profile" : redirectTo);
    }

    /**
     * @version 1.0
     * @author Pete To
     * @param details @type: Object with 2 properties
     * 1. email 2. password
     * This login function will first check if user's email is a school email
     * if successfully passed the check, the formData will be sent to backend for authentication
     * else, 1. the data will not pass to backend 2. an error message will pop out
     */
    function loginFunction(details){
        //check with the input email is a school domain email
        if(details.email.includes("ac.uk")){
            setUser({
                email: details.email
            })
            //fetch to api to perform a post request for user authentication
            UserService.loginUser(details)
                .then((res) => {
                //Http status code 200 denotes successful authentication
                if(res.data.code === 200){
                    setError("")
                    alert("Logged in successfully, user email: " + details.email)
                    localStorage.setItem("email", details.email)
                    console.log(res)
                    //handleSuccessLogin function
                    handleSuccessLogin();

                //Other status code denotes unsuccessful authentication
                }else{
                    console.log(res.data.code + ": " + res.data.message)
                    setError("incorrect username and/or password") //error message pop out
                }
                //catch for any server failure message for exception handling
            }).catch(error => {
                console.log("server error" + error)
            })
        //if not a school domain email, pop out a warning message
        }else{
            setError("Login email needs to belong to the domain @xxx.ac.uk")
        }
    }

    /**
     * @version 1.0
     * @author Pete To
     * This function will log out the user by:
     * 1. clearing the login Email at the localStorage
     * 2. clearing the user's cookie section details on the server and client side
     */
    function logoutFunction(){
        //clear all local storage
        localStorage.clear();
        setUser({email: ""})

        setError("")

        //clear cookie session
        UserService.logoutUser()
            .then((res) => {
                if(res.data.code === 200){
                    alert("User logout successfully")
                }else{
                    alert("Failed to logout, Reason: " + res.data.message)
                }
            })
            .catch((error) => {
            console.log(error)
        })
    }


    /**
     * @authoer Pete To
     * @version 1.0
     * The MainComponents hold all the routers and path to the webpage
     */
    return (
        <div>
            <div className="main-content">
                {/* Display of header 1. before user logged in and 2. after logged in */}
                {(localStorage.getItem("email")) ?
                    <Header status="after-login" LogoutFunction={logoutFunction}/>
                    :
                    <Header status="before-login"/>
                }
                <Switch>
                    {/* Routers to basic website navigation
                        1. homepage
                        2. basic castle features
                        3. contact for information
                        4. view castle location information (map)
                        5. Reference list
                     */}
                    <Route path='/home' component={Home} />
                    <Route path='/castle' component={Castle} />
                    <Route path='/contact' component={Contact} />
                    <Route path='/map' component={Map}/>
                    <Route path='/reference' component={Reference}/>

                    {/* Router to the create-trip components, including
                        1. create trip name
                        2. choose of targeted castle
                        3. choose date and time
                        4. select amount of castle tickets
                        5. select amount of inbound transportation tickets
                        6. view transportation details (e.g. operators, route number etc.)
                        7. view trip summary
                    */}
                    <Route path='/trip-name' component={TripName}/>
                    <Route path='/trip-castle' component={TripCastle}/>
                    <Route path='/trip-date-time' component={TripDateAndTime}/>
                    <Route path='/trip-castle-ticket' component={TripCastleTicket}/>
                    <Route
                        path='/trip-transportation-inbound'
                        component={() => <TripTransportationInbound withDetails={false} />}
                    />
                    <Route path='/trip-back-time' component={TripDateAndTimeForBack} />
                    <Route
                        path='/trip-transport-outbound'
                        component={() => <TripTransportationOutbound withDetails={false} />}
                    />
                    <Route path='/trip-summary' component={TripSummary} />
                    <Route path='/trip-transportation-details' component={TripTransportationDetails} />

                    {/* Routers to user services components
                        1. register/edit profile
                        2. login
                        3. Change password
                        4. view user profile
                        5. view created plans or orders
                        6. view individual plan or order details
                        7. checkout plans (plan turned to order afterwards)
                        8. Actions when forget password
                    */}
                    <Route path='/register' component={Register}/>
                    <Route path='/login'>
                        <Login LoginFunction={loginFunction} error={error}/>
                    </Route>
                    <Route path='/change-password' component={ChangePassword}/>
                    <Route path='/profile' component={UserProfile}/>
                    <Route path='/user-plan-details'>
                        <UserTripDetails type="plan"/>
                    </Route>
                    <Route path='/user-order-details'>
                        <UserTripDetails type="order"/>
                    </Route>
                    <Route path='/created-trips'>
                        <UserCreatedTrips status="unpaid"/>
                    </Route>
                    <Route path='/created-orders'>
                        <UserCreatedTrips status="paid"/>
                    </Route>
                    <Route path='/checkout' component={Checkout} />
                    <Route path='/forget-password' component={ForgetPassword} />
                    <Route path='/password-code' component={PasswordCode} />

                    {/* Routers to different castle components according to
                        1. castle id @type: integer
                        2. belongs to whether or not trip-planning section @type boolean
                    */}
                    <Route path='/castle-alnwick'>
                        <CastleExploration castleId={1} tripSection={false}/>
                    </Route>
                    <Route path='/castle-auckland'>
                        <CastleExploration castleId={2} tripSection={false}/>
                    </Route>
                    <Route path='/castle-bamburgh'>
                        <CastleExploration castleId={3} tripSection={false}/>
                    </Route>
                    <Route path='/castle-barnard'>
                        <CastleExploration castleId={4} tripSection={false}/>
                    </Route>
                    <Route path='/alnwick'>
                        <CastleExploration castleId={1} tripSection={true}/>
                    </Route>
                    <Route path='/auckland'>
                        <CastleExploration castleId={2} tripSection={true}/>
                    </Route>
                    <Route path='/bamburgh'>
                        <CastleExploration castleId={3} tripSection={true}/>
                    </Route>
                    <Route path='/barnard'>
                        <CastleExploration castleId={4} tripSection={true}/>
                    </Route>

                    {/* default: redirect to homepage */}
                    <Redirect to="/home" />
                </Switch>
            </div>

            <Footer />
        </div>
    );
}

export default withRouter(Main);