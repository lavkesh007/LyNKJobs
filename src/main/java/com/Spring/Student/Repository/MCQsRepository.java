package com.Spring.Student.Repository;


import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Spring.Student.UserModel.Mcqs;

public interface MCQsRepository extends JpaRepository<Mcqs,Integer>{
	List<Mcqs> findBySubjectAndDate(String subject, LocalDate date);
}
