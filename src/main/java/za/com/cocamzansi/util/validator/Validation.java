package za.com.cocamzansi.util.validator;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static boolean isValidEmailAddress(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
    public static boolean isValidPhoneNumber(String s)
    {         //number to validate
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber usNumberProto = phoneUtil.parse(s, "ZA");            //with default country
            boolean isValid = phoneUtil.isValidNumber(usNumberProto);                  //returns true
            String usNumber = phoneUtil.format(usNumberProto, PhoneNumberUtil.PhoneNumberFormat.E164); //+12025550100
            return isValid;
        } catch (NumberParseException e) {
            System.err.println("NumberParseException was thrown: " + e.toString());
            return false;
        }
    }
}
