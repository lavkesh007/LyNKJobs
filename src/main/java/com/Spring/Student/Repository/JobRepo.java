package com.Spring.Student.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Spring.Student.Jobs.Jobs;
import com.Spring.Student.Jobs.PresentJobs;

public interface JobRepo extends JpaRepository<Jobs,String>{
	List<Jobs> findByJobIDIn(List<String> present);
	Jobs findByjobID(String id);
	List<Jobs> findByLocationContainingIgnoreCase(String location);
	List<Jobs> findByRoleContainingIgnoreCaseOrSkillContainingIgnoreCase(String role,String skill);
	List<Jobs> findByRoleContainingIgnoreCaseOrSkillContainingIgnoreCaseAndLocationContainingIgnoreCase(String role,String skill,String location);
	
	
}
