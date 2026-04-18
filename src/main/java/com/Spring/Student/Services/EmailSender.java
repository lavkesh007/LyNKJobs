package com.Spring.Student.Services;

import java.util.List;
import java.util.stream.Collectors;

import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
    public void emailSender(String companyName, String role, String url) {

        List<String> emails = userRepo.findAll()
                .stream()
                .map(UserRegister::getUserEmail)
                .filter(email -> email != null && !email.isEmpty())
                .collect(Collectors.toList());

        if (emails.isEmpty()) {
            System.out.println("No users found to send emails.");
            return;
        }

        for (String email : emails) {

            int attempts = 0;
            boolean sent = false;

            while (attempts < 3 && !sent) {
                try {
                    System.out.println("Sending mail to: " + email);

                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);

                    helper.setFrom("yourgmail@gmail.com", "LyNK Jobs");
                    helper.setTo(email);
                    helper.setSubject("New Job Update from LyNK Jobs");

                    // ✅ HTML content
                    String htmlContent =
                            "<html>" +
                            "<body style='font-family: Arial, sans-serif; background-color: #f4f6f8; padding: 20px;'>" +

                            "<div style='max-width: 600px; margin: auto; background: #ffffff; border-radius: 10px; padding: 20px;'>" +

                            "<h2 style='color: #2c3e50;'>New Job Update from LyNK Jobs</h2>" +

                            "<p style='font-size: 15px; color: #555;'>Hi,</p>" +

                            "<p style='font-size: 15px; color: #555;'>A new job matching your interest has been posted.</p>" +

                            "<div style='background: #f1f8ff; padding: 15px; border-radius: 8px; margin: 20px 0;'>" +
                            "<p><strong>Company:</strong> " + companyName + "</p>" +
                            "<p><strong>Role:</strong> " + role + "</p>" +
                            "</div>" +

                            "<div style='text-align: center; margin: 30px 0;'>" +
                            "<a href='" + url + "' style='background: #007bff; color: #ffffff; padding: 12px 25px; text-decoration: none; border-radius: 5px;'>View Job</a>" +
                            "</div>" +

                            "<p style='font-size: 14px; color: #555;'>Visit the link above to apply.</p>" +

                            "<hr/>" +

                            "<p style='font-size: 13px; color: #999;'>You are receiving this email because you registered on LyNK Jobs.</p>" +

                            "<p style='font-size: 13px; color: #999;'>Best regards,<br><strong>Team LyNK Jobs</strong></p>" +

                            "<p style='font-size:12px;color:gray'>" +
                            "If you no longer wish to receive these emails, " +
                            "<a href='https://jobslynk.in/unsubscribe'>unsubscribe here</a>." +
                            "</p>" +

                            "</div>" +
                            "</body>" +
                            "</html>";

                    helper.setText(htmlContent, true);

                    // ✅ Send
                    mailSender.send(message);

                    System.out.println("Mail sent to: " + email + " ✅");

                    sent = true;

                    // ✅ delay to avoid spam block
                    Thread.sleep(2000);

                } catch (Exception e) {
                    attempts++;
                    System.out.println("Retry " + attempts + " for: " + email);

                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }

            if (!sent) {
                System.out.println("Failed after retries: " + email);
            }
        }
    }
    
    @Async
    public void AdminMailSender(String Subject,String msg) {
    	List<String> emails = userRepo.findAll()
                .stream()
                .map(UserRegister::getUserEmail)
                .filter(email -> email != null && !email.isEmpty())
                .collect(Collectors.toList());
    	for (String email : emails) {

            int attempts = 0;
            boolean sent = false;

            while (attempts < 3 && !sent) {
                try {
                    System.out.println("Sending mail to: " + email);

                    MimeMessage message = mailSender.createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(message, true);

                    helper.setFrom("yourgmail@gmail.com", "LyNK Jobs");
                    helper.setTo(email);
                    helper.setSubject(Subject);

                 // ✅ HTML content for Admin Mail
                    String htmlContent =
                            "<html>" +
                            "<body style='font-family: Arial, sans-serif; background-color: #f4f6f8; padding: 20px;'>" +

                            "<div style='max-width: 600px; margin: auto; background: #ffffff; border-radius: 10px; padding: 25px; box-shadow: 0 2px 8px rgba(0,0,0,0.05);'>" +

                            // Header
                            "<h2 style='color: #2c3e50; text-align:center;'>LyNK Jobs Notification</h2>" +

                            "<p style='font-size: 15px; color: #555;'>Hello,</p>" +

                            "<p style='font-size: 15px; color: #555;'>We have an update for you:</p>" +

                            // Message Box
                            "<div style='background: #f1f8ff; padding: 15px; border-radius: 8px; margin: 20px 0; color:#333; font-size:14px;'>" +
                            msg +
                            "</div>" +

                            // Button
                            "<div style='text-align: center; margin: 30px 0;'>" +
                            "<a href='https://jobslynk.in' style='background: #007bff; color: #ffffff; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold;'>Visit LyNK Jobs</a>" +
                            "</div>" +

                            "<p style='font-size: 14px; color: #555;'>Stay connected with us for more updates.</p>" +

                            "<hr style='margin:20px 0;'/>" +

                            // Footer
                            "<p style='font-size: 13px; color: #999;'>You are receiving this email because you are registered on LyNK Jobs.</p>" +

                            "<p style='font-size: 13px; color: #999;'>Best regards,<br><strong>Team LyNK Jobs</strong></p>" +

                            "<p style='font-size:12px;color:gray'>" +
                            "If you no longer wish to receive these emails, " +
                            "<a href='https://jobslynk.in/unsubscribe' style='color:#007bff;'>unsubscribe here</a>." +
                            "</p>" +

                            "</div>" +
                            "</body>" +
                            "</html>";

                    helper.setText(htmlContent, true);

                    // ✅ Send
                    mailSender.send(message);

                    System.out.println("Mail sent to: " + email + " ✅");

                    sent = true;

                    // ✅ delay to avoid spam block
                    Thread.sleep(2000);
                    
                } catch (Exception e) {
                    attempts++;
                    System.out.println("Retry " + attempts + " for: " + email);

                    try {
                        Thread.sleep(2000);
                       
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
    	
    }
    	
    }
}