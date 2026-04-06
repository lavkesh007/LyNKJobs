package com.Spring.Student.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Spring.Student.AdminModel.AdminLoginCredentials;

public interface AdminRepo extends JpaRepository<AdminLoginCredentials,String>{
	AdminLoginCredentials findByAdminEmail(String email);
	AdminLoginCredentials findByAdminID(String id);
}
