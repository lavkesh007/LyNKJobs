package com.Spring.Student.AdminModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class AdminLoginCredentials {
	@Id
	private String adminID;
	@Column
	private String adminName;
	@Column
	private String adminEmail;
	@Column
	private String adminPassword;
	public AdminLoginCredentials(String adminID, String adminName, String adminEmail, String adminPassword) {
		super();
		this.adminID = adminID;
		this.adminName = adminName;
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
	}
	public AdminLoginCredentials(String adminName, String adminEmail, String adminPassword) {
		super();
		this.adminName = adminName;
		this.adminEmail = adminEmail;
		this.adminPassword = adminPassword;
	}
	public AdminLoginCredentials(String adminEmail, String abminPassword) {
		super();
		this.adminEmail = adminEmail;
		this.adminPassword = abminPassword;
	}
	public AdminLoginCredentials() {
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
	public String getAdminPassword() {
		return adminPassword;
	}
	public void setAdminPassword(String abminPassword) {
		this.adminPassword = abminPassword;
	}
	
}
