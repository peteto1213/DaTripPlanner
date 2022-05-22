package com.ncl.team3.controllers.imp;

import com.ncl.team3.controllers.UserController;
import com.ncl.team3.mappers.UserMapper;
import com.ncl.team3.models.ResultData;
import com.ncl.team3.models.User;
import com.ncl.team3.services.UserService;
import com.ncl.team3.util.EmailUtil;
import com.ncl.team3.util.MD5EncryptionUtil;
import com.sun.istack.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import javax.xml.transform.Result;
import java.time.Duration;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/06 15:18:41
 */
@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin(allowCredentials = "true",originPatterns = "*")
public class UserControllerImp implements UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private UserMapper userMapper;

    /**
     * For user register
     * @param email email address
     * @param password user password,length >= 8
     * @param repeatPassword must same as password.
     * @param lastName user's last name
     * @param firstName user's first name
     * @return 'data.code ==  200' is represent successful
     */
    @Override
    @PostMapping("/register")
    public ResultData register(String email,String password,String repeatPassword,String lastName,String firstName) {
        log.info("Execute /user/register");
        if (email == null){
            return new ResultData(404,"email is null.");
        }

        if (password == null | password.length()<8){
            return new ResultData(404,"password is null or length <= 8.");
        }
        if (lastName == null){
            return new ResultData(404,"lastName is null.");
        }
        if (firstName == null){
            return new ResultData(404,"firstName is null.");
        }
        if (!password.equals(repeatPassword)){
            return new ResultData(404,"Password inconsistency");
        }
        else {
            User user = new User();
            user.setPassword(password);
            user.setEmail(email);
            user.setFirstName(firstName);
            user.setRole("user");
            user.setLastName(lastName);
            return userService.register(user);
        }

    }

    /**
     * The user applies to resend the activation code.
     * @param email
     * @return
     */
    @Override
    @PostMapping("/user/resendActiveCode")
    public ResultData resendActiveCode(String email) {
        log.info("resendActiveCode");
        if (email!=null){
            return userService.resendCode(email);
        }
        return new ResultData(404,"Failed");
    }

    /**
     * get user personal information
     * @return if code is 200 represent successful.
     */
    @Override
    @GetMapping("/getUserInfo")
    public ResultData getUserInfo() {
        log.info("getUserInfo");
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user == null){
            return new ResultData(404,"you need login...");
        }
        //check password;
        Optional<User> optionalUser = userMapper.findById(user.getEmail());
        User result = optionalUser.get();
        if (result == null){
            return new ResultData(404,"unsuccessfully get user from database...");
        }
        if (user.getPassword().equals(result.getPassword())) {
            result.setPassword("Password is encrypted");
            return new ResultData(result,200,"ok");
        }else {
            return new ResultData(404,"The password incorrect...");
        }


    }

    /**
     * data format:
     * raw format and Json format
     * {
     * "email":"linzy9553@hotmail.com",
     * "lastName":"123"
     * }
     * @param user user object.
     * @return if code ==200 , change is ok, else change have something wrong.
     */
    @Override
    @PostMapping("/editUserInfo")
    public ResultData editUserInfo(@RequestBody User user) {
        log.info(user.toString());
        User realUser = (User) SecurityUtils.getSubject().getPrincipal();
        //check user info
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        if (firstName != null){
            realUser.setFirstName(firstName);
        }
        if (lastName != null){
            realUser.setLastName(user.getLastName());
        }
        ResultData resultData = userService.editUserInfo(realUser);
        if (resultData.getCode() == 200){
            //update the shiro principle.
            login(realUser.getEmail(),realUser.getPassword());
        }else {
            return new ResultData(404,"The operation of editing is not successful.");
        }


        return new ResultData(200,"The editing is completed.");
    }

    /**
     * The method will achieve activating an account.
     * @param email email address
     * @param activeCode the activation code from the email box.
     * @return
     */
    @Override
    @RequestMapping("/activeCode")
    public ResultData activeAccount(@PathParam("email") String email,@PathParam("activeCode") String activeCode) {
        log.info("activeCode");
        if (activeCode!=null&&email!=null){
            return userService.activeAccount(email,activeCode);
        }
        return new ResultData(404,"The parameters are wrong.");
    }

    /**
     * The method can achieve a login function.
     * @param email email address.
     * @param password the user's password.
     * @return
     */
    @Override
    @PostMapping(value = "/login")
    public ResultData login(String email,String password) {
        log.info("Execute the user login method.");
        log.info("email:" + email);
        log.info("password:" + password);
        if (email == null){
            return  new ResultData(400,"email is null");
        }
        if (password == null){
            return  new ResultData(400,"password is null");
        }
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(email,password);
        try{
            subject.login(token);
        }catch (UnknownAccountException ue){
            return new ResultData(404,"User does not exist.");
        }catch (IncorrectCredentialsException e){
            return new ResultData(404,"Incorrect password");
        }
        return new ResultData(200,"login success");
    }


    @Override
    @PostMapping("/changePasswordByOldPassword")
    public ResultData changePasswordByOldPassword(String oldPassword, String newPassword,String repeatNewPassword) {
        //get user email
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if (user != null){
            oldPassword = MD5EncryptionUtil.encryptPassword(user.getEmail(), oldPassword);
            if (!oldPassword.equals(user.getPassword())){
                return new ResultData(404,"The user old password is incorrect.");
            }
            if (newPassword.equals(repeatNewPassword)){
                if (newPassword.length()<8){
                    return new ResultData(404,"The user password length < 8");
                }
                String password = MD5EncryptionUtil.encryptPassword(user.getEmail(), newPassword);
                user.setPassword(password);
                userMapper.save(user);
                return  new ResultData(200,"Change user password successfully.");
            }else {
                return new ResultData(404,"New password and duplicate password do not match.");
            }
        }else {
            return new ResultData(404,"user is no login.");
        }

    }

    /**
     * This method can change the password of the user by code from user's email box sends by server .
     * @param code code from server.
     * @param email email address
     * @param newPassword
     * @param repeatPassword
     * @return
     */
    @Override
    @PostMapping("/changePasswordByEmailCode")
    public ResultData changePasswordByEmailCode(String code, String email, String newPassword, String repeatPassword) {
        //从Redis获取发送的code
        //检查密码是否有值
        //传送到逻辑处理
        User user = new User();
        if (code == null){
            return new ResultData(404,"The code is null.");
        }
        user.setEmail(email);
        user.setPassword(newPassword);
        if (!newPassword.equals(repeatPassword)){
            return new ResultData(404,"The new password is not the same as the duplicate password");
        }
        if (newPassword.length()<8){
            return new ResultData(404,"The new password length < 8 .");
        }
        //检查对应的Code
        ValueOperations<String,String> opsForValue = redisTemplate.opsForValue();
        String realCode = opsForValue.get(email);
        if (code.equals(realCode)){
            log.info("change the password according to the email code.");
            return userService.changePasswordByEmailCode(user);
        }
        return new ResultData(404,"The password changes failed...");
    }

    @Override
    @PostMapping("/sendCode")
    public ResultData sendCode(String email) {
        //check email
        if (email == null){
            return new ResultData(404,"The Email is incorrect.");
        }
        return userService.sendCode(email);
    }

    /**
     * The method achieves the account log out.
     * @return
     */
    @Override
    @PutMapping("/logout")
    public ResultData logOut() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return new ResultData(200,"Successfully");
    }
}
