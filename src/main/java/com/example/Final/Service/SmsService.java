package com.example.Final.Service;

import com.example.Final.Dto.OtpRequest;
import com.example.Final.Dto.OtpValidationRequest;
import com.example.Final.config.OtpResponseDto;
import com.example.Final.config.OtpStatus;
import com.example.Final.config.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SmsService {

    @Autowired
    private TwilioConfig twilioConfig;
    Map<String, String> otpMap = new HashMap<>();
    String otp;
    public OtpResponseDto sendSMS(OtpRequest otpRequest) {
        OtpResponseDto otpResponseDto = null;
        try {
            PhoneNumber to = new PhoneNumber(otpRequest.getPhoneNumber());//to
            PhoneNumber from = new PhoneNumber(twilioConfig.getPhoneNumber()); // from
            otp = Integer.toString(otpGen());
            String otpMessage = "Dear Customer , Your OTP is  " + otp + " for sending sms through Spring boot application. Thank You.";
            Message message = Message
                    .creator(to, from,
                            otpMessage)
                    .create();
             otpResponseDto = new OtpResponseDto(OtpStatus.DELIVERED, otpMessage);
        } catch (Exception e) {
            e.printStackTrace();
            otpResponseDto = new OtpResponseDto(OtpStatus.FAILED, e.getMessage());
        }
         return otpResponseDto;
    }

    public boolean validateOtp(OtpValidationRequest otpValidationRequest) {
        Set<String> keys = otpMap.keySet();
        if (otpValidationRequest.getOtpNumber().equals(otp)) {
            return true;
        } else {
            return false;
        }
    }


    private Integer otpGen(){
        int min = 1000;
        int max = 9999;
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }
}
