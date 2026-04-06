package com.Spring.Student.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Spring.Student.UserModel.UserOtpVerify;

public interface UserOTP extends JpaRepository<UserOtpVerify,String>{
	UserOtpVerify findByEmail(String Email);
}
