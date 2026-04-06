package com.Spring.Student.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Spring.Student.UserModel.UserMessage;

public interface UserMessageRepo extends JpaRepository<UserMessage,Integer>{
	
}
