package com.ncl.team3.util;

import com.ncl.team3.models.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.stereotype.Service;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/04/08 12:26:50
 */
@Service
public class MD5EncryptionUtil {
    public User encryptUser(User user){
        String hashAlgorithmName = "MD5";
        Object credentials = user.getPassword();
        Object salt = user.getEmail();
        int hashIteration = 1024;
        Object result = new SimpleHash(hashAlgorithmName,credentials,salt,hashIteration);
        user.setPassword(result.toString());
        return user;
    }
    public static String encryptPassword(String email , String originalPassword){
        String hashAlgorithmName = "MD5";
        Object credentials = originalPassword;
        Object salt = email;
        int hashIteration = 1024;
        Object result = new SimpleHash(hashAlgorithmName,credentials,salt,hashIteration);
        return result.toString();
    }
}
