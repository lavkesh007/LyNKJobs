package com.Spring.Student.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Spring.Student.UserModel.UserRegister;

public interface UserRepo extends JpaRepository<UserRegister,String>{
	UserRegister findByUserID(String UserID);
	 boolean existsByUserEmail(String userEmail);
	 boolean existsByUserID(String userID);
	 
	 UserRegister findByuserEmail(String UserEmail);
	 

}
