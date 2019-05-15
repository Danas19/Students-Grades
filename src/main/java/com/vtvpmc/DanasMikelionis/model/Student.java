package com.vtvpmc.DanasMikelionis.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	@CreationTimestamp
	@Column(columnDefinition="TIMESTAMP")
	private Date birthDate;
	private int age;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "student", fetch = FetchType.LAZY)
	private List<Grade> grades;
	
	public Student() { }
	
	public Student(String firstName, String lastName, Date birthDate, int age) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.age = age;
	}
	
	
	
	public List<Grade> getGrades() {
		return grades;
	}

	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
	
	public void addGrade(Grade grade) {
		this.grades.add(grade);
	}

	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public Date getBirthDate() {
		return birthDate;
	}
	
	public int getAge() {
		return age;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	public void setAge(int age) {
		this.age = age;
	}
	
	
}
