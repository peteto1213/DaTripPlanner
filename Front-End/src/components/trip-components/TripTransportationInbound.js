import React, {useEffect, useState} from "react"
import {useHistory} from "react-router-dom";
import TripTransportationDetails from "./TripTransportationDetails";
import Tippy from "@tippy.js/react";
import 'tippy.js/dist/tippy.css';
import 'tippy.js/themes/light.css';
import TripService from "../../service/TripService";
import DateFunction from "../../CustomFunction/DateFunction";


export default function TripTransportationInbound(){

    const[ticketArray, setTicketArray] = useState([]);
    const[routes, setRoutes] = useState([])
    const[show, setShow] = useState(false);
    const[currentRouteId, setCurrentRouteId] = useState(0);
    const[ticketDetails, setTicketDetails] = useState({});

    const[transportTickets, setTransportTickets] = useState([]);
    const[loadingMessage, setLoadingMessage] = useState("loading Inbound Route Data")

    useEffect(() => {
        if(localStorage.getItem("transportTickets")){
            let array = JSON.parse(localStorage.getItem("transportTickets"));
            setTicketArray(array);
        }
    },[localStorage.getItem("transportTickets")])
    //castleId, date,departureTime,ticketQuantity
    /**
     * This is write By Lei, This is for Pete to handle complex data.
     * @param data the data from back-end
     * @returns {any[]}
     */
    function dataHandle(data) {
        let routes = new Array();
        //遍历路线
        for (let key in data){
            console.log(data)
            //为了拿到票的信息
            let route = new Object();
            let totalTime = 0;
            let routeDetails = new Array();
            let routeInfo ;
            for (let i = 0; i < data[key].length; i++) {
                let routeDetail = new Object();
                let ticketInfo = data[key][i];
                //determine which type of ticket
                try {
                    routeDetail.price = ticketInfo.price;
                    routeDetail.operator = ticketInfo.busTimetable.bus.busOperator.name;
                    routeDetail.transportName = ticketInfo.busTimetable.bus.busName;
                    routeDetail.ticketId= ticketInfo.busTicketId;
                    //Save ticket info to local store;
                    routeDetail.ticketType = ticketInfo.type;
                    routeDetail.date = ticketInfo.busTimetable.date;
                    routeDetail.time = ticketInfo.busTimetable.time;
                    routeDetail.availableTicketQuantity = ticketInfo.quantity;
                    routeDetail.routeType = 'bus'
                    //gps
                    routeDetail.startPoint = ticketInfo.busTimetable.routeDetail.startPointGps.name;
                    routeDetail.startPointGoogle = ticketInfo.busTimetable.routeDetail.startPointGps.googleAddress;

                    routeDetail.endPoint = ticketInfo.busTimetable.routeDetail.endPointGps.name;
                    routeDetail.endPointGoogle = ticketInfo.busTimetable.routeDetail.endPointGps.googleAddress;

                    routeDetail.travelTime = ticketInfo.busTimetable.travelTime;
                    totalTime = totalTime+routeDetail.travelTime;
                    routeDetails[i]=routeDetail;
                    routeInfo = ticketInfo.busTimetable.routeDetail.route;
                }catch (e){
                    routeDetail.price = ticketInfo.price;
                    routeDetail.operator = ticketInfo.trainTimetable.train.trainOperator.name;
                    routeDetail.transportName = ticketInfo.trainTimetable.train.trainName;
                    routeDetail.ticketId= ticketInfo.trainTicketId;
                    //Save ticket info to local store;
                    routeDetail.ticketType = ticketInfo.type;
                    routeDetail.date = ticketInfo.trainTimetable.date;
                    routeDetail.time = ticketInfo.trainTimetable.time;
                    routeDetail.availableTicketQuantity = ticketInfo.quantity;
                    routeDetail.routeType = 'train';

                    routeDetail.startPoint = ticketInfo.trainTimetable.routeDetail.startPointGps.name;
                    routeDetail.startPointGoogle = ticketInfo.trainTimetable.routeDetail.startPointGps.googleAddress;
                    routeDetail.endPoint = ticketInfo.trainTimetable.routeDetail.endPointGps.name;
                    routeDetail.endPointGoogle = ticketInfo.trainTimetable.routeDetail.endPointGps.googleAddress;

                    routeDetail.travelTime = ticketInfo.trainTimetable.travelTime;
                    totalTime = totalTime+routeDetail.travelTime;
                    routeDetails[i]=routeDetail;
                    routeInfo = ticketInfo.trainTimetable.routeDetail.route;
                }

            }
            route.routeInfo = routeInfo;
            route.name = routeInfo.routeName;
            route.key = routeInfo.routeId;
            route.routeDetails = routeDetails;
            route.totalTime = totalTime;
            route.routeKey = key;
            route.deparuteName = routeInfo.departureGps.name;
            route.deparuteNameGoogle = routeInfo.departureGps.googleAddress;
            route.destinationName =routeInfo.destinationGps.name;
            route.destinationGoogle =routeInfo.destinationGps.googleAddress;
            route.isSelected = false;
            route.isClicke =  false;

            routes.push(route);
        }
        console.log(routes)
        return routes;
    }

    function handlePrevious(){
        localStorage.setItem("transportTickets", "")
        history.push("/trip-castle-ticket")
    }

    useEffect(() => {
        let castleId = localStorage.getItem("castleId");
        let date = DateFunction.DateOrTimeToNumber(localStorage.getItem("date"));
        let departureTime = localStorage.getItem("time");
        let ticketQuantity = localStorage.getItem("totalTicketQuantity");
        TripService.getRouteData(castleId,date,departureTime,ticketQuantity).then((res) => {
            let result = res.data;
            if (result.code === 200){
                if (result.data !== null ) {
                    //Change the result data structure to a Map
                    let routesData = dataHandle(result.data);
                    if (routesData.length > 0 ){
                        setRoutes(routesData)
                    }else {
                        setLoadingMessage("There is no available route data for your selected start time, please select another time slot ")
                    }
                }else {
                    setLoadingMessage(res.data.message)
                }
            }
            else {
                setLoadingMessage(res.data.message)
            }
            // console.log(res.data)
        }).catch(error => {
            setLoadingMessage(error)
        })

        if(localStorage.getItem("transportTickets")){
            setTransportTickets(JSON.parse(localStorage.getItem("transportTickets")));
        }
    },[])

    const history = useHistory();
    function handleShow(key,route){
        localStorage.setItem("routeId",route.key)
        setRoutes(prevRoute => {
            return prevRoute.map((element) => {
                element.isSelected = false;
                element.isClicked = false;
                return element.name === key ? {...element, isClicked: true,isSelected:true}:
                    {...element}
            })
        })
    }

    function handleHide(key){
        setRoutes(prevRoute => {
            return prevRoute.map((element) => {
                return element.name === key ? {...element, isClicked: false}:
                    {...element}
            })
        })
    }


    /**
     * Created by Pete
     * @param routeId
     * @param stopId
     * We use both routeId and stopId to determine the user's selection
     * the details of the selection will be displayed in separate components onClick
     */
    function handleSelect(routeDetail,routeId){
        setCurrentRouteId(currentRouteId=>{
            return routeId;
        })
        setTicketDetails(routeDetail)
        setShow(true)
    }

    /**
     * Created by Pete
     * @param stopName
     * @returns {JSX.Element}
     * We use stopName to determine the mini-map to show
     * it will return the JSX element (a mini-map of the stop)
     * 1. user can then view the GPS location of the stop on hover
     * 2. user can click on the link to view it via Google map
     */
    const miniMap = (gpsName,gpsGoogleAddress) => (
        <div>
            <h3>{gpsName}</h3>
            <iframe
                src={gpsGoogleAddress}
                width="250" height="200"
            />
        </div>
    )

    function handleSubmit(){
        history.push("/trip-back-time")
    }

    /**
     * Created by Pete
     * Edit By lei
     * This is about route page
     */
    const showRoutes = routes.map(route =>
        <div key={route.name} className={route.isSelected ? "box-clicked" : "box"}>
            <h1 className="heading" > Route For You</h1>
            {!route.isClicked ?
                <div>
                    <h3>From {route.deparuteName} to {route.destinationName}</h3>
                    <h3>Estimated time: {route.totalTime} mins</h3>
                </div>
                :
                <div>
                    <div className="main-route">
                        <h3>From {route.deparuteName} to {route.destinationName}</h3>
                        <h3>Estimated time: {route.totalTime} mins</h3>
                    </div>
                    <div>
                        <Tippy
                            content={miniMap(route.deparuteName,route.deparuteNameGoogle)}
                            theme={'light'}
                            interactive={true}
                        >
                            <h3 className="destination">
                                <a href={`https://www.google.com/maps/place/${route.deparuteName}`}>
                                    <i className="fa-solid fa-location-dot"/> {route.deparuteName}
                                </a>
                            </h3>
                        </Tippy>
                        {route.routeDetails.map(routeDetail =>

                            <div key={routeDetail.ticketId} className="main-route-details">
                                <div className="stops">
                                    {/* Tippy */}
                                    <Tippy
                                        content={miniMap(routeDetail.startPoint,routeDetail.startPointGoogle)}
                                        theme={'light'}
                                    >
                                        <h3 className="stop-name">
                                            <a href={`https://www.google.com/maps/place/${routeDetail.startPoint}`} target="_blank">
                                                <i className="fa-solid fa-circle stop-icon"/> {routeDetail.startPoint}
                                            </a>
                                        </h3>
                                    </Tippy>
                                    {/* End of Tippy */}

                                    <h3 className="stop-time"><i className="fa-solid fa-clock"/> {routeDetail.travelTime} mins</h3>
                                    <Tippy
                                        content={miniMap(routeDetail.endPoint,routeDetail.endPointGoogle)}
                                        theme={'light'}
                                    >
                                        <h3 className="stop-name">
                                            <a href={`https://www.google.com/maps/place/${routeDetail.endPoint}`} target="_blank">
                                                <i className="fa-solid fa-circle stop-icon"/> {routeDetail.endPoint}
                                            </a>
                                        </h3>
                                    </Tippy>
                                </div>

                                <div>
                                    <button
                                        className="btn add-btn"
                                        onClick={() => {handleSelect(routeDetail,route.key)}}
                                    >
                                        <i className="fa-solid fa-cart-plus"/> view ticket details
                                    </button>
                                </div>

                            </div>
                        )}
                        <Tippy
                            content={miniMap(route.destinationName, route.destinationGoogle)}
                            theme={'light'}
                            interactive={true}
                        >
                            <h3 className="destination">
                                <a href={`https://www.google.com/maps/place/${route.destinationName}`}>
                                    <i className="fa-solid fa-location-dot"/> {route.destinationName}
                                </a>
                            </h3>
                        </Tippy>
                    </div>
                </div>
            }

            {!route.isClicked ?
                <button onClick={function(){handleShow(route.name,route)}} className="btn">show details and choice</button>
                :
                <button onClick={function(){handleHide(route.name)}} className="btn">hide details</button>
            }
        </div>
    )

    function clearBasket(){
        localStorage.setItem("transportTickets", "")
        history.push('/trip-transportation-inbound')
    }

    return(
        <section className="trip-section">
            {show && <button onClick={() => setShow(false)} className="fas fa-times form-close"/>}
            <h1 className="heading">Suggested Route (Inbound)</h1>

            {routes.length === 0 ?
                <div className="loading-container"><h1 className="loading">{loadingMessage}</h1></div>
                :
                <div className="box-container">
                    {showRoutes}
                </div>
            }

            {ticketArray.length != 0 ?
                <div className="confirm-tickets">
                    <h2>Inbound Tickets basket: </h2>
                    <button onClick={() => clearBasket()} className="btn bin-btn"><i className="fa-solid fa-dumpster"/> clear basket</button>
                    {ticketArray.map(element =>
                        <div className="ticket-basket-row">
                            <h3>No./Operator: <span>{element.operator}</span></h3>
                            <h3>Type: <span>{element.type}</span></h3>
                            <h3>Price: <span>{element.price}</span></h3>
                            <h3>Date: <span>{localStorage.getItem("date")}</span></h3>
                            <h3>Quantity: <span>{element.ticketQuantity}</span></h3>
                        </div>
                    )}
                </div>
                :
                <div className="confirm-tickets">
                    <h2>Inbound Tickets basket:</h2>
                    <h2 className="heading">Your Transportation ticket basket is currently Empty</h2>
                </div>
            }

            <div>
                <button onClick={handlePrevious} className="btn cancel-btn"><i className="fa-solid fa-circle-arrow-left"/> previous</button>
                <button onClick={handleSubmit} className="btn">next <i className="fa-solid fa-circle-arrow-right"/></button>
            </div>



            {show &&
            <TripTransportationDetails
                ticketData ={ticketDetails}
                transportTickets = {transportTickets}
                currentRouteId = {currentRouteId}
            />
            }
        </section>
    )
}
