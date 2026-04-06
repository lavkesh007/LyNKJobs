package com.Spring.Student.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Spring.Student.Jobs.Jobs;
import com.Spring.Student.Jobs.PresentJobs;

public interface PresentJobsRepo extends JpaRepository<PresentJobs , String>{

	PresentJobs findByJobID(String jobID);

}
