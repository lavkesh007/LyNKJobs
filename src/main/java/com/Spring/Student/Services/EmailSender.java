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
@Async
@Service
public class EmailSender {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JavaMailSender mailSender;

//    @Async
    public void emailSender(String companyName, String role,String url) {

        List<String> emails = userRepo.findAll()
                .stream()
                .map(UserRegister::getUserEmail)
                .filter(email -> email != null && !email.isEmpty())
                .collect(Collectors.toList());

        for (String email : emails) {
        	try {
            // ✅ Create new object every time
	            SimpleMailMessage message = new SimpleMailMessage();
	            message.setFrom("lynkjobs09@gmail.com");
	            System.out.println("Mail sended");
	            message.setTo("patillavkesh763@gmail.com");
	            message.setSubject("New Job Opportunity Added on LyNK Jobs!");
	            message.setText(
	            	    "Hi,\n\n" +
	            	    "We’re excited to inform you that a new job opportunity has just been posted on LyNK Jobs!\n\n" +
	            	    "🏢 Company: " + companyName + "\n" +
	            	    "💼 Role: " + role + "\n\n" +
	            	    "If you’ve been looking for the right opportunity to grow your career, this could be the perfect match for you. At LyNK Jobs, we aim to connect talented individuals like you with companies that are actively hiring and looking for your skills.\n\n" +
	            	    "Don’t miss out on this opportunity! Log in to your LyNK Jobs account today to view complete job details, check eligibility, and submit your application with ease.\n\n" +
	            	    "🔔 Not registered yet? Sign up on LyNK Jobs to receive instant notifications about the latest job openings tailored to your profile. Stay ahead of the competition and never miss an opportunity again.\n\n" +
	            	    "Your next career move is just a click away. Take action now and apply before the position gets filled!\n\n" +
	            	    "Best regards,\n" +
	            	    "Team LyNK Jobs \n" +
	            	    "URL: " + url
	            	    
	            	    
	            	);
	
	            mailSender.send(message);
        	}catch(Exception e) {
        		System.out.println("Failed to send");
        		e.printStackTrace();
        	}
        }
    }
}