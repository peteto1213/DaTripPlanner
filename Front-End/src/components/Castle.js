import React from "react";
import { Link } from "react-router-dom";
import CastleService from "../service/CastleService";
import CastleInfo from "../data/CastleInfo";

export default function Castle(){
    //State hook for storing incoming data from the Rest API
    const[castles, setAllCastles] = React.useState([{},{},{},{}])
    const[price, setPrice] = React.useState(CastleInfo)

    /**
     * @version 1.0
     * @author Pete To
     * The React useEffect hook will make a fetch request once for castle data from the Rest API
     * the targeted response (res) should be @type: castlesObject[]
     * upon successful fetching, information of the castles from the database will display
     * any error caught during fetching will be displayed as alert message
     */
    React.useEffect(() => {
        CastleService.getAllCastles()
            .then((res) => {
                if (res.data.code === 200){
                    console.log(res.data)
                    setAllCastles(res.data.data)
                }
                else {
                    alert(res.data.message())
                }
            })
            .catch(error => {
                alert("Failed to fetch data from server: " + error)
            })
    },[])

    /**
     * @version 1.0
     * @author Pete To
     * This variable will hold the incoming fetched data
     * @type {castles[{},{},{},{}]}
     * corresponding castle information will then be called in the return statement below
     */
    const displayCastles = castles.map(castle =>
        <div key={castle.castleId} className="box">
            <img src={castle.castleCoverPicAddress} alt="castle-image"/>

            <h3><i className="fa-solid fa-location-dot"/>{castle.castleName}</h3>

            <p>{castle.castleDescription}</p>

            <div className="castle-section">
                <i className="fa-solid fa-business-time"/>
                <span>{castle.castleOpenTimeInfo}</span>
            </div>

            <div className="castle-section">
                <i className="fa-solid fa-hourglass"/>
                <span>Est. tour time: {parseInt(castle.castleEstimatedTourTime/60)}hrs {castle.castleEstimatedTourTime%60}mins </span>
            </div>

            <div className="castle-section">
                <i className="fa-solid fa-sterling-sign"/>
                <span>{price[CastleService.getCastleIdByCastleName(castle.castleName) - 1].price} per guest</span>
            </div>

            <div className="castle-section">
                <i className="fa-solid fa-phone"/>
                <span>{castle.castleContactPhone}</span>
            </div>

            <Link
                to={CastleService.moreInformationRouter(castle.castleId)}
                className="btn link-to-castle"
            >
                <i className="fa-solid fa-circle-info"/>
                More information
            </Link>
        </div>
    )

    /**
     * @version 1.0.1
     * @author Pete To
     * edited by Pete To
     * The Castle component consists of 2 parts
     * 1. 4 castles information (upon successful fetching)
     * 2. Link to create trip section
     */
    return(
        <section className="castle">
            <h1 className="heading">Castles in Newcastle</h1>
            <div className="box-container">
                {displayCastles}
            </div>

            <div className="slogan">
                <Link className="link" to="/trip-name">let's create a trip right now!</Link>
            </div>
        </section>
    )
}