import React, {useRef, useState} from "react"
import NavbarLinks from "../data/NavbarLinks";
import { Link } from "react-router-dom";
import Tippy from "@tippy.js/react";
import 'tippy.js/dist/tippy.css';


export default function HeaderAfterLogin({status, LogoutFunction}){
    //State of the search-bar display, default: false
    const [showSearch,setShowSearch] = useState(false)

    //State of the nav-bar display, default: false
    const[showNav, setShowNav] = useState(false)

    //track user's input in the search bar
    const searchRef = useRef();

    /**
     * @version 1.0
     * @author Pete To
     * onClick of the search button besides the search bar, according to user's input
     * redirect to the Google search page with the input wordings
     */
    function handleSearch(){
        window.open(
            `https://www.google.com/search?q=${searchRef.current.value}`,
            '_blank'
        );
    }

    /**
     * @version 1.0
     * @author Pete To
     * On trigger, close all search-bar and nav-bar
     */
    function setAllToFalse(){
        setShowSearch(false)
        setShowNav(false)
    }

    //onScroll effects: trigger "setAllToFalse" function
    window.onscroll = ()=>{
        setAllToFalse()
    }

    /**
     * @version 1.0
     * @author Pete To
     * This variable stores a navbar object array
     * number of objects in the array depends on the imported NavbarLinks.js file
     * if wish to add new navigation in the future, add it in the NavbarLinks.js file separately
     * @type {navbar[]}
     */
    const navLinks = NavbarLinks.map(element =>
        <Link
            key={element.id}
            to={element.href}
            className="icon-block"
            onClick={setAllToFalse}
        >
            <i className={element.iClass}/>
            <span>{element.title}</span>
        </Link>
    )

    /**
     * @version 1.0
     * @author Pete To
     * The Header function will return 2 types of component based on the user state of login
     * The Header component will return a <header> tag consisting of:
     * 1. Title of the website
     * 2. NavbarLinks to the website
     * 3. Search function
     * 4. Header-icons section (seen below)
     *
     * the only difference is the div-section with className="icons" (Header-icons section)
     * after-login: display navigation icons to 1. user-profile 2. logout function
     * before-login: display navigation icons to 1. login function
     */
    return(
        <>
            <header>
                <div onClick={() => setShowNav(!showNav)} id="menu-bar" className={showNav ? "fa fa-times" : "fas fa-bars"}/>

                {/* Title of the website */}
                <a href="#" className="logo"><span>Da</span>Trip Planner</a>

                {/* NavbarLinks to the website */}
                <nav className={showNav ? "navbar active" : "navbar"}>
                    {navLinks}
                </nav>

                {/* Header-icons section after login, with navigation to user-profile and logout function */}
                {status === "after-login" ?
                    <div className="icons">
                        <i onClick={() => setShowSearch(!showSearch)} className={showSearch ? "fa fa-times" : "fa fa-search"} id="search-btn"/>

                        <Tippy content="View user profile">
                            <Link
                                className="fa-solid fa-id-card login_icon"
                                to="/profile"
                                onClick={setAllToFalse}
                            >
                            </Link>
                        </Tippy>

                        <Tippy content="Logout">
                            <Link
                                className="fa-solid fa-arrow-right-from-bracket login_icon"
                                to="/Home"
                                onClick={function(){
                                    setAllToFalse();
                                    LogoutFunction();
                                }}
                            >
                            </Link>
                        </Tippy>

                    </div>
                    :
                    // Header-icons section before login, only login icon
                    <div className="icons">
                        <i onClick={() => setShowSearch(!showSearch)} className={showSearch ? "fa fa-times" : "fa fa-search"} id="search-btn"/>

                        <Tippy content="Login / Register">
                            <Link
                                className="fa fa-user login_icon"
                                to="/login"
                                onClick={setAllToFalse}
                            >
                            </Link>
                        </Tippy>

                    </div>
                }

                {/* Search function */}
                <form action="" className={showSearch ? "search-bar-container active" : "search-bar-container"}>
                    <input
                        type="search"
                        id="search-bar"
                        placeholder="perform a google search here..."
                        ref={searchRef}
                    />
                    <label  onClick={handleSearch} htmlFor="search-bar" className="fas fa-search"/>
                </form>
            </header>
        </>
    )
}

