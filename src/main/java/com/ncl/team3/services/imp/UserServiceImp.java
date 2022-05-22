package com.ncl.team3.services.imp;

import com.ncl.team3.mappers.UserMapper;
import com.ncl.team3.models.ResultData;
import com.ncl.team3.models.User;
import com.ncl.team3.services.UserService;
import com.ncl.team3.util.EmailUtil;
import com.ncl.team3.util.MD5EncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * This class provides for the processing of User related information, such as providing user registration,
 * logging in and changing passwords,
 * modifying information and other business requirements.
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/06 13:33:13
 */
@Service
@Slf4j
public class UserServiceImp implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MD5EncryptionUtil encryptionUtil;
    @Autowired
    private EmailUtil emailUtil;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Value("${hostAddress}")
    private String hostAddress;

    /**
     * user register function.
     * provide a function to achieve user login.
     * @param user user object
     * @return if code == 200, it registers successfully.
     *
     */
    @Override
    public ResultData register(User user) {
        //Check if the user already exists
        Optional<User> byId = userMapper.findById(user.getEmail());
        if (byId.isPresent()){
            return new ResultData(404,"Account already exists.");
        }
        user = encryptionUtil.encryptUser(user);
        user.setStatus("inactive");
        //send active code to user's email
        UUID activeCode = UUID.randomUUID();
        redisTemplate.opsForValue().set("activeCode"+user.getEmail(), String.valueOf(activeCode),600,TimeUnit.SECONDS);
        String email = user.getEmail();
        String subject = "New Active Code";
        String content = "http://"+hostAddress+"/user/activeCode?email="+email+"&activeCode="+activeCode;
        try{
            emailUtil.sendSimpleMail(email,subject,content);
        }catch (Exception e){
            log.error(e.getMessage());
            return new ResultData(404,"register code send failed.");
        }
        userMapper.save(user);
        return new ResultData(200,"register successfully.");
    }

    /**
     * This function provides the possibility to forget the original password in case the user forgets it
     * @param user user object, need have the password.
     * @return if code == 200, it changes successfully.
     */
    @Override
    public ResultData changePasswordByEmailCode(User user) {
        String password = user.getPassword();
        if (password.length()<8){
            return new ResultData(404,"The password word length > 8 ");
        }
        User userFromDB = userMapper.getById(user.getEmail());
        userFromDB.setPassword(password);
        userFromDB = encryptionUtil.encryptUser(userFromDB);
        userMapper.save(userFromDB);
        return new ResultData(200,"Change password successfully.");
    }

    /**
     * send active code to user email  box for user change the password.
     * @param email email account, need real email address.
     * @return if code == 200, it sends successfully.
     */
    @Override
    public ResultData sendCode(String email) {
        Optional<User> byId = userMapper.findById(email);
        if (!byId.isPresent()){
            return new ResultData(404,"User does not exist.");
        }
        ValueOperations<String,String> opsForValue = redisTemplate.opsForValue();
        //generate a new code
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        //save generate code to the redis.
        opsForValue.set(email,code,60000, TimeUnit.SECONDS);
        //send code to the user by email;
        String subject = "Change your password.";
        String content = "Hello, \n" +
                "This is a new code for you to change your password.\n" +
                "new Code is "+code;
        try {
            emailUtil.sendSimpleMail(email,subject,content);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResultData(500,"send email failed...");
        }

        return new ResultData(200,"A new verification code has been sent");
    }

    /**
     * When user register successfully, the status is inactive.
     * The user need by this function to activate their account.
     * @param email email address.
     * @param activeCode the active code parses from email link.
     * @return if successfully return code == 200.
     */
    @Override
    public ResultData activeAccount(String email, String activeCode) {
        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String key = "activeCode"+email;
        String code = operations.get(key);
        if (!activeCode.equals(code)){
            return new ResultData(404,"Active Code is incorrect");
        }
        User user = userMapper.getById(email);
        user.setStatus("active");
        userMapper.save(user);
        return new ResultData(200,"Account has been activated.");
    }

    /**
     * This is backup function.
     * @param email
     * @return
     */
    @Override
    public ResultData login(String email) {

        return null;
    }

    /**
     * This method is for situation when the email send failed.
     * @param email user email
     * @return
     */
    @Override
    public ResultData resendCode(String email) {
        Optional<User> optionalUser = userMapper.findById(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (!user.getStatus().equals("inactive")){
                return new ResultData(404,"The user's status not is inactive.Please use register function.");
            }
            try{
                UUID activeCode = UUID.randomUUID();
                redisTemplate.opsForValue().set("activeCode"+user.getEmail(), String.valueOf(activeCode),600,TimeUnit.SECONDS);
                String subject = "New Active Code";
                String content = "http://"+hostAddress+"/user/activeCode?email="+email+"&activeCode="+activeCode;
                emailUtil.sendSimpleMail(email,subject,content);
            }catch (Exception e){
                e.printStackTrace();
                return new ResultData(500,"You need a real email for register");
            }

            return new ResultData(200,"A new activation link has been sent.");
        }
        return new ResultData(404,"Unknown exceptions.");
    }

    /**
     * Provide the method for editing user info.
     * @param realUser user object after modifying.
     * @return code == 200
     */
    @Override
    public ResultData editUserInfo(User realUser) {
        User save = userMapper.save(realUser);
        return new ResultData(save,200,"ok");
    }
}
