package com.ncl.team3.services;

import com.ncl.team3.models.ResultData;
import com.ncl.team3.models.User;
import org.springframework.stereotype.Service;

/**
 * This interface defines the information processing functions related to User,
 * such as providing user registration,
 * login and password change, information modification and other business requirements.
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/06 13:31:32
 */
@Service
public interface UserService {
    /**
     * user register function.
     * provide a function to achieve user login.
     * @param user user object
     * @return if code == 200, it registers successfully.
     *
     */
    ResultData register(User user);
    /**
     * This function provides the possibility to forget the original password in case the user forgets it
     * @param user user object, need have the password.
     * @return if code == 200, it changes successfully.
     */
    ResultData changePasswordByEmailCode(User user);
    /**
     * send active code to user email  box for user change the password.
     * @param email email account, need real email address.
     * @return if code == 200, it sends successfully.
     */
    ResultData sendCode(String email);
    /**
     * When user register successfully, the status is inactive.
     * The user need by this function to activate their account.
     * @param email email address.
     * @param activeCode the active code parses from email link.
     * @return if successfully return code == 200.
     */
    ResultData activeAccount(String email, String activeCode);
    /**
     * This is backup function.
     * @param email
     * @return
     */
    ResultData login(String email);
    /**
     * This method is for situation when the email send failed.
     * @param email email address.
     * @return if code = 200, that means resends successfully.
     */
    ResultData resendCode(String email);

    /**
     * Provide the method for editing user info.
     * @param realUser user object after modifying.
     * @return code == 200
     */
    ResultData editUserInfo(User realUser);
}
