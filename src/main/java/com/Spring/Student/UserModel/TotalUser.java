package com.Spring.Student.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class TotalUser {
	@Id
	int id;
	@Column
	String UserID;
	@Column
	String UserName;
	@Column
	String Email;
	public TotalUser(int id, String userID, String userName, String email) {
		super();
		this.id = id;
		UserID = userID;
		UserName = userName;
		Email = email;
	}
	public TotalUser(String userID, String userName, String email) {
		super();
		UserID = userID;
		UserName = userName;
		Email = email;
	}
	public TotalUser() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserID() {
		return UserID;
	}
	public void setUserID(String userID) {
		UserID = userID;
	}
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	
}
