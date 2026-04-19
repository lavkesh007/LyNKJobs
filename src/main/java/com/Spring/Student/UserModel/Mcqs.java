package com.Spring.Student.UserModel;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Mcqs {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;

    @Column(length = 1000)
    private String question;

    @Column(length = 1000)
    private String options;

    private String answer;

    private LocalDate date;

	public Mcqs(Long id, String subject, String question, String options, String answer, LocalDate date) {
		super();
		this.id = id;
		this.subject = subject;
		this.question = question;
		this.options = options;
		this.answer = answer;
		this.date = date;
	}

	public Mcqs(String subject, String question, String options, String answer, LocalDate date) {
		super();
		this.subject = subject;
		this.question = question;
		this.options = options;
		this.answer = answer;
		this.date = date;
	}

	public Mcqs() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getOptions() {
		return options;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
    
}
