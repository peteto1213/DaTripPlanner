import React from "react";
import ReferenceList from "../data/ReferenceList";

/**
 * @author Pete To
 * @version 1.0
 * The Reference component will return a JSX section holding all the references used in the website
 * @returns {JSX.Element}
 */
export default function Reference(){

    /**
     * @author Pete To
     * @version 1.0
     * This variable holds the references used in the website passed from the ReferenceList js file
     * @type {references[]}
     */
    const showReference = ReferenceList.map(element =>
        <div className="row">
            <h3>{element.title}</h3>
            <p>{element.citation}</p>
            <p>Retrieved from: <a href={element.link} target="_blank">{element.link}</a></p>
        </div>
    )

    return(
        <section className="reference">
            <h1 className="heading"><i className="fa-solid fa-book"/> Reference list</h1>

            <div className="row-container">
                {showReference}
            </div>
        </section>
    )
}