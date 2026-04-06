package com.Spring.Student.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Spring.Student.Jobs.Jobs;
import com.Spring.Student.Repository.JobApplyRepo;
import com.Spring.Student.Repository.JobRepo;
import com.Spring.Student.UserModel.UserAppliedJobs;
@Service
public class ApplyJobsServices {
	@Autowired
	private JobRepo jobRepo;
	private JobApplyRepo applyRepo;
	

	public ApplyJobsServices(JobApplyRepo applyRepo) {
		super();
		this.applyRepo = applyRepo;
	}
	
	public String applyJob(String UserID,String JobID) {
		UserAppliedJobs user = new UserAppliedJobs();
		user.setUserID(UserID);
		user.setJobID(JobID);
		applyRepo.save(user);
		return "Applied";
	}
	
	public List<Jobs> getAllJobsIDs(String userID){

	    List<UserAppliedJobs> appliedJobs = applyRepo.findAllByUserID(userID);

	    List<Jobs> jobs = new ArrayList<>();

	    for(UserAppliedJobs u : appliedJobs) {
	        jobs.add(jobRepo.findByjobID(u.getJobID()));
	    }

	    return jobs;	
	}
	

}
