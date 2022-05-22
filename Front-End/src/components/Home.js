import React, { useState } from "react"
import videos from "../data/Videos"
import {Link} from 'react-router-dom'

export default function Home(){
    //imported videos save into an array for later retrieval
    const [videoArray, setVideoArray] = useState(videos)
    //default background video - alnwick castle
    const[video, setVideo] = useState(videoArray[0].vid)

    /**
     * @version 1.0
     * @author Pete To
     * This function detects user selection of background video
     * it is identified by video id
     * @param id
     * the video with the id matching the click action will be displayed, with corresponding button on
     * other videos in the videoArray will not be shown, with corresponding button off
     */
    function toggle(id){
        setVideoArray(preVideoArray=>{
            return preVideoArray.map((element) =>{
                return element.id === id ?{...element, isClicked: true} 
                : {...element, isClicked: false}
            })
        })
    }

    /**
     * @version 1.0
     * @author Pete To
     * This variable can store multiple JSX elements
     * number of JSX elements depends on number of videos in the object array "videoArray"
     * onClick of the video button will trigger the toggle function
     * @type {video[]}
     */
    const videoElement = videoArray.map(element =>
            <span key={element.id} onClick={function () {
                toggle(element.id)
                setVideo(element.vid)
        }}
                className={element.isClicked ? "vid-btn active" : "vid-btn"} data-src={element.vid}/>
        )

    /**
     * @version 1.0
     * @author Pete To
     * The Home component returns videos with corresponding button to toggle with
     * , triggering the toggle function above
     */
    return(
        <>
            <section className="home" id="home">
                <div className="content">
                    <h3>Create your own daytrip</h3>
                    <p>Explore the beautiful castles in Newcastle!</p>
                    <Link to="/castle" className="btn">discover more</Link>
                </div>

                <div className="controls">
                    {videoElement}
                </div>

                <div className="video-container">
                    <video id="video-slider" src={video} autoPlay muted loop/>
                </div>
            </section>
        </>
    )
}