package com.Spring.Student.Jobs;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table
public class Jobs {
	@Id
	String jobID;
	@Column(length = 1000)
	String logo;
	@Column
	String companyName;
	@Column
	String role;
	@Column
	String location;
	@Column
	LocalDate openDate;
	@Column
	LocalDate expireDate;
	@Column
	String skill;
	@Column
	String experience;
	@Column
	String passoutYear;
	@Column(length = 1000)
	String responsibility;
	@Column
	String qualification;
	@Column
	String workMode;
	@Column
	String salary;
	@Column
	String bond;
	@Column
	String genderPreference;
	@Column(length = 1000)
	String description;
	@Column(length = 1000)
	String websiteUrl;
	public String getWebsiteUrl() {
		return websiteUrl;
	}
	public void setWebsiteUrl(String websiteUrl) {
		this.websiteUrl = websiteUrl;
	}
	public Jobs(String jobID, String logo, String companyName, String role, String location, LocalDate openDate,
			LocalDate expireDate, String skill, String experience, String passoutYear, String responsibility,
			String qualification, String workMode, String salary, String bond, String genderPreference,
			String description) {
		super();
		this.jobID = jobID;
		this.logo = logo;
		this.companyName = companyName;
		this.role = role;
		this.location = location;
		this.openDate = openDate;
		this.expireDate = expireDate;
		this.skill = skill;
		this.experience = experience;
		this.passoutYear = passoutYear;
		this.responsibility = responsibility;
		this.qualification = qualification;
		this.workMode = workMode;
		this.salary = salary;
		this.bond = bond;
		this.genderPreference = genderPreference;
		this.description = description;
	}
	public Jobs(String logo, String companyName, String role, String location, LocalDate openDate, LocalDate expireDate,
			String skill, String experience, String passoutYear, String responsibility, String qualification,
			String workMode, String salary, String bond, String genderPreference, String description,String websiteUrl) {
		super();
		this.logo = logo;
		this.companyName = companyName;
		this.role = role;
		this.location = location;
		this.openDate = openDate;
		this.expireDate = expireDate;
		this.skill = skill;
		this.experience = experience;
		this.passoutYear = passoutYear;
		this.responsibility = responsibility;
		this.qualification = qualification;
		this.workMode = workMode;
		this.salary = salary;
		this.bond = bond;
		this.genderPreference = genderPreference;
		this.description = description;
		this.websiteUrl = websiteUrl;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Jobs() {
		super();
	}
	
	
	public String getJobID() {
		return jobID;
	}
	public void setJobID(String jobID) {
		this.jobID = jobID;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getOpenDate() {
		return openDate;
	}
	public void setOpenDate(LocalDate openDate) {
		this.openDate = openDate;
	}
	public LocalDate getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}
	public String getSkill() {
		return skill;
	}
	public void setSkill(String skill) {
		this.skill = skill;
	}
	public String getExperience() {
		return experience;
	}
	public void setExperience(String experience) {
		this.experience = experience;
	}
	public String getPassoutYear() {
		return passoutYear;
	}
	public void setPassoutYear(String passoutYear) {
		this.passoutYear = passoutYear;
	}
	public String getResponsibility() {
		return responsibility;
	}
	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}
	public String getQualification() {
		return qualification;
	}
	public void setQualification(String qualification) {
		this.qualification = qualification;
	}
	public String getWorkMode() {
		return workMode;
	}
	public void setWorkMode(String workMode) {
		this.workMode = workMode;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	public String getBond() {
		return bond;
	}
	public void setBond(String bond) {
		this.bond = bond;
	}
	public String getGenderPreference() {
		return genderPreference;
	}
	public void setGenderPreference(String genderPreference) {
		this.genderPreference = genderPreference;
	}
	
}
