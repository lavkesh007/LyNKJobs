package com.Spring.Student.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.Spring.Student.Repository.UserRepo;

@Service
@Async
public class EmailSender {
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private JavaMailSender mailSender;
	
	@Async
	public void emailSender(String companyName , String role) {
		SimpleMailMessage message = new SimpleMailMessage();
		List<String> emails  = userRepo.findAlluserEmail();
		for(String email : emails) {
			message.setTo(email);
			message.setSubject("New Job Opportunity Added on LyNK Jobs!");
			message.setText("Hi,\n"
					+ "\n"
					+ "We’re excited to inform you that a new job opportunity has just been added on LyNK Jobs! We continuously strive to bring you the latest and most relevant job listings to support your career growth. We encourage you to log in to your account, explore the new opening, and apply if it matches your skills and interests. Don’t miss out on this opportunity to take the next step in your professional journey.\n"
					+ "\n"
					+ "Best regards,\n"
					+ "Team LyNK Jobs");
			mailSender.send(message);
		}
		
	}
}
