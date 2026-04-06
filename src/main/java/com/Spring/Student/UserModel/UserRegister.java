package com.Spring.Student.UserModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table
public class UserRegister {
	@Id 
	String userID;
	@Column
	String userName;
	@Column(unique = true, nullable = false)
	String userEmail;
	@Column
	Long PhoneNo;
	@Column
	LocalDate DOB;
	@Column
	String passoutYear;
	
	@Column
	String password;
	@Column
	String gender;
	@Column
	String highQualification;
	@Column
	String collegeName;
	@Column
	String image;
	public UserRegister(String userID, String userName, String userEmail, Long phoneNo, LocalDate dOB,String password) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userEmail = userEmail;
		PhoneNo = phoneNo;
		DOB = dOB;
	
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public Long getPhoneNo() {
		return PhoneNo;
	}
	public UserRegister(String userName, String userEmail, Long phoneNo, LocalDate dOB, String password) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		PhoneNo = phoneNo;
		DOB = dOB;
		this.password = password;
	}
	public void setPhoneNo(Long phoneNo) {
		PhoneNo = phoneNo;
	}
	public LocalDate getDOB() {
		return DOB;
	}
	public void setDOB(LocalDate dOB) {
		DOB = dOB;
	}
	public String getPassoutYear() {
		return passoutYear;
	}
	public void setPassoutYear(String passoutYear) {
		this.passoutYear = passoutYear;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getHighQualification() {
		return highQualification;
	}
	public void setHighQualification(String highQualification) {
		this.highQualification = highQualification;
	}
	public String getCollegeName() {
		return collegeName;
	}
	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	public UserRegister() {
		super();
	}
	public UserRegister(String userName, String userEmail, Long phoneNo, LocalDate dOB) {
		super();
		userName = userName;
		userEmail = userEmail;
		PhoneNo = phoneNo;
		DOB = dOB;
		
	}
	public UserRegister(String userName, String userEmail, Long phoneNo, LocalDate dOB, String passoutYear,
			String password, String gender, String highQualification, String collegeName, String image) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		PhoneNo = phoneNo;
		DOB = dOB;
		this.passoutYear = passoutYear;
		this.password = password;
		this.gender = gender;
		this.highQualification = highQualification;
		this.collegeName = collegeName;
		this.image = image;
	}
	public UserRegister(String userID, String userName, String userEmail, Long phoneNo, LocalDate dOB,
			String passoutYear, String password, String gender, String highQualification, String collegeName,
			String image) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userEmail = userEmail;
		PhoneNo = phoneNo;
		DOB = dOB;
		this.passoutYear = passoutYear;
		this.password = password;
		this.gender = gender;
		this.highQualification = highQualification;
		this.collegeName = collegeName;
		this.image = image;
	}
	
}
