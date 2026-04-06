package com.Spring.Student.Controller;
import io.jsonwebtoken.ExpiredJwtException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Spring.Student.DTO.UserDTO;
import com.Spring.Student.Services.UserServices;
import com.Spring.Student.UserModel.UserMessage;
import com.Spring.Student.UserModel.UserRegister;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import tools.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "https://lynkjob.d1z9wprzw5dh5p.amplifyapp.com")
@RestController
@RequestMapping("/user")
public class UserController {
	UserServices service;
	Token tokenservice;
	UserController(UserServices service ,Token tokenservice){
		this.service = service;
		this.tokenservice = tokenservice;
	}
	
	
	public UserDTO getDTO(UserRegister user) {
		return new UserDTO(user.getUserID(),user.getUserName(),user.getUserEmail(),user.getPhoneNo(),user.getDOB(),user.getGender(),user.getHighQualification(),user.getCollegeName(),user.getImage(),user.getPassoutYear());
	}
	
	
	
	
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserRegister user) {
		String result = service.Login(user.getUserEmail(), user.getPassword());
	    if(result.equals( "User Not Found")) {
	    	return ResponseEntity.status(404).body(Map.of("message", "User not found"));
	    }

	    if(result.equals("Invalid Password")) {
	    	return ResponseEntity.status(401).body(Map.of("message", "Invalid password"));
	    }
	    String userID = service.getUserID(user.getUserEmail());
	    String userName = service.getUserName(user.getUserEmail());
	    String Token = tokenservice.Token(userName,userID,null);
	    return ResponseEntity.ok(Map.of("message", "Login Successful","token",Token)); 
	}
	
	@PostMapping("/registerEmailOtp")
	public ResponseEntity<?> registerEmailOtp(@RequestBody UserRegister user){
		String  result = service.generateOtpforVerifyRegister(user.getUserEmail());
		if(result.equals("User already Exist!")) {
			return ResponseEntity.status(409).body(Map.of("message","User already Exist!"));
		}
		return ResponseEntity.ok(Map.of("message","OTP Send"));
	}
	
	
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody Map<String,String> request){
	    String name = request.get("userName");
	    String email = request.get("userEmail");
	    Long phone = Long.parseLong(request.get("phoneNo"));
	    LocalDate dob = LocalDate.parse(request.get("dob"));
	    String password = request.get("password");
	    String otp = request.get("otp");
	    UserRegister user = new UserRegister(name,email,phone,dob,password);
	    String result = service.register(user,otp);
	    if(result.equals("InvalidOTP")) {
	        return ResponseEntity.status(400).body(Map.of("message","Enter Valid OTP!"));
	    }
	    if(result.equals("OTPExpired")) {
	        return ResponseEntity.status(410).body(Map.of("message","OTP Expired!!!"));
	    }
	    return ResponseEntity.ok(Map.of("message","Registration Successfull"));
	}
	
	@PostMapping("/forgotPasswordOTP")
	public ResponseEntity<?> GenrateOTPForForgotPassword(@RequestBody Map<String,String> request){
		String email = request.get("userEmail");
		String result = service.generateOtpforForgotPassword(email);
		if(result.equals("User Not Found")) {
			return ResponseEntity.status(404).body(Map.of("message","User Not Found!!"));
		}
		return ResponseEntity.ok(Map.of("message","OTP Send!!!"));
	}
	@PostMapping("/forgotPasswordverifyOTP")
	public ResponseEntity<?> VerifyOTPForChangingPassword(@RequestBody Map<String,String> request){
		String email = request.get("email");
		String otp = request.get("otp");
		String result = service.VerifyOtpforChangePassword(email, otp);
		if(result.equals("InvalidOTP")) {
	        return ResponseEntity.status(400).body(Map.of("message","Enter Valid OTP!"));
	    }
	    if(result.equals("OTPExpired")) {
	        return ResponseEntity.status(410).body(Map.of("message","OTP Expired!!!"));
	    }
		return ResponseEntity.ok(Map.of("message","Verified"));
	}
	
	@PutMapping("/changePassword")
	public ResponseEntity<?> changePassword(@RequestBody Map<String,String> request){
		String email = request.get("email");
		String password = request.get("password");
		String result = service.ChangePassword(email, password);
		if(result.equals("User Not Found")) {
			return ResponseEntity.status(404).body(Map.of("message","User Not Found!!!"));
		}
		return ResponseEntity.ok(Map.of("message","Password Changed Successfully!!!"));
	}
	
	//get user detail after login
	@GetMapping("/me")
	public ResponseEntity<?> getUser(HttpServletRequest request){
		
		String token = tokenservice.getToken(request);
		if(token == null) {
			return ResponseEntity.status(401).body("Unauthorized");
		}
		try {
			Claims claim = tokenservice.validateToken(token);
			String userName = claim.getSubject();
			String userID = claim.get("userID",String.class);
			UserRegister user = service.getUser(userID);
			return ResponseEntity.ok(getDTO(user));
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid Token");
		}
	}
	
	@GetMapping("/userDetails")
	public ResponseEntity<?> getUserDetails(HttpServletRequest request){
		String token = tokenservice.getToken(request);
		if(token == null) {
			return ResponseEntity.status(401).body("Unauthorized");
		}
		try {
			Claims claim = tokenservice.validateToken(token);
			String userID = claim.get("userID", String.class);
			UserRegister user = service.getUser(userID);
			return ResponseEntity.ok(getDTO(user));
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid Token");
		}
	}
	
	
	@PostMapping("/sendMessage")
	public ResponseEntity<?> sendMessage(@RequestBody UserMessage message){
		System.out.println(message.getUserMessage());
		return ResponseEntity.ok(Map.of("message",service.sendMessage(message)));
	}
	
	@PostMapping("/editUser")
	public ResponseEntity<?> editUserDetails(HttpServletRequest request,@RequestParam("user") String userJson,@RequestParam(value = "image", required = false) MultipartFile file){
		
		String token = tokenservice.getToken(request);
		if(token == null) {
			return ResponseEntity.status(401).body("Unauthorized");
		}
		try {
			Claims claim = tokenservice.validateToken(token);
			String userID = claim.get("userID", String.class);
			ObjectMapper mapper = new ObjectMapper();
			UserRegister user = mapper.readValue(userJson, UserRegister.class);
			
			return ResponseEntity.ok(service.editUserDetails(user,userID,file));
		}catch (ExpiredJwtException e) {
		    return ResponseEntity.status(401).body("Token Expired");
		} 
		catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid Token");
		}
	}
	
	@PostMapping("/checkOldPassword")
	public ResponseEntity<?> checkPassword(HttpServletRequest request,@RequestBody UserRegister user){
		String token = tokenservice.getToken(request);
		if(token == null) {
			return ResponseEntity.status(401).body("Unauthorized");
		}
		try {
			Claims claim = tokenservice.validateToken(token);
			String userID = claim.get("userID", String.class);
			String result = service.checkPassword(userID, user.getPassword());
			if(result.equals("Invalid")) {
				return ResponseEntity.status(404).body(Map.of("message",result));
			}
			return ResponseEntity.ok(Map.of("message" ,result));
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid Token");
		}
	}
	
	@PutMapping("/settingPassword")
	public ResponseEntity<?> changeFromSettingPassword(HttpServletRequest request,@RequestBody Map<String,String> user){
		String token = tokenservice.getToken(request);
		if(token == null) {
			return ResponseEntity.status(401).body("Unauthorized");
		}
		try {
			Claims claim = tokenservice.validateToken(token);
			String userID = claim.get("userID", String.class);
			String result = service.changefromSettingPassword(userID, user.get("password"));
			
			return ResponseEntity.ok(Map.of("message" ,result));
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid Token");
		}
	}
	
	@PostMapping("/settingOTP")
	public ResponseEntity<?> sendOTPToSetting(HttpServletRequest request){
		String token = tokenservice.getToken(request);
		if(token == null) {
			return ResponseEntity.status(401).body("Unauthorized");
		}
		try {
			Claims claim = tokenservice.validateToken(token);
			String userID = claim.get("userID", String.class);
			String result = service.sendOTPToSetting(userID);
			
			return ResponseEntity.ok(Map.of("message" ,result));
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid Token");
		}
	}
	@PostMapping("/settingVerifyOTP")
	public ResponseEntity<?> verifyOTPToSetting(HttpServletRequest request,@RequestBody Map<String,String> otp){
		String token = tokenservice.getToken(request);
		if(token == null) {
			return ResponseEntity.status(401).body("Unauthorized");
		}
		try {
			Claims claim = tokenservice.validateToken(token);
			String userID = claim.get("userID", String.class);
			String result = service.verifyOTPToSetting(userID,otp.get("otp"));
			if(result.equals("OTPExpired")) {
				return ResponseEntity.status(410).body(Map.of("message",result));
			}
			if(result.equals("InvalidOTP")) {
				return ResponseEntity.status(400).body(Map.of("message",result));
			}
			return ResponseEntity.ok(Map.of("message" ,result));
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid Token");
		}
	}
	
	@DeleteMapping("/deleteUser")
	public ResponseEntity<?> DeleteUser(HttpServletRequest request){
		String token = tokenservice.getToken(request);
		if(token == null) {
			return ResponseEntity.status(401).body("Unauthorized");
		}
		try {
			Claims claim = tokenservice.validateToken(token);
			String userID = claim.get("userID", String.class);
			String result = service.deleteUser(userID);
			
			return ResponseEntity.ok(Map.of("message" ,result));
		}catch(Exception e) {
			return ResponseEntity.status(401).body("Invalid Token");
		}
	}
}
