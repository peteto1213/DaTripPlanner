package com.ncl.team3.util;

import com.ncl.team3.exception.WrongCardDetailException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class PaymentFormatUtil {

    public Boolean verifyCardNumber(String cardNumber) throws WrongCardDetailException{
        if (cardNumber == null || cardNumber.length() != 16){
            throw new WrongCardDetailException("invalid card number");
        }else {
            Pattern compile = Pattern.compile("[0-9]{1,16}");
            Matcher matcher = compile.matcher(cardNumber);
            if (!matcher.matches()){
                throw new WrongCardDetailException("invalid card number");
            }
        }
        return true;
    }

    public Boolean verifySecurityCode(String securityCode) throws WrongCardDetailException{
        if (securityCode == null || securityCode.length() != 3){
            throw new WrongCardDetailException("invalid security code");
        }else {
            Pattern compile = Pattern.compile("[0-9]{1,3}");
            Matcher matcher = compile.matcher(securityCode);
            if (!matcher.matches()){
                throw new WrongCardDetailException("invalid security code");
            }
        }
        return true;
    }

    public Boolean verifyExpireDate(String expireDate) throws WrongCardDetailException{
        if (expireDate == null || expireDate.length() != 5){
            throw new WrongCardDetailException("invalid expire date");
        }else {
            Pattern compile = Pattern.compile("[0-9]{1,2}/[0-9]{1,2}");
            Matcher matcher = compile.matcher(expireDate);
            if (!matcher.matches()){
                throw new WrongCardDetailException("invalid expire date");
            }
        }
        return true;
    }

    public Boolean verifyOwnerName(String ownerName) throws WrongCardDetailException {
        if (ownerName == null) {
            throw new WrongCardDetailException("invalid owner name");
        }
        Pattern compile = Pattern.compile("[A-Z][A-Z-\\s]*");
        Matcher matcher = compile.matcher(ownerName);
        if (!matcher.matches()) {
            throw new WrongCardDetailException("invalid owner name");
        }
        return true;
    }
}
