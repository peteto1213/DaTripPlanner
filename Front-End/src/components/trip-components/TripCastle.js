import React, {useEffect, useState} from "react";
import {Link, useHistory} from "react-router-dom"
import CastleService from "../../service/CastleService";

export default function TripCastle(){

    //useHistory hook created to push to other components
    const history = useHistory();

    // create a local state to hold castle object array passed from backend
    const[castles, setCastles] = useState([]);

    // state to hold castleName to be stored to localStorage
    const[castleName, setCastleName] = useState("")

    /**
     * @author Pete To
     * @version 1.0
     * status will be triggered if user hasn't chosen any castle and try to proceed to next section
     * true: display message to prompt user to select a castle
     * false: no prompting message
     * default: false
     */
    const[status, setStatus] = useState(false)

    /**
     * @author Pete To
     * @version 1.0
     * This effect will only render once with an empty dependency array[]
     * It serves the following functions
     * 1. try to fetch API to get castle array
     * if successful, display castles, if not, catch the error and alert the user
     */
    useEffect(() => {
        //fetch API to get all castles' information
        CastleService.getAllCastles()
            .then((res) => {
            if (res.data.code === 200){
                //200 = successful, save the response to local state
                setCastles(res.data.data)
            }
            else {
                //catch any other http status message to alert
                alert(res.data.message())
            }
        })
            //catch any errors to alert
            .catch(error => {
            alert(error)
        })
    },[])

    /**
     * @author Pete To
     * @version 1.0
     * This variable can store multiple JSX elements(in a box) according to API response
     * @type {castles[]}
     * display castle information according to different castle object parameters(e.g. castleName, castleDescription etc.)
     * onClick of the corresponding castle box indicates the user's choice and trigger handleClick function
     * onClick of corresponding 'explore more button' will redirect user to view castle details
     */
    const castleList = castles.map(element =>
        <div key={element.castleId} onClick={function(){handleClick(element.castleId)}} className={element.isClicked ? "box-clicked" : "box"}>
            {element.isClicked && <i className="fa-solid fa-circle-check castle-selected-mark"/>}
            <img src={element.castleCoverPicAddress} alt="castleCoverPicAddress"/>
            <div className="content">
                <h3>{element.castleName}</h3>
                <p>{element.castleDescription}</p>
                {/* route to the targeted castle details if corresponding "explore more" button was clicked */}
                <Link to={CastleService.exploreMoreRouter(element.castleId)}  className="btn">explore more</Link>
            </div>
        </div>
    )

    /**
     * @author Pete To
     * @version 1.0
     * The function handleSubmit determines whether the user has selected a castle or not
     *     * the selected castle will be monitored by the state "castleName"
     *     * if castleName = empty String, warnings will appear to request the user to select a castle
     *     * else, push to the next section
     * @param event(submit of form)
     */
    function handleSubmit(event){
        event.preventDefault();
        localStorage.setItem("castleName", castleName);
        if(localStorage.getItem("castleName")){
            setStatus(false);
            history.push("/trip-date-time");
        }
        else{
            setStatus(true);
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * The function handleClick tracks the user's activity of selecting a castle
     * the id of the selected castle will be passed to local Storage
     * @param id (change of id according to user's click action)
     * Alnwick Castle: 1
     * Auckland Castle: 2
     * Bamburgh Castle: 3
     * Barnard Castle: 4
     */
    function handleClick(id){
        setCastles(prevCastles=>{
            return prevCastles.map((element)=>{
                return element.castleId === id ? {...element, isClicked: true}:
                    {...element, isClicked: false}
            })
        })
        switch (id){
            case 1: setCastleName("Alnwick")
                break
            case 2: setCastleName("Auckland")
                break
            case 3: setCastleName("Bamburgh")
                break
            case 4: setCastleName("Barnard")
                break
            default: setCastleName("")
        }
        localStorage.setItem("castleName", castleName)
        localStorage.setItem("castleId", id)
    }

    /**
     * @author Pete To
     * @version 1.0
     * TripCastle Component
     * 1. provide a list of castles (by fetching) for the user to choose from
     * 2. route to the targeted castle details if corresponding "explore more" button was clicked
     */
    return(
        <section className="trip-section">
            <h1 className="heading">Choose a castle</h1>
            {status && <h2 className="warnings">*please select a castle</h2>}
            <div className="box-container">

                {castleList} {/* refer to above function to fetch api to get list of castles */}
            </div>

            <div className="castle-buttons">
                <Link to="/trip-name" className="btn cancel-btn"><i className="fa-solid fa-circle-arrow-left"/> previous</Link>
                <button onClick={handleSubmit} className="btn">next <i className="fa-solid fa-circle-arrow-right"/></button>
            </div>

        </section>
    )
}