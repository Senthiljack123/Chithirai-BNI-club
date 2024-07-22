package com.example.Final.controller;

import com.example.Final.Dto.MailBody;
import com.example.Final.Service.EmailService;
import com.example.Final.Service.EventService;
import com.example.Final.Service.ReminderService;
import com.example.Final.Service.SmsService;
import com.example.Final.config.ApiResponse;
import com.example.Final.model.Event;
import com.example.Final.model.EventRegistration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.ZoneId;


@RestController
@RequestMapping("/event")
public class ReminderController {

    @Autowired
    private ReminderService reminderService;

    @GetMapping("/send")
    public ResponseEntity<String> sendReminders() {
        reminderService.sendReminderEmails();
        return new ResponseEntity<>("Reminder emails sent successfully.", HttpStatus.OK);
    }
}

