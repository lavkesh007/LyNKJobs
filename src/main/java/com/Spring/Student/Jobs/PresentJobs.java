package com.Spring.Student.Jobs;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class PresentJobs {
	@Id
	String jobID;
	@Column
	LocalDate ExpiryDate;
	public PresentJobs(String jobID, LocalDate expiryDate) {
		super();
		this.jobID = jobID;
		ExpiryDate = expiryDate;
	}

	public LocalDate getExpiryDate() {
		return ExpiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		ExpiryDate = expiryDate;
	}

	public String getJobID() {
		return jobID;
	}
	
	public PresentJobs() {
		super();
	}

	public void setJobID(String jobID) {
		this.jobID = jobID;
	}

	public PresentJobs(String jobID) {
		super();
		this.jobID = jobID;
	}
}
