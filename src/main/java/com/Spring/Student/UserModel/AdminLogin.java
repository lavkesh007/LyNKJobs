package com.Spring.Student.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class AdminLogin {
	@Id
	private String adminID;
	@Column
	private String adminName;
	@Column
	private String adminEmail;
	@Column
	private String abminPassword;
	public AdminLogin(String adminID, String adminName, String adminEmail, String abminPassword) {
		super();
		this.adminID = adminID;
		this.adminName = adminName;
		this.adminEmail = adminEmail;
		this.abminPassword = abminPassword;
	}
	public AdminLogin(String adminName, String adminEmail, String abminPassword) {
		super();
		this.adminName = adminName;
		this.adminEmail = adminEmail;
		this.abminPassword = abminPassword;
	}
	public AdminLogin(String adminEmail, String abminPassword) {
		super();
		this.adminEmail = adminEmail;
		this.abminPassword = abminPassword;
	}
	public AdminLogin() {
		super();
	}
	public String getAdminID() {
		return adminID;
	}
	public void setAdminID(String adminID) {
		this.adminID = adminID;
	}
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getAdminEmail() {
		return adminEmail;
	}
	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}
	public String getAbminPassword() {
		return abminPassword;
	}
	public void setAbminPassword(String abminPassword) {
		this.abminPassword = abminPassword;
	}
	
}
