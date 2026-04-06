package com.Spring.Student.Services;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.Spring.Student.AdminModel.AdminLoginCredentials;
import com.Spring.Student.Repository.AdminRepo;
import com.Spring.Student.Repository.JobApplyRepo;
import com.Spring.Student.Repository.JobRepo;
import com.Spring.Student.Repository.PresentJobsRepo;
import com.Spring.Student.Repository.TotalUserRepo;
import com.Spring.Student.Repository.UserMessageRepo;
import com.Spring.Student.Repository.UserRepo;
import com.Spring.Student.UserModel.TotalUser;
@Service
public class AdminServices {
	private AdminRepo adminRepo;
	private PresentJobsRepo presentRepo;
	private JobApplyRepo jobApplyRepo;
	private TotalUserRepo totalUserRepo;
	private UserMessageRepo messageRepo;
	private UserRepo userRepo;
	private JobRepo jobRepo;
	
	
	
	public AdminServices(PresentJobsRepo presentRepo, JobApplyRepo jobApplyRepo, TotalUserRepo totalUserRepo,
			UserMessageRepo messageRepo, UserRepo userRepo, JobRepo jobRepo,AdminRepo adminRepo) {
		super();
		this.presentRepo = presentRepo;
		this.jobApplyRepo = jobApplyRepo;
		this.totalUserRepo = totalUserRepo;
		this.messageRepo = messageRepo;
		this.userRepo = userRepo;
		this.jobRepo = jobRepo;
		this.adminRepo = adminRepo;
	}
	
	public String verifyAdminLogin(String email ,String password) {
		AdminLoginCredentials admin = adminRepo.findByAdminEmail(email);
		if(admin==null) {
			return "Admin Not Found";
		}
		if(admin.getAdminPassword() == null ||!admin.getAdminPassword().equals(password)) {
			return "Invalid Password";
		}
		return "Login Successfull";
	}
	
	public AdminLoginCredentials getAdmin(String email) {
		return adminRepo.findByAdminEmail(email);
	}
	public AdminLoginCredentials getAdminbyID(String id) {
		return adminRepo.findByAdminID(id);
	}
	public Map<String,Long> getStats(){
		Long activeJobs = presentRepo.count();
		Long totalJobs = jobRepo.count();
		Long activeUsers = userRepo.count();
		Long totalUsers = totalUserRepo.count();
		Long totalApplyJobs = jobApplyRepo.count();
		Long totalUserRequest = messageRepo.count();
		Map<String,Long> stats = new LinkedHashMap<>();
		stats.put("activeJobs", activeJobs);
		stats.put("totalJobs", totalJobs);
		stats.put("activeUsers", activeUsers);
		stats.put("totalUsers", totalUsers);
		stats.put("totalApplyJobs", totalApplyJobs);
		stats.put("totalUserRequest", totalUserRequest);
		
		return stats;
		
	}
}
