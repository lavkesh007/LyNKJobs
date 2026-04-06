package com.Spring.Student.UserModel;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class UserOtpVerify {
	public UserOtpVerify() {
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public LocalDateTime getTime() {
		return time;
	}
	public void setTime(LocalDateTime time) {
		this.time = time;
	}
	@Id
	String email;
	@Column
	String otp;
	@Column
	LocalDateTime time;
	public UserOtpVerify(String email, String otp, LocalDateTime time) {
		super();
		this.email = email;
		this.otp = otp;
		this.time = time;
	}
}
