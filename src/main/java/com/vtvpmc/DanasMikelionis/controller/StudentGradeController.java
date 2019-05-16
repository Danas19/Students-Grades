package com.vtvpmc.DanasMikelionis.controller;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.vtvpmc.DanasMikelionis.model.Grade;
import com.vtvpmc.DanasMikelionis.model.Student;
import com.vtvpmc.DanasMikelionis.model.createCommands.CreateGradeCommand;
import com.vtvpmc.DanasMikelionis.model.createCommands.CreateStudentCommand;
import com.vtvpmc.DanasMikelionis.model.enums.Subject;
import com.vtvpmc.DanasMikelionis.service.StudentGradeService;

@Controller
@CrossOrigin
@RequestMapping("/api")
public class StudentGradeController {
	@Autowired
	StudentGradeService service;
	
	@GetMapping("/students")
	public ResponseEntity<Collection<Student>> getStudents(@RequestParam(required = false) String sortingTypeString) {
		return ResponseEntity.ok().body(service.getStudents(sortingTypeString));
	}
	
	@GetMapping("/students/order/{orderBy1}/{orderBy2}")
	public ResponseEntity<Collection<Student>> getStudentsQueryOrdered(@PathVariable String orderBy1, @PathVariable String orderBy2) {
		return ResponseEntity.ok().body(this.service.getStudentsQueryOrder(orderBy1, orderBy2));
	}
	
	@GetMapping("/students/{studentId}/grades")
	public ResponseEntity<Collection<Grade>> getStudentGrades(@PathVariable Long studentId) {
		return ResponseEntity.ok().body(this.service.getStudentGrades(studentId));
	}
	
	@PostMapping("/students")
	public ResponseEntity<Student> addStudent(@RequestBody @Valid CreateStudentCommand createStudentCommand) {
		return ResponseEntity.ok().body(this.service.addStudent(createStudentCommand));
	}
	
	@PostMapping("/students/{studentId}/grades")
	public ResponseEntity<Grade> addGrade(@PathVariable Long studentId, @RequestBody @Valid CreateGradeCommand createGradeCommand) {
		Grade gradeAdded = this.service.addGrade(studentId, createGradeCommand);
		return gradeAdded == null ? ((BodyBuilder) ResponseEntity.notFound()).body(gradeAdded) : ResponseEntity.ok().body(gradeAdded);
	}
	
	@GetMapping("/grades")
	public ResponseEntity<Collection<Grade>> getGrades() {
		return ResponseEntity.ok().body(service.getGrades());
	}
	
	@GetMapping("/grades/{subjectString}/average")
	public ResponseEntity<Double> getSubjectAverageGrade(@PathVariable String subjectString) {
		return ResponseEntity.ok().body(this.service.getSubjectAverageGrade(Subject.valueOf(subjectString)));
	}
	
}
