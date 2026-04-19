package com.Spring.Student.Controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Spring.Student.Repository.MCQsRepository;
import com.Spring.Student.Services.GeminiService;
//import com.sun.tools.javac.util.List;
import com.Spring.Student.UserModel.Mcqs;


@RestController
@RequestMapping("/mcqs")
public class MCQController {
	 @Autowired
	    private MCQsRepository repository;
	@Autowired
    private GeminiService service;

	@GetMapping("/{subject}")
    public List<Mcqs> getMCQs(@PathVariable String subject) {

        // ✅ handle case issue
        subject = subject.toLowerCase();

        return repository.findBySubjectAndDate(subject, LocalDate.now());
    }
}
