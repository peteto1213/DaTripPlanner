import React, {useEffect, useRef, useState} from "react";
import castleInfo from "../../data/CastleInfo";
import {motion} from "framer-motion";
import {Link} from "react-router-dom";
import castleImages from "../../data/CastleImages";
import Tippy from "@tippy.js/react";
import 'tippy.js/dist/tippy.css';

/**
 * The content of the CastleExploration component is based on the props type (tripSection) passing in
 * different castle's information will be shown as a result
 * @param props
 * @returns {JSX.Element}
 * @constructor
 */
export default function CastleExploration(props){

    const[tripStatus, setTripStatus] = useState(false);

    useEffect(() => {
        setTripStatus(props.tripSection);
    },[])

    let targetCastle = castleInfo[props.castleId - 1];
    let castleImage = castleImages[props.castleId - 1].images;

    const[width,setWidth] = useState(0);
    const carousel = useRef();
    useEffect(() => {
        setWidth(carousel.current.scrollWidth - carousel.current.offsetWidth);
    },[])

    const toDo = targetCastle.to_do.map(element =>
        <li key={element.id}><a href={element.link}>{element.name}</a></li>
    )

    /**
     * @author Pete To
     * @version 1.0
     * This variable will hold the restaurant carousels to scroll on when the user is viewing
     */
    const restaurants = targetCastle.restaurants.map(element =>
        <motion.div key={element.id} className="card">

            <img src={element.photo} alt="restaurant"/>
            <h3>{element.name}</h3>
            <p>Ratings: <span>{element.ratings}</span></p>
            <p>Address: <span>{element.address}</span></p>
            <p>Service Type: <span>{element.service}</span></p>
            <a
                className="btn"
                href="https://www.tripadvisor.com/Restaurants-g504032-Alnwick_Northumberland_England.html"
                target="_blank"
            >
                learn more
            </a>

        </motion.div>
    )

    const[counter, setCounter] = useState(0)

    //react state hook to hold the current displaying image
    const[imageToDisplay, setImageToDisplay] = useState(castleImage[counter])

    /**
     * @author Pete To
     * @version 1.0
     * When the next/previous button of besides the castle images is clicked, the conter will change,
     * trigger the switching of the photo
     */
    useEffect(() => {
        setImageToDisplay(castleImage[counter])
    },[counter])

    /**
     * @author Pete To
     * @version 1.0
     * this function refers to the imageToDisplay react state hook
     */
    function showNextImage(){
        if(counter < castleImage.length - 1){
            setCounter(prevCounter => {
                return prevCounter+1
            })
        }else{
            setCounter(0)
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * this function refers to the imageToDisplay react state hook
     */
    function showPrevImage(){
        if(counter <= castleImage.length - 1 && counter > 0){
            setCounter(prevCounter => {
                return prevCounter-1
            })
        }else{
            setCounter(castleImage.length - 1)
        }
    }


    return(
        <section className="castle-section">
            <h1 className="heading">{targetCastle.castleName}</h1>

            <div className="row">

                <div className="row-image">
                    <button className=" switch switch-left" onClick={showPrevImage}><i className="fa-solid fa-circle-left"/></button>
                    <img
                        src={imageToDisplay}
                        alt="photo_of_castle"
                    />
                    <button className="switch switch-right" onClick={showNextImage}><i className="fa-solid fa-circle-right"/></button>
                </div>

                <div className="description">

                    <div className="description-section">
                        <i className="fa-solid fa-location-dot">
                            <span>Northumberland</span>
                        </i>
                    </div>

                    <div className="description-section">
                        <h3>About the castle</h3>
                        <p>{targetCastle.castleDescription}</p>
                    </div>

                    <div className="description-section">
                        <h3>Popular Things to do around {targetCastle.castleName}</h3>
                        <ul>
                            {toDo}
                        </ul>
                    </div>

                    <div className="description-section">
                        <h3>Ticket Price</h3>
                        <i className="fa-solid fa-sterling-sign">
                            <span>{targetCastle.price}</span>
                        </i>
                    </div>

                    <div className="description-section">
                        <h3>Approximate trip time</h3>
                        <i className="fa-solid fa-hourglass">
                            <span>{targetCastle.duration}</span>
                        </i>
                    </div>

                    <div className="description-section">
                        <h3>Accessibility and related services</h3>
                        <Tippy content={targetCastle.accessibility} interactive={true}>
                            <a href={targetCastle.accessibilityLink} target="_blank"><i className="fa-solid fa-wheelchair"/></a>
                        </Tippy>
                    </div>

                    <a href={targetCastle.link} target="_blank" rel="noreferrer" className="btn">More information</a>
                </div>
            </div>

            <motion.div className="carousel-row">
                <h1>Restaurants around {targetCastle.castleName}</h1>
                <motion.div
                    drag="x"
                    dragConstraints={{right: 0, left: -width}}
                    className="restaurant-carousel"
                    whileTap={{cursor: "grabbing"}}
                    ref={carousel}
                >
                    {restaurants}
                </motion.div>
            </motion.div>

            {!tripStatus ?
                <Link to="/trip-castle" className="btn back-btn"><i className="fa-solid fa-circle-arrow-left"/> Back to select castle</Link>
                :
                <Link to="/castle" className="btn back-btn"><i className="fa-solid fa-circle-arrow-left"/> Back to select castle</Link>
            }


        </section>
    )
}