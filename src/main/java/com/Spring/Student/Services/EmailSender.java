package com.Spring.Student.Services;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.Spring.Student.Repository.UserRepo;
import com.Spring.Student.UserModel.UserRegister;

@Service
public class EmailSender {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JavaMailSender mailSender;

    @Async
    public void emailSender(String companyName, String role) {

        List<String> emails = userRepo.findAll()
                .stream()
                .map(UserRegister::getUserEmail)
                .filter(email -> email != null && !email.isEmpty())
                .collect(Collectors.toList());

        for (String email : emails) {

            // ✅ Create new object every time
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(email);
            message.setSubject("New Job Opportunity Added on LyNK Jobs!");
            message.setText(
                    "Hi,\n\n" +
                    "A new job has been posted at " + companyName + " for the role of " + role + ".\n\n" +
                    "Log in to LyNK Jobs and apply now!\n\n" +
                    "Best regards,\nTeam LyNK Jobs"
            );

            mailSender.send(message);
        }
    }
}