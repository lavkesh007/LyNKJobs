package com.Spring.Student.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Spring.Student.AdminModel.AdminLoginCredentials;
import com.Spring.Student.DTO.AdminDTO;
import com.Spring.Student.Services.AdminServices;
import com.Spring.Student.Services.EmailSender;
import com.Spring.Student.Services.JobsServices;
import com.Spring.Student.Services.UserServices;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin(origins = "*") 
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	EmailSender emailSender;
	UserServices userService;
	AdminServices services;
	AdminToken tokenservices;
	public AdminController(AdminServices services,AdminToken tokenservice) {
		super();
		this.services = services;
		this.tokenservices = tokenservice;
	}
	@Autowired
	JobsServices jobServices;
	public AdminDTO getDTO(AdminLoginCredentials admin) {
		return new AdminDTO(admin.getAdminID(),admin.getAdminName(),admin.getAdminEmail());
	}
	@PostMapping("/login")
	public ResponseEntity<?> verifyLoginCredentials(@RequestBody AdminLoginCredentials admin){
		String result = services.verifyAdminLogin(admin.getAdminEmail(),admin.getAdminPassword());
		if(result.equals("Admin Not Found")) {
			return ResponseEntity.status(404).body(Map.of("message",result));
		}
		if(result.equals("Invalid Password")) {
			return ResponseEntity.status(401).body(Map.of("message",result));
		}
		AdminLoginCredentials getAdmin = services.getAdmin(admin.getAdminEmail());
		String adminToken = tokenservices.Token(getAdmin.getAdminName(), getAdmin.getAdminID(), null);
		return ResponseEntity.ok(Map.of("message",result,"adminToken",adminToken));
	}
	
	@GetMapping()
	public ResponseEntity<?> getAdmin(HttpServletRequest request){
		String adminToken = tokenservices.getToken(request);
		if(adminToken==null) {
			return ResponseEntity.status(401).body("Unauthorized");
		}
		try {
			Claims claim = tokenservices.validateToken(adminToken);
			String adminID = claim.get("adminID",String.class);
			String adminName = claim.getSubject();
			return ResponseEntity.ok(getDTO(services.getAdminbyID(adminID)));
			
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid Token");
		}
	}
	
	@DeleteMapping("/deleteJob/{jobID}")
	public ResponseEntity<?> deleteJob(HttpServletRequest request,@PathVariable String jobID){
		String adminToken = tokenservices.getToken(request);
		if(adminToken==null) {
			return ResponseEntity.status(401).body(Map.of("message","Unauthorized"));
		}
		try {
			Claims claim = tokenservices.validateToken(adminToken);
			String adminID = claim.get("adminID",String.class);
			String adminName = claim.getSubject();
			
			String result = jobServices.deleteJob(jobID);
			if(result.equals("Job Not Exist")) {
				return ResponseEntity.status(404).body(Map.of("message",result));
			}
			return ResponseEntity.ok((Map.of("message",result)));
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(401).body(Map.of("message","Invalid Token"));
		}
	}
	
	@GetMapping("/allMessages")
	public ResponseEntity<?> getMessage(HttpServletRequest request){
		String adminToken = tokenservices.getToken(request);
		if(adminToken==null) {
			return ResponseEntity.status(401).body(Map.of("message","Unauthorized"));
		}
		try {
			Claims claim = tokenservices.validateToken(adminToken);
			String adminID = claim.get("adminID",String.class);
			String adminName = claim.getSubject();
			
			return ResponseEntity.ok(jobServices.getMessage());
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(401).body(Map.of("message","Invalid Token"));
		}
	}
	@DeleteMapping("/deleteMessage/{id}")
	public ResponseEntity<?> deleteMessage(HttpServletRequest request, @PathVariable int id){
		String adminToken = tokenservices.getToken(request);
		if(adminToken==null) {
			return ResponseEntity.status(401).body(Map.of("message","Unauthorized"));
		}
		try {
			Claims claim = tokenservices.validateToken(adminToken);
			String adminID = claim.get("adminID",String.class);
			String adminName = claim.getSubject();
			
			return ResponseEntity.ok(Map.of("message",jobServices.deleteMessage(id)));
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(401).body(Map.of("message","Invalid Token"));
		}
	}
	
	@GetMapping("/stats")
	public ResponseEntity<?> getstats(HttpServletRequest request){
		String adminToken = tokenservices.getToken(request);
		if(adminToken==null) {
			return ResponseEntity.status(401).body(Map.of("message","Unauthorized"));
		}
		try {
			Claims claim = tokenservices.validateToken(adminToken);
			String adminID = claim.get("adminID",String.class);
			String adminName = claim.getSubject();
			
			return ResponseEntity.ok(services.getStats());
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(401).body(Map.of("message","Invalid Token"));
		}
	}
	
	@PostMapping("/mailSender")
	public ResponseEntity<?> adminMailSender(HttpServletRequest request,@RequestBody Map<String,String> msg){
		String adminToken = tokenservices.getToken(request);
		if(adminToken == null) {
			return ResponseEntity.status(401).body(Map.of("message","Unauthorized"));
		}
		try {
			Claims claim = tokenservices.validateToken(adminToken);
			String adminId = claim.get("adminID",String.class);
			if (adminId == null || adminId.isEmpty()) {
	            return ResponseEntity.status(403)
	                    .body(Map.of("message", "Access Denied"));
	        }
			
			String Subject = msg.get("subject");
			String Message = msg.get("message");
			
			if (Subject == null || Subject.isEmpty() ||
		            Message == null || Message.isEmpty()) {

		            return ResponseEntity.badRequest()
		                    .body(Map.of("message", "Subject and message are required"));
		        }
			emailSender.AdminMailSender(Subject, Message);
			
			return ResponseEntity.ok(Map.of("message","Mail Sent Successfully"));
			
		}catch(Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(401).body(Map.of("message","Invalid Token"));
		}
	}
	@GetMapping("/userInfo")
	public ResponseEntity<?> getUser(HttpServletRequest request){
//		String adminToken = tokenservices.getToken(request);
//		if(adminToken==null) {
//			return ResponseEntity.status(401).body(Map.of("message","Unauthorized"));
//		}
//		try {
//			Claims claim = tokenservices.validateToken(adminToken);
//			String adminID = claim.get("adminID",String.class);
//			if(adminID==null) return ResponseEntity.status(401).body(Map.of("message","Unauthorized"));
			
			
			
			return ResponseEntity.ok(userService.getAllUser());
			
//		}catch(Exception e) {
//			e.printStackTrace();
//			return ResponseEntity.status(401).body(Map.of("message","Invalid Token"));
//		}
	}
	
	
}
