package com.Spring.Student.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Spring.Student.Configuration.MCQScheduler;
import com.Spring.Student.Repository.MCQsRepository;
import com.Spring.Student.Services.GeminiService;
//import com.sun.tools.javac.util.List;
import com.Spring.Student.UserModel.Mcqs;
import com.Spring.Student.UserModel.UserRegister;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;


@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/mcqs")
public class MCQController {
	@Autowired
	Token tokenservice;
	 @Autowired
	    private MCQsRepository repository;
	@Autowired
    private GeminiService service;
	@Autowired
	private MCQScheduler scheduler;

	@GetMapping("/{subject}")
    public ResponseEntity<?> getMCQs(HttpServletRequest request,@PathVariable String subject) {
		String token = tokenservice.getToken(request);
		if(token==null) {
			return ResponseEntity.status(401).body("Unauthorized");
		}
		try {
			Claims claim = tokenservice.validateToken(token);
			String userID = claim.get("userID", String.class);
			subject = subject.toLowerCase();
			return ResponseEntity.ok(repository.findBySubjectAndDate(subject, LocalDate.now()));
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid Token");
		}
        // ✅ handle case issue
        

//        return repository.findBySubjectAndDate(subject, LocalDate.now());
    }
	@GetMapping("/generate")
	public String generateNow() {
	    scheduler.generateDailyMCQs();
	    return "MCQs Generated!";
	}
}
