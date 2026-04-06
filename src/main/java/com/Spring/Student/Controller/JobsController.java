package com.Spring.Student.Controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Spring.Student.Jobs.Jobs;
import com.Spring.Student.Services.JobsServices;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/jobs")
public class JobsController {
	private JobsServices services;
	@Autowired
	private Token tokenService;
	public JobsController(JobsServices services) {
		this.services = services;
	}

	@GetMapping()
	public ResponseEntity<?> getJobs(){
		return ResponseEntity.ok(services.getJobs());
	}
	
	@GetMapping("/allJobs")
	public ResponseEntity<?> getAllJob(){
		return ResponseEntity.ok(services.getAllJobs());
	}
	
	@GetMapping("/jobDetail/{id}")
	public ResponseEntity<?> getJobDetail(@PathVariable String id){
		return ResponseEntity.ok(services.getjobDetail(id));
	}
	
	@PostMapping("/addJob")
	public ResponseEntity<?> addJob(@RequestBody Jobs job ,HttpServletRequest request){
		
		String adminToken = tokenService.getToken(request);
		if(adminToken==null) {
			ResponseEntity.status(401).body("Unauthorized");
		}
		try {
			Claims claim = tokenService.validateToken(adminToken);
			
			
			String result = services.addJob(job);
			if(result.equals("Unable to Make Job ID")) {
				return ResponseEntity.status(400).body(Map.of("message", services.addJob(job)));
			}
			return ResponseEntity.ok(Map.of("message", "Job Added to Loppy Job's"));
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid Token");
		}
		
		
	}
	
	@GetMapping("/search")
	public ResponseEntity<?> searchJobs(@RequestParam String role,@RequestParam String location){
		if(role == null ||location==null) {
			return ResponseEntity.ok(services.getAllJobs());
		}
		return ResponseEntity.ok(services.getSearchJobs(role,location));
	}
}
