package com.Spring.Student.UserModel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table
public class UserMessage {
	@Id
	int id;
	@Column
	String userName;
	@Column
	String userID;
	@Column
	String userEmail;
	@Column
	String userMessage;
	public UserMessage(int id, String userName, String userID, String userEmail, String userMessage) {
		super();
		this.id = id;
		this.userName = userName;
		this.userID = userID;
		this.userEmail = userEmail;
		this.userMessage = userMessage;
	}
	public UserMessage(String userName, String userID, String userEmail, String userMessage) {
		super();
		this.userName = userName;
		this.userID = userID;
		this.userEmail = userEmail;
		this.userMessage = userMessage;
	}
	public UserMessage(String userName, String userEmail, String userMessage) {
		super();
		this.userName = userName;
		this.userEmail = userEmail;
		this.userMessage = userMessage;
	}
	public UserMessage() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserMessage() {
		return userMessage;
	}
	public void setUserMessage(String userMessage) {
		this.userMessage = userMessage;
	}
	
}
