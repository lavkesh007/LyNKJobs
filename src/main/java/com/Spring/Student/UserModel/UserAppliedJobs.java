package com.Spring.Student.UserModel;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class UserAppliedJobs {
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int ID;
	@Column
	String userID;
	@Column
	String jobID;
	@Column
	String userName;
	@Column
	LocalDateTime applyDateTime;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public LocalDateTime getApplyDateTime() {
		return applyDateTime;
	}
	public void setApplyDateTime(LocalDateTime applyDateTime) {
		this.applyDateTime = applyDateTime;
	}
	public UserAppliedJobs(String userID, String jobID, String userName, LocalDateTime applyDateTime) {
		super();
		this.userID = userID;
		this.jobID = jobID;
		this.userName = userName;
		this.applyDateTime = applyDateTime;
	}
	public UserAppliedJobs(int iD, String userID, String jobID, String userName, LocalDateTime applyDateTime) {
		super();
		ID = iD;
		this.userID = userID;
		this.jobID = jobID;
		this.userName = userName;
		this.applyDateTime = applyDateTime;
	}
	public UserAppliedJobs(int iD, String userID, String jobID) {
		super();
		ID = iD;
		this.userID = userID;
		this.jobID = jobID;
	}
	public UserAppliedJobs(String userID, String jobID) {
		super();
		this.userID = userID;
		this.jobID = jobID;
	}
	
	public UserAppliedJobs() {
		super();
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getJobID() {
		return jobID;
	}
	public void setJobID(String jobID) {
		this.jobID = jobID;
	}
	
}
