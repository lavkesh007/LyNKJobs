package com.Spring.Student.Services;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Spring.Student.Repository.TotalUserRepo;
import com.Spring.Student.Repository.UserMessageRepo;
import com.Spring.Student.Repository.UserOTP;
import com.Spring.Student.Repository.UserRepo;
import com.Spring.Student.UserModel.TotalUser;
import com.Spring.Student.UserModel.UserMessage;
import com.Spring.Student.UserModel.UserOtpVerify;
import com.Spring.Student.UserModel.UserRegister;

@Service
public class UserServices {
	@Autowired
	private UserMessageRepo messageRepo;
	@Autowired
	private CloudnairyServices cloudServices;
	@Autowired
	private TotalUserRepo totalRepo;
	private UserRepo userRepo;
	private UserOTP userOtpRepo;
	private JavaMailSender mailSender;
	public UserServices(UserRepo userRepo,UserOTP userOtpRepo,JavaMailSender mailSender){
	this.userRepo = userRepo;
	this.userOtpRepo = userOtpRepo;
	this.mailSender  = mailSender;
	}

	

	    public String register(UserRegister user , String otp) {
	    	UserOtpVerify verify = userOtpRepo.findByEmail(user.getUserEmail());
	    	if(!verify.getOtp().equals(otp)) {
	    		return "InvalidOTP";
	    	}
	    	if(LocalDateTime.now().isAfter( verify.getTime().plusMinutes(5))) {
	    		return "OTPExpired";
	    	}
	        String id;
	        int attempts = 0;
	        do {
	            id = "LSP" + generateNumber();
	            attempts++;
	            
	            if(attempts > 25) {
	                throw new RuntimeException("Unable to generate unique ID");
	            }
	        } while (userRepo.existsByUserID(id));
	        user.setUserID(id);
	        TotalUser total = new TotalUser();
	        total.setUserID(id);
	        total.setEmail(user.getUserEmail());
	        total.setUserName(user.getUserName());
	        totalRepo.save(total);
	        userRepo.save(user);
	        
	        return "Registered Successfully";
	    }
	    
	    
	    
	    public int generateNumber() {
	        return ThreadLocalRandom.current().nextInt(10000, 100000);
	    }
	    
	    public String getUserID(String email) {
	    	UserRegister user = userRepo.findByuserEmail(email);
	    	String UserID = user.getUserID();
	    	return UserID;
	    }
	    public String getUserName(String email) {
	    	UserRegister user = userRepo.findByuserEmail(email);
	    	String UserName = user.getUserName();
	    	return UserName;
	    }
	    
	   public UserRegister getUser(String UserID) {
		   return userRepo.findByUserID(UserID);
	   }
	    
	    
	    public String Login(String Email , String Password) {
	    	
	    	UserRegister user = userRepo.findByuserEmail(Email);
	    	if(user==null) {
	    		return "User Not Found";
	    	}
	    	if(!user.getPassword().equals(Password)) {
	    		return "Invalid Password";
	    	}
	    	return "Login Successfull!!!";
	    }
	    
	    
	    public String generateOtpforVerifyRegister(String email) {
	    	UserRegister user = userRepo.findByuserEmail(email);
	    	if(user!=null) {
	    		return "User already Exist!";
	    	}
	    	
	    	return OTPSender(email);
	    }
	    
	    public String generateOtpforForgotPassword(String email) {
	    	UserRegister user = userRepo.findByuserEmail(email);
	    	if(user==null) {
	    		return "User Not Found";
	    	}
	    	return OTPSender(email);
	    }
	    public String VerifyOtpforChangePassword(String email,String otp) {
	    	UserOtpVerify verify = userOtpRepo.findByEmail(email);
	    	if(LocalDateTime.now().isAfter( verify.getTime().plusMinutes(5))) {
	    		return "OTPExpired";
	    	}
	    	
	    	if(!verify.getOtp().equals(otp)) {
	    		return "InvalidOTP";
	    	}
	    	
	    	userOtpRepo.delete(verify);
	    	return "Verified";
	    }
	    
	    public String ChangePassword(String email, String password) {
	    	UserRegister user = userRepo.findByuserEmail(email);
	    	if(user==null) {
	    		return "User Not Found";
	    	}
	    	user.setPassword(password);
	    	userRepo.save(user);
	    	return "changed";
	    }
	    
	    
	    public String OTPSender(String email) {
	    	int otp = generateNumber();
	    	UserOtpVerify userOTP = new UserOtpVerify();
	    	userOTP.setEmail(email);
	    	userOTP.setOtp(String.valueOf(otp));
	    	userOTP.setTime(LocalDateTime.now());
	    	userOtpRepo.save(userOTP);
	    	SimpleMailMessage message = new SimpleMailMessage();
	    	message.setTo(email);
	    	message.setSubject("OTP Verification");
	    	message.setText("Dear User, \n Thank you for part of LyNK Job's.\nYour OTP for account verification is: "+otp +". It is valid for 5 minutes.\nFor security reasons, please do not share this code with anyone.\nIf you did not initiate this request, please ignore this message.");
	    	mailSender.send(message);
	    	System.out.print(otp);
	    	return "Otp Generated";
	    }
	    
	    
	    public String sendMessage(UserMessage message) {
	    	messageRepo.save(message);
	    	return "success";
	    }
	    
	    public String editUserDetails(UserRegister user, String userID, MultipartFile file) {

	        UserRegister user1 = userRepo.findByUserID(userID);
	        if(user.getUserName() != null)
	            user1.setUserName(user.getUserName());
	        if(user.getUserEmail() != null)
	            user1.setUserEmail(user.getUserEmail());
	        if(user.getPhoneNo() != null)
	            user1.setPhoneNo(user.getPhoneNo());
	        if(user.getDOB() != null)
	            user1.setDOB(user.getDOB());
	        if(user.getPassoutYear() != null)
	            user1.setPassoutYear(user.getPassoutYear());
	        if(user.getGender() != null)
	            user1.setGender(user.getGender());
	        if(user.getHighQualification() != null)
	            user1.setHighQualification(user.getHighQualification());
	        if(user.getCollegeName() != null)
	            user1.setCollegeName(user.getCollegeName());

	        if (file != null && !file.isEmpty()) {
	            String imageUrl = cloudServices.uploadImage(file);
	            user1.setImage(imageUrl);
	        }
	        userRepo.save(user1);
	        return "success";
	    }
	    
	    public String checkPassword(String userID,String password) {
	    	UserRegister user = userRepo.findByUserID(userID);
	    	if(!password.equals(user.getPassword())) {
	    		return "Invalid";
	    	}
	    	return "success";
	    }
	    public String changefromSettingPassword(String userID,String password) {
	    	UserRegister user = userRepo.findByUserID(userID);
	    	user.setPassword(password);
	    	userRepo.save(user);
	    	return "success";
	    }
	    
	    public String sendOTPToSetting(String userID) {
	    	UserRegister user = userRepo.findByUserID(userID);
	    	
	    	return OTPSender(user.getUserEmail());
	    }
	    public String verifyOTPToSetting(String userID,String otp) {
	    	UserRegister user = userRepo.findByUserID(userID);
	    	
	    	return VerifyOtpforChangePassword(user.getUserEmail(),otp);
	    }
	    
	    public String deleteUser(String userID) {
	    	userRepo.deleteById(userID);
	    	return "User Account Deleted!!";
	    }
	}



