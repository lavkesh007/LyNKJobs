package com.Spring.Student.DTO;

import java.time.LocalDate;

import jakarta.persistence.Column;

public class UserDTO {
	private String userID;
	private String userName;
	private String userEmail;
	private Long PhoneNo;
	private LocalDate DOB;
	private String gender;
	private String highQualification;
	private String collegeName;
	private String image;
	private String passoutYear;
	public UserDTO(String userID, String userName, String userEmail, Long phoneNo, LocalDate dOB, String gender,
			String highQualification, String collegeName, String image, String passoutYear) {
		super();
		this.userID = userID;
		this.userName = userName;
		this.userEmail = userEmail;
		PhoneNo = phoneNo;
		DOB = dOB;
		this.gender = gender;
		this.highQualification = highQualification;
		this.collegeName = collegeName;
		this.image = image;
		this.passoutYear = passoutYear;
	}
	public UserDTO(String userName, String userEmail, Long phoneNo, LocalDate dOB, String gender,
			String highQualification, String collegeName, String image, String passoutYear) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		PhoneNo = phoneNo;
		DOB = dOB;
		this.gender = gender;
		this.highQualification = highQualification;
		this.collegeName = collegeName;
		this.image = image;
		this.passoutYear = passoutYear;
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
	public void setPhoneNo(Long phoneNo) {
		PhoneNo = phoneNo;
	}
	public LocalDate getDOB() {
		return DOB;
	}
	public void setDOB(LocalDate dOB) {
		DOB = dOB;
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
	public String getPassoutYear() {
		return passoutYear;
	}
	public void setPassoutYear(String passoutYear) {
		this.passoutYear = passoutYear;
	}
	
	
}
