package com.vtvpmc.DanasMikelionis.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vtvpmc.DanasMikelionis.CreateGradeCommand;
import com.vtvpmc.DanasMikelionis.CreateStudentCommand;
import com.vtvpmc.DanasMikelionis.model.Grade;
import com.vtvpmc.DanasMikelionis.model.Student;
import com.vtvpmc.DanasMikelionis.model.Subject;
import com.vtvpmc.DanasMikelionis.service.StudentGradeService;

@Controller
@CrossOrigin
@RequestMapping("/api")
public class StudentGradeController {
	@Autowired
	StudentGradeService service;
	
	@GetMapping("/students")
	public ResponseEntity<Collection<Student>> getStudents(@RequestParam(required = false) String sortingTypeString) {
		return service.getStudents(sortingTypeString);
	}
	
	@GetMapping("/students/order/{orderBy1}/{orderBy2}")
	public ResponseEntity<Collection<Student>> getStudentsQueryOrdered(@PathVariable String orderBy1, @PathVariable String orderBy2) {
		return this.service.getStudentsQueryOrder(orderBy1, orderBy2);
	}
	
	@GetMapping("/students/{studentId}/grades")
	public ResponseEntity<Collection<Grade>> getStudentGrades(@PathVariable Long studentId) {
		return this.service.getStudentGrades(studentId);
	}
	
	@PostMapping("/students")
	public ResponseEntity<Student> addStudent(@RequestBody @Valid CreateStudentCommand createStudentCommand) {
		return this.service.addStudent(createStudentCommand);
	}
	
	@PostMapping("/students/{studentId}/grades")
	public ResponseEntity<Grade> addGrade(@PathVariable Long customerId, @RequestBody @Valid CreateGradeCommand createGradeCommand) {
		return this.service.addGrade(customerId, createGradeCommand);
	}
	
	@GetMapping("/grades")
	public ResponseEntity<Collection<Grade>> getGrades() {
		return service.getGrades();
	}
	
	@GetMapping("/grades/{subjectString}/average")
	public ResponseEntity<Double> getSubjectAverageGrade(@PathVariable String subjectString) {
		return ResponseEntity.ok().body(this.service.getSubjectAverageGrade(Subject.valueOf(subjectString)));
	}
	
}
