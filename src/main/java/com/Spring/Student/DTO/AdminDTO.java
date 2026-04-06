package com.Spring.Student.DTO;

import jakarta.persistence.Column;

public class AdminDTO {
	private String adminID;
	
	private String adminName;
	
	private String adminEmail;
	
	
	public AdminDTO(String adminID, String adminName, String adminEmail) {
		super();
		this.adminID = adminID;
		this.adminName = adminName;
		this.adminEmail = adminEmail;
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
	
}
