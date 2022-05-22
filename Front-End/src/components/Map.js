import React, {useState} from "react";
import CastleInfo from "../data/CastleInfo";

export default function Map(){
    //State hook to hold the link to google map of the castles
    const [mapLink, setMapLink] = useState(CastleInfo[0].mapLink)

    /**
     * @version 1.0
     * @author Pete To
     * This function detects the castle which the user clicks at
     * it is identified by castle id:
     * @param id
     * then display corresponding castle map info
     */
    function handleClick(id){
        CastleInfo.map(element => {
            if(element.castleId === id){
                setMapLink(element.mapLink);
            }
        })
    }

    /**
     * @version 1.0
     * @author Pete To
     * This variable can store multiple JSX elements
     * number of JSX elements depends on number of castles in the object array "CastleInfo"
     * @type {castle[]}
     * the button included in the div tag will trigger the handleClick function
     */
    const showButton = CastleInfo.map(element =>
        <div key={element.castleId} className="box">
            <button onClick={function(){handleClick(element.castleId)}} className="btn">{element.castleName}</button>
        </div>
    )

    /**
     * @version 1.0
     * @author Pete To
     * The Map component consists of 2 parts:
     * 1. list of buttons pointing to different castles location
     * 2. an iframe tag to show the exact location of the castles on Google Map
     */
    return(
        <section className="map">

            <h1 className="heading">Map</h1>

            <div className="map-content">
                <div className="list">
                    {showButton}
                </div>
                <iframe
                    src={mapLink}
                    width="800" height="600"
                />
            </div>

        </section>
    )
}