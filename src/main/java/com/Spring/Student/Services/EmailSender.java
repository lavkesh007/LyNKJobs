package com.Spring.Student.Services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.Spring.Student.Repository.UserRepo;
import com.Spring.Student.UserModel.UserRegister;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

@Service
public class EmailSender {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SendGrid sendGrid;

    private final String FROM_EMAIL = "lynkjobs09@gmail.com";
    private final String FROM_NAME = "LyNK Jobs";

    @Async
    public void emailSender(String companyName, String role, String url) {

        List<String> emails = userRepo.findAll()
                .stream()
                .map(UserRegister::getUserEmail)
                .filter(email -> email != null && !email.isEmpty())
                .collect(Collectors.toList());

        for (String email : emails) {
            try {
                System.out.println("Sending mail to: " + email);

                Email from = new Email(FROM_EMAIL, FROM_NAME);
                Email to = new Email(email);

                String subject = "New Job Opportunity Added on LyNK Jobs!";

                Content content = new Content(
                        "text/plain",
                        "Hi,\n\n" +
                        "We’re excited to inform you that a new job opportunity has just been posted on LyNK Jobs!\n\n" +
                        "🏢 Company: " + companyName + "\n" +
                        "💼 Role: " + role + "\n" +
                        "Apply here: " + url + "\n\n" +
                        "Don’t miss out on this opportunity!\n\n" +
                        "Best regards,\nTeam LyNK Jobs"
                );

                Mail mail = new Mail(from, subject, to, content);

                Request request = new Request();
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());

                Response response = sendGrid.api(request);

                System.out.println("Status for " + email + ": " + response.getStatusCode());

            } catch (Exception e) {
                System.out.println("Failed to send to: " + email);
                e.printStackTrace();
            }
        }
    }
}