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

    private final String FROM_EMAIL = "no-reply@jobslynk.in";
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
                	    "text/html",
                	    "<html>" +
                	    "<body style='font-family: Arial, sans-serif; background-color: #f4f6f8; padding: 20px;'>" +

                	    "<div style='max-width: 600px; margin: auto; background: #ffffff; border-radius: 10px; padding: 20px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);'>" +

                	    "<h2 style='color: #2c3e50;'>🚀 New Job Opportunity on LyNK Jobs</h2>" +

                	    "<p style='font-size: 15px; color: #555;'>Hi there,</p>" +

                	    "<p style='font-size: 15px; color: #555;'>We found a job opportunity that might be a great match for your profile. Don't miss out!</p>" +

                	    "<div style='background: #f1f8ff; padding: 15px; border-radius: 8px; margin: 20px 0;'>" +
                	    "<p style='margin: 5px 0;'><strong>🏢 Company:</strong> " + companyName + "</p>" +
                	    "<p style='margin: 5px 0;'><strong>💼 Role:</strong> " + role + "</p>" +
                	    "</div>" +

                	    "<div style='text-align: center; margin: 30px 0;'>" +
                	    "<a href='" + url + "' style='background: #007bff; color: #ffffff; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-size: 16px;'>Apply Now</a>" +
                	    "</div>" +

                	    "<p style='font-size: 14px; color: #555;'>This could be your next big career move. Apply now before the position gets filled!</p>" +

                	    "<hr style='border: none; border-top: 1px solid #eee;'/>" +

                	    "<p style='font-size: 13px; color: #999;'>You are receiving this email because you registered on LyNK Jobs.</p>" +

                	    "<p style='font-size: 13px; color: #999;'>Best regards,<br><strong>Team LyNK Jobs</strong></p>" +

                	    "</div>" +

                	    "</body>" +
                	    "</html>"
                	);

                Mail mail = new Mail(from, subject, to, content);

                Request request = new Request();
                request.setMethod(Method.POST);
                request.setEndpoint("mail/send");
                request.setBody(mail.build());

                Response response = sendGrid.api(request);

                System.out.println("Status for " + email + ": " + response.getStatusCode());
                Thread.sleep(1200);
            } catch (Exception e) {
                System.out.println("Failed to send to: " + email);
                e.printStackTrace();
            }
        }
    }
}