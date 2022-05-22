import React from "react";
import { Link } from "react-router-dom";
import NavbarLinks from "../data/NavbarLinks";
import FooterData from "../data/FooterData";

export default function Footer(){

    /**
     * @version 1.0
     * @author Pete To
     * This variable stores a navbar object array
     * @type {navbar[]}
     * number of objects in the array depends on the imported NavbarLinks.js file
     * if wish to add new navigation in the future, add it in the NavbarLinks.js file separately
     */
    const links = NavbarLinks.map((element) =>
        <Link key={element.id} to={element.href}>{element.title}</Link>
    )

    /**
     * @version 1.0
     * @author Pete To
     * This variable stores a member object array
     * @type {members[]}
     * number of objects in the array depends on the data returned from the FooterData function getMembers()
     * if wish to there are new members in the future, add him/her to the FooterData.js file separately
     */
    const members = FooterData.getMembers().map((element) =>
        <a key={element.id} href={element.profileLink} target="_blank">{element.name}</a>
    )

    /**
     * @version 1.0
     * @author Pete To
     * This variable stores a socialMediaLinks object array
     * @type {socialMediaLinks[]}
     * number of objects in the array depends on the data returned from the FooterData function getSocialMediaLinks()
     * if wish to add new social medias in the future, add it to the FooterData.js file separately
     */
    const socialMediaLinks = FooterData.getSocialMediaLinks().map((element) =>
        <a key={element.id} href={element.mediaLink} target="_blank">{element.name}</a>
    )

    /**
     * @version 1.0
     * @author Pete To
     * The Footer component consists of 4 parts
     * 1. about us description
     * 2. member list
     * 3. navigation links to the website
     * 4. social media link list
     */
    return(
        <>
            <section className="footer">

                <div className="box-container">

                    {/* About us description from FooterData.js file */}
                    <div className="box">
                        <h3>About us</h3>
                        <p className="no-transformation">
                            {FooterData.getAboutUs()}
                        </p>
                    </div>

                    {/* members list from FooterData.js members object array */}
                    <div className="box">
                        <h3>Our Members</h3>
                        {members}
                    </div>

                    {/* navBarLinks from corresponding .js file imported */}
                    <div className="box">
                        <h3>Navigations</h3>
                        {links}
                    </div>

                    {/* socialMediaLinks list from FooterData.js socialMediaLinks object array */}
                    <div className="box">
                        <h3>follow us</h3>
                        {socialMediaLinks}
                    </div>

                </div>

                <h1 className="credit"> created by <span> CSC 8019 Team 3 </span> | all rights reserved</h1>
            </section>  
        </>
    )
}