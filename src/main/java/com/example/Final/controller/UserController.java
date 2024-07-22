package com.example.Final.controller;

import com.example.Final.Dto.*;
import com.example.Final.Repo.Forgotpasswordrepo;
import com.example.Final.Repo.UserRepository;
import com.example.Final.Service.EmailService;
import com.example.Final.Service.SmsService;
import com.example.Final.Service.UserService;
import com.example.Final.config.ApiResponse;
import com.example.Final.model.ForgotPassword;
import com.example.Final.model.Password;
import com.example.Final.model.User;
import com.example.Final.util.ChangePassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;
    @Autowired
    Forgotpasswordrepo forgotpasswordrepo;
    @Autowired
    UserRepository userRepository;
    String email;
    private Map<String, User> signUpDetails = new HashMap<>();
    @PostMapping("/user/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {

        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Email already exists", null));
        }

        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a Firstname", null));
        }

        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a Lastname", null));
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter an email", null));
        }

        if (user.getAge() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a valid age", null));
        }

        if (user.getCountry() == null || user.getCountry().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a country", null));
        }

        if (user.getCity() == null || user.getCity().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a city", null));
        }

        if (user.getState() == null || user.getState().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a state", null));
        }

        if (user.getPostalCode() == null || user.getPostalCode().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a postal code", null));
        }

        if (user.getPhNum() == null || user.getPhNum().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a phone number", null));
        }

        email = user.getEmail();
        signUpDetails.put(user.getEmail(), user);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "sign up details captured", null));
    }
    int otp;
    @PostMapping("/user/signup/password")
    public ResponseEntity<?> capturePassword(@RequestBody Password password) {
        User user = signUpDetails.get(email);

        if (user == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "User details not found", null));
        }

        // Validate password and confirmation password
        if (!password.getPassword().equals(password.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Passwords do not match", null));
        }
        // Add password to user details
        user.setPassword(password.getPassword());
        otp = otpGen();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("this is the OTp for a email verficattion " + otp)
                .subject("OTP set password").build();
        emailService.sendSimpleMessage(mailBody);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "otp send sucessfully", null));
    }


    @PostMapping("/resendotp")
    public ResponseEntity<?> resendOtp(){
        otp = otpGen();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("this is the OTp for a email verficattion " + otp)
                .subject("OTP set password").build();
        emailService.sendSimpleMessage(mailBody);
            return ResponseEntity.ok(new ApiResponse(true, "otp send sucessfully", null));


    }

        @PostMapping("/verifyOtp")
        public ResponseEntity<?> sendOTP(@RequestBody OTP otpes) {

            User user = signUpDetails.get(email);
            String otpString = Integer.toString(otp);
            String n = otpes.getOtp();

            if(otpString.equals(otpes.getOtp())) {
                userRepository.save(user);
                signUpDetails.remove(email);
                return ResponseEntity.ok(new ApiResponse(true, "user register sucess", null));
            }
            else{
                return ResponseEntity.ok(new ApiResponse(true, "hit resend otp", null));

            }

        }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        User existingUser = userService.findByEmail(user.getEmail());

        if (existingUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Enter Valid Email", null));
        }

        if (!user.getPassword().equals(existingUser.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Incorrect Password", null));
        }

        return ResponseEntity.ok(new ApiResponse(true, "Login successful", null));

    }

     int fotp;
     String mail;
    @PostMapping("/forgotpassword")
    public ResponseEntity<?> forgotPassword(@RequestBody Emails email) {

        User user  = userRepository.findByEmail(email.getEmail());
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "pls provide valid mail", null));
        }
        mail = email.getEmail();
        fotp = otpGen();
        MailBody mailBody = MailBody.builder()
                .to(email.getEmail())
                .text("this is the OTp for a email verficattion   " + fotp)
                .subject("OTP forgot password").build();
        ForgotPassword fb = ForgotPassword.builder()
                .otp(otp)
                .expireTime(new Date(System.currentTimeMillis() + 70 * 1000))
                .user(user).build();
        emailService.sendSimpleMessage(mailBody);
        forgotpasswordrepo.save(fb);

        return ResponseEntity.ok("Email send for a verfiaction");

    }

    @PostMapping("/verifyOtps")
    public ResponseEntity<?> verifyOtp(@RequestBody OTP otpes){
        String otpString = Integer.toString(fotp);
        if(otpString.equals(otpes.getOtp())) {
            return ResponseEntity.ok(new ApiResponse(true, "otp verify sucess", null));
        }
        else{
            return ResponseEntity.ok(new ApiResponse(true, "hit resend otp", null));

        }

    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword){
        if(!Objects.equals(changePassword.password(), changePassword.rebeatpassword())){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ApiResponse(false, "pls enter password again", null));
        }
        String encodedPassword  =  changePassword.password();
        userRepository.updatePassword(mail,encodedPassword);
        return ResponseEntity.ok("Password has been changed");
    }

    @Autowired
    private SmsService smsService;
    @PostMapping("/forgotpassword-phone")
    public ResponseEntity<?> sendOtp(@RequestBody OtpRequest otpRequest) {
        smsService.sendSMS(otpRequest);
        return ResponseEntity.ok(new ApiResponse(true, "otp send success", null));

    }

    @PostMapping("/validate-otp")
    public ResponseEntity<?> validateOtp(@RequestBody OtpValidationRequest otpValidationRequest) {
         boolean b = smsService.validateOtp(otpValidationRequest);
         if(b) {

             return ResponseEntity.ok(new ApiResponse(true, "otp validate success hit change password", null));
         }
         else {
             return ResponseEntity.ok(new ApiResponse(true, "hit resend otp  ", null));
         }

    }

    @PostMapping("/changePasswordPhone")
    public ResponseEntity<?> changePasswordPhone(@RequestBody ChangePassword changePassword) {
        if(!Objects.equals(changePassword.password(), changePassword.rebeatpassword())){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ApiResponse(false, "pls enter password again", null));
        }
        String encodedPassword  =  changePassword.password();
        userRepository.updatePassword(mail,encodedPassword);
        return ResponseEntity.ok("Password has been changed");
    }


    private Integer otpGen(){
        int min = 1000;
        int max = 9999;
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }



    @PostMapping("/admin/signup")
    public ResponseEntity<?> adminSignUp(@RequestBody User admin) {
        // Check if the email already exists
        if (userService.findByEmail(admin.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Email already exists", null));
        }

        if (admin.getFirstName() == null || admin.getFirstName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a Firstname", null));
        }

        if (admin.getLastName() == null || admin.getLastName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a Lastname", null));
        }

        if (admin.getEmail() == null || admin.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter an email", null));
        }

        if (admin.getAge() <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a valid age", null));
        }

        if (admin.getCountry() == null || admin.getCountry().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a country", null));
        }

        if (admin.getCity() == null || admin.getCity().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a city", null));
        }

        if (admin.getState() == null || admin.getState().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a state", null));
        }

        if (admin.getPostalCode() == null || admin.getPostalCode().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a postal code", null));
        }

        if (admin.getPhNum() == null || admin.getPhNum().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a phone number", null));
        }

        // Sign up the admin
        userService.signUp(admin);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Admin registered successfully", null));
    }

    @PostMapping("/admin/login")
    public ResponseEntity<?> adminLogin(@RequestBody User admin) {
        // Find the admin by email
        User existingAdmin = userService.findByEmail(admin.getEmail());

        // Check if admin exists
        if (existingAdmin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "Invalid credentials", null));
        }

        return ResponseEntity.ok(new ApiResponse(true, "Admin login successful", null));
    }

   /* @PostMapping("/changePassword/{email}")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassword changePassword, @PathVariable String email){
        if(!Objects.equals(changePassword.password(), changePassword.rebeatpassword())){
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ApiResponse(false, "pls enter password again", null));
        }

        String encodedPassword  = passwordEncoder.encode(changePassword.password());
        userRepository.updatePassword(email,encodedPassword);
        return ResponseEntity.ok("Password has been changed");
    }*/

    //cart Controller


    /*@PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        if (userService.findByEmail(user.getEmail()) != null) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

      @PostMapping("/verifyMail/{email}")
    public ResponseEntity<?> login(@PathVariable String email) {
        User user  = userRepository.findByEmail(email);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(false, "pls provide valid mail", null));
        }
        int otp = otpGen();
        MailBody mailBody = MailBody.builder()
                .to(email)
                .text("this is the OTp for a email verficattion" + otp)
                .subject("OTP forgot password").build();
        ForgotPassword fb = ForgotPassword.builder()
                .otp(otp)
                .expireTime(new Date(System.currentTimeMillis() + 70 * 1000))
                .user(user).build();
        emailService.sendSimpleMessage(mailBody);
        forgotpasswordrepo.save(fb);
        return ResponseEntity.ok("Email send for a verfiaction");

    }

    */



}