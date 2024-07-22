package com.example.Final.Service;

import com.example.Final.Dto.MailBody;
import com.example.Final.model.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String sendSimpleMessage(MailBody mailBody) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom("kumardeva3578@gmail.com");
            msg.setTo(mailBody.to());
            msg.setSubject(mailBody.subject());
            msg.setText(mailBody.text());
            javaMailSender.send(msg);

            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }
}