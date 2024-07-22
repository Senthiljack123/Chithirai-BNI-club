package com.example.Final.controller;
// DonationController.java
import com.example.Final.Service.DonationService;
import com.example.Final.config.ApiResponse;
import com.example.Final.model.Donation;
import com.example.Final.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class DonationController {
    private final DonationService donationService;
    private Donation donationDetails;
    @Autowired
    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping("/personal-details")
    public ResponseEntity<?> createDonation(@RequestBody Donation donation) {
        if (donation.getFirstName() == null || donation.getFirstName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a Firstname", null));
        }

        if (donation.getLastName() == null || donation.getLastName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a Lastname", null));
        }
        if (donation.getEmail() == null || donation.getEmail().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter an email", null));
        }
        if (donation.getPhoneNumber() == null || donation.getPhoneNumber().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter an phnumber", null));
        }
        if (donation.getPan() == null || donation.getPan().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter an pan number", null));
        }
        donationDetails = donation;

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "personal  details captured", null));
     }

    @PostMapping("/billing-address")
    public ResponseEntity<?> captureBillingAddress(@RequestBody Donation donation) {
        if (donation.getAddressLine1() == null || donation.getAddressLine1().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter Address Line 1", null));
        }
        if (donation.getAddressLine2() == null || donation.getAddressLine2().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter Address Line 2", null));
        }

        if (donation.getCity() == null || donation.getCity().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a City", null));
        }

        if (donation.getState() == null || donation.getState().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a State", null));
        }

        if (donation.getPincode() == null || donation.getPincode().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please enter a Pincode", null));
        }
        update(donation);


        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "Billing address details captured", null));
    }
    public void update(Donation donation){
        donationDetails.setAddressLine1(donation.getAddressLine1());
        donationDetails.setAddressLine2(donation.getAddressLine2());
        donationDetails.setCity(donation.getCity());
        donationDetails.setState(donation.getState());
        donationDetails.setPincode(donation.getPincode());
    }
    @PostMapping("/club-selection")
    public ResponseEntity<?> selectClub(@RequestBody Donation donation) {
        if (donation.getClubType() == null || donation.getClubType().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse(false, "Please provide an club type", null));
        }
        donationDetails.setClubType(donation.getClubType());
        donationService.saveDonation(donationDetails);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(true, "details saved", null));

    }

    @GetMapping("/google-pay")
    public String redirectToGooglePay() {
        // Construct the URL for Google Pay
        String googlePayUrl = "https://pay.google.com/gp/v/pubtrans/pay";

        // Redirect the user to Google Pay
        return "redirect:" + googlePayUrl;
    }

 }
