package com.ncl.team3.controllers;

import com.ncl.team3.models.ResultData;
import com.ncl.team3.models.User;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/03 15:49:28
 */

public interface UserController {

    ResultData register(String email,String password,String repeatPassword,String lastName,String firstName);
    ResultData activeAccount(String email,String activeCode);
    ResultData login(String email,String password);
    ResultData changePasswordByOldPassword(String oldPassword,String newPassword,String oldNewPassword);
    ResultData changePasswordByEmailCode(String code,String email, String newPassword,String repeatPassword);
    ResultData sendCode(String email);
    ResultData logOut();
    ResultData resendActiveCode(String email);
    ResultData getUserInfo();
    ResultData editUserInfo(User user);

}
