package com.Spring.Student.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Spring.Student.UserModel.UserAppliedJobs;

public interface JobApplyRepo extends JpaRepository<UserAppliedJobs,Integer>{
	List<UserAppliedJobs> findAllByUserID(String ID);
	

}
