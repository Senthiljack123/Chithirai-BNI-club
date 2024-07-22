package com.example.Final.Service;

import com.example.Final.Repo.UserRepository;
import com.example.Final.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender emailSender;


    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signUp(User user) {

            /*String encodedPassword = passwordEncoder.encode(user.getPassword());
           // System.out.println("Encoded password: " + encodedPassword); // Debugging
            user.setPassword(encodedPassword);*/
            userRepository.save(user);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);

    }
    public User findByPassword(String password) {
        return userRepository.findByPassword(password);
    }


}



