package com.example.Final.config;

import com.example.Final.Service.ReminderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class ReminderScheduler {
    @Autowired
    private ReminderService reminderService;

    @Scheduled(cron = "0 0 9 * * *") // Runs everyday at 9:00 AM
    public void sendReminder() {
        reminderService.sendReminderEmails();
    }
}
