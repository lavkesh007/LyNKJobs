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
import com.sendgrid.helpers.mail.objects.Personalization;

@Service
public class EmailSender {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private SendGrid sendGrid;

    // ✅ Use consistent & professional sender
    private final String FROM_EMAIL = "jobs@em3976.jobslynk.in";
    private final String FROM_NAME = "LyNK Jobs";

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

                    Email from = new Email(FROM_EMAIL, FROM_NAME);
                    Email to = new Email(email);

                    String subject = "New Job Update from LyNK Jobs";

                    // ✅ HTML content
                    Content htmlContent = new Content(
                            "text/html",
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
                            "</html>"
                    );

                    // ✅ Plain text (ANTI-SPAM)
                    Content textContent = new Content(
                            "text/plain",
                            "New Job Update from LyNK Jobs\n" +
                            "Company: " + companyName + "\n" +
                            "Role: " + role + "\n" +
                            "Apply here: " + url + "\n\n" +
                            "To unsubscribe visit: https://jobslynk.in/unsubscribe"
                    );

                    // ✅ Mail setup
                    Mail mail = new Mail();
                    mail.setFrom(from);
                    mail.setSubject(subject);

                    // 🔥 IMPORTANT: Reply-To (improves trust & name display)
                    mail.setReplyTo(new Email("support@jobslynk.in", "LyNK Jobs Support"));

                    Personalization personalization = new Personalization();
                    personalization.addTo(to);
                    mail.addPersonalization(personalization);

                    mail.addContent(textContent);
                    mail.addContent(htmlContent);

                    Request request = new Request();
                    request.setMethod(Method.POST);
                    request.setEndpoint("mail/send");
                    request.setBody(mail.build());

                    Response response = sendGrid.api(request);

                    System.out.println("Status for " + email + ": " + response.getStatusCode());

                    sent = true;

                    // ✅ safer delay
                    Thread.sleep(2500);

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
}