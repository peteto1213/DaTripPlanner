class SystemFunction{
    /**
     * @author Pete To
     * @version 1.0
     * This function will return the value of the cookie according to "name" key
     * @param name
     * @returns {string|null}
     */
    getCookie(name) {
        // Split cookie string and get all individual name=value pairs in an array
        let cookieArr = document.cookie.split(";");

        // Loop through the array elements
        for(let i = 0; i < cookieArr.length; i++) {
            let cookiePair = cookieArr[i].split("=");

            /* Removing whitespace at the beginning of the cookie name
            and compare it with the given string */
            if(name == cookiePair[0].trim()) {
                // Decode the cookie value and return
                return decodeURIComponent(cookiePair[1]);
            }
        }

        // Return null if not found
        return null;
    }
}

export default new SystemFunction();