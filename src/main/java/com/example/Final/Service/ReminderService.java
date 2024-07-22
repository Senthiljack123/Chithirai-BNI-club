package com.example.Final.Service;

import com.example.Final.Repo.EventRepository;
import com.example.Final.model.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReminderService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private JavaMailSender emailSender;

    public void sendReminderEmails() {
        LocalDate today = LocalDate.now().plusDays(1);
         List<Event> events = eventRepository.findByEventDate(today);
         String em = "kumardeva3578@gmail.com";
        for (Event event : events) {
            sendEmail(em, "Reminder: Your Event Today", "Don't forget about your event today: " + event.getName());
        }
    }

    private void sendEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        emailSender.send(message);
    }
}

