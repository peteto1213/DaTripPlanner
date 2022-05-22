/**
 * @author Pete To
 * @version 1.0
 * The DateFunction provides functions for conversion between date format and string
 */
class DateFunction{
    /**
     * @author Pete To
     * @version 1.0
     * This function will return the date of today in the format yyyy-mm-dd
     * @returns {Date}
     */
    getToday(){
        try{
            let today = new Date();
            let dd = String(today.getDate()).padStart(2, '0');
            let mm = String(today.getMonth() + 1).padStart(2, '0');
            let yyyy = today.getFullYear();

            today = yyyy + '-' + mm + '-' + dd;

            return today;
        }catch (error){
            console.log(error)
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return a converted the 8-digit string into the date-format yyyy-mm-dd
     * @param number
     * @returns {string}
     */
    convertDate(number){
        try{
            let stringNumber = ""+number;
            let yyyy = "";
            let mm = "";
            let dd = "";
            for(let i = 0; i <= 3; i++){
                yyyy += stringNumber.charAt(i);
            }
            for(let i = 4; i <= 5; i++){
                mm+= stringNumber.charAt(i);
            }
            for(let i = 6; i <= 7; i++){
                dd+= stringNumber.charAt(i);
            }

            let convertedDate = yyyy + '-' + mm + '-' + dd;

            return convertedDate;
        }catch (error){
            console.log(error)
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return a converted the 4-digit string into time format hh-mm
     * @param number
     * @returns {string}
     */
    convertTime(number){
        try{
            let stringNumber = "" + number;
            let hh = "";
            let mm = "";
            for(let i = 0; i <= 1; i++){
                hh += stringNumber.charAt(i);
            }
            for(let i = 2; i <= 3; i++){
                mm += stringNumber.charAt(i);
            }
            let convertedTime = hh + ":" + mm;

            return convertedTime;
        }catch(error){
            console.log(error)
        }
    }

    /**
     * @author Pete To
     * @version 1.0
     * This function will return a converted string of date/number passing in by
     * getting rid of all the special characters in the string
     * @param date
     * @returns {*}
     * @constructor
     */
    DateOrTimeToNumber(date){
        try{
            let result = date.replace(/[^a-zA-Z0-9 ]/g, '');
            return result;
        }catch (error){
            console.log(error)
        }
    }
}

export default new DateFunction();