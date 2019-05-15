package com.vtvpmc.DanasMikelionis.model;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Grade {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int grade;
	@CreationTimestamp
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
	private Date gradeDate;
	@Enumerated(EnumType.STRING)
	private Subject schoolSubject;
	
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "student_id")
	private Student student;
	
	public Grade(int grade, Subject subject) {
		super();
		this.grade = grade;
		this.schoolSubject = subject;
	}
	
	public Grade() { }
	
	public Long getStudentId() {
		return this.student.getId();
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Long getId() {
		return id;
	}

	public int getGrade() {
		return grade;
	}

	public Date getGradeDate() {
		return gradeDate;
	}

	public Subject getSchoolSubject() {
		return schoolSubject;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	public void setGradeDate(Date gradeDate) {
		this.gradeDate = gradeDate;
	}

	public void setSchoolSubject(Subject subject) {
		this.schoolSubject = subject;
	}
	
	
	
}
