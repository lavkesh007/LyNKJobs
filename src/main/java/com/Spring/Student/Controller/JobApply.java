package com.Spring.Student.Controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Spring.Student.Services.ApplyJobsServices;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/user")
public class JobApply {
	ApplyJobsServices applyService;
	Token tokenService;
	public JobApply(Token tokenService,ApplyJobsServices applyService) {
		super();
		this.tokenService = tokenService;
		this.applyService = applyService;
	}
	@PostMapping("/apply/{jobID}")
	public ResponseEntity<?> addApply(@PathVariable String jobID ,HttpServletRequest request){
		String token = tokenService.getToken(request);
		if(token==null) {
			ResponseEntity.status(401).body("Unauthorized");
		}
		try {
			Claims claim = tokenService.validateToken(token);
			String userID = claim.get("userID",String.class);
			return ResponseEntity.ok(Map.of("message",applyService.applyJob(userID, jobID)));
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid Token");
		}

	}
	
	@GetMapping("allAppliedJobs")
	public ResponseEntity<?> getAllAppliedJobs(HttpServletRequest request){
		String token = tokenService.getToken(request);
		if(token==null) {
			ResponseEntity.status(401).body("Unauthorized");
		}
		try {
			Claims claim = tokenService.validateToken(token);
			String userID = claim.get("userID",String.class);
			
			return ResponseEntity.ok(applyService.getAllJobsIDs(userID));
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid Token");
		}
	}

}
