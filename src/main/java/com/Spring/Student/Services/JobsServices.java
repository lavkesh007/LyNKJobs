package com.Spring.Student.Services;

import com.Spring.Student.Jobs.Jobs;
import com.Spring.Student.Jobs.PresentJobs;
import com.Spring.Student.Repository.JobRepo;
import com.Spring.Student.Repository.PresentJobsRepo;
import com.Spring.Student.Repository.UserMessageRepo;
import com.Spring.Student.Repository.UserRepo;
import com.Spring.Student.UserModel.UserMessage;
import com.Spring.Student.UserModel.UserRegister;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
@Service
public class JobsServices {
	
	private JobRepo jobRepo;
	@Autowired
	private PresentJobsRepo presentJob; 
	@Autowired
	private UserMessageRepo messageRepo;
	@Autowired
	private EmailSender sender;
	
	public JobsServices(JobRepo jobRepo) {
		this.jobRepo = jobRepo;
	}
	
	public List<Jobs> getAllJobs(){

	    List<PresentJobs> present = presentJob.findAll();

	    List<String> validJobIDs = present.stream()
	        .filter(job -> {
	            if(job.getExpiryDate() == null){
	                return true; // or false (your choice)
	            }

	            if(job.getExpiryDate().isBefore(LocalDate.now())){
	                presentJob.delete(job); // delete expired
	                return false;
	            }
	            return true;
	        })
	        .map(PresentJobs::getJobID)
	        .toList();

	    return jobRepo.findByJobIDIn(validJobIDs);
	}
	
	public Jobs getjobDetail(String id) {
		return jobRepo.findByjobID(id);
	}
	
	public String addJob(Jobs job) {
		String id ;
		
		int attempt = 0;
		do {
			id = "LPJOB" + generateNumber();
			if(attempt>25) {
				return "Unable to Make Job ID";
			}
		}while(jobRepo.existsById(id));
		
		job.setJobID(id);
		jobRepo.save(job);
		presentJob.save(new PresentJobs(id,job.getExpireDate()));
		sender.emailSender(job.getCompanyName(),job.getRole());
		return "Job Added!!!";
	}
	
	public int generateNumber() {
		return ThreadLocalRandom.current().nextInt(1000, 10000);
	}
	
	public List<Jobs> getJobs(){
		List<PresentJobs> present = presentJob.findAll();
		List<String> validJobIDs = present.stream()
		        .filter(job -> {
		            if(job.getExpiryDate() == null){
		                return true; // or false (your choice)
		            }

		            if(job.getExpiryDate().isBefore(LocalDate.now())){
		                presentJob.delete(job); // delete expired
		                return false;
		            }
		            return true;
		        })
		        .map(PresentJobs::getJobID)
		        .toList();
		List<Jobs> jobs = jobRepo.findByJobIDIn(validJobIDs).subList(0, Math.min(4, jobRepo.findByJobIDIn(validJobIDs).size()));
		return jobs;
		
	}
	
	public List<Jobs> getSearchJobs(String role, String location){
    	if(role=="") {
    		return jobRepo.findByLocationContainingIgnoreCase(location);
    	}
    	if(location=="") {
    		return jobRepo.findByRoleContainingIgnoreCaseOrSkillContainingIgnoreCase(role,role);
    	}
    	return jobRepo.findByRoleContainingIgnoreCaseOrSkillContainingIgnoreCaseAndLocationContainingIgnoreCase(role,role, location);
    }
	
	public String deleteJob(String jobID) {
		PresentJobs job = presentJob.findByJobID(jobID);
		if(job == null) {
			return "Job Not Exist";
		}
		presentJob.delete(job);
		return "Job Delete";
	}
	
	public List<UserMessage> getMessage(){
		return messageRepo.findAll();
	}
	
	public String deleteMessage(int id) {
		messageRepo.deleteById(id);
		return "Message Delete";
	}
	
}
