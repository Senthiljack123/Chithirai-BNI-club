package com.example.Final.model;

import com.example.Final.Dto.MailBody;
import com.example.Final.model.User;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPassword {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer fbid;
    @Column(nullable = false)
    private Integer otp;
    @Column(nullable = false)
    private Date expireTime;
    @OneToOne
    private User user;

}
