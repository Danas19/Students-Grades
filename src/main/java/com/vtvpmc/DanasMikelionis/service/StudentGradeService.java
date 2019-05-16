package com.vtvpmc.DanasMikelionis.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Service;

import com.vtvpmc.DanasMikelionis.CreateGradeCommand;
import com.vtvpmc.DanasMikelionis.CreateStudentCommand;
import com.vtvpmc.DanasMikelionis.model.Grade;
import com.vtvpmc.DanasMikelionis.model.SortingType;
import com.vtvpmc.DanasMikelionis.model.Student;
import com.vtvpmc.DanasMikelionis.model.Subject;
import com.vtvpmc.DanasMikelionis.repository.GradeRepository;
import com.vtvpmc.DanasMikelionis.repository.StudentRepository;

@Service
public class StudentGradeService {
	private StudentRepository studentRepository;
	private GradeRepository gradeRepository;
	
	public StudentGradeService(StudentRepository studentRepository, GradeRepository gradeRepository) {
		super();
		this.studentRepository = studentRepository;
		this.gradeRepository = gradeRepository;
	}
	
	public ResponseEntity<Collection<Student>> getStudents(String sortingTypeString) {
		if (SortingType.valueOf(sortingTypeString) == SortingType.LAST_NAME_ASC_FIRST_NAME_ASC) {
			return ResponseEntity.ok().body(this.studentRepository.findAll(
					Sort.by("lastName")
					.and(Sort.by("firstName")))
			);
		} else if (SortingType.valueOf(sortingTypeString) == SortingType.LAST_NAME_DESC_FIRST_NAME_DESC) {
			return ResponseEntity.ok().body(this.studentRepository.findAll(
					Sort.by("lastName").descending()
					.and(Sort.by("firstName").descending())
			));
		}
		return ResponseEntity.ok().body(this.studentRepository.findAll());
	}
	
	public ResponseEntity<Collection<Student>> getStudentsQueryOrder(String orderBy1, String orderBy2) {
		return ResponseEntity.ok().body(this.studentRepository.getStudentsQueryOrder(orderBy1, orderBy2));
	}
	
	public Double getSubjectAverageGrade(Subject subject) {
		List<Integer> grades = this.gradeRepository.findAll().stream()
				.filter(g -> g.getSchoolSubject() == subject)
				.map(g -> g.getGrade())
				.collect(Collectors.toList());
		
		if (grades.size() == 0) {
			return null;
		}
		return (double) grades.stream()
			.mapToInt(g -> g.intValue())
		.sum()
		/
		grades
		.size();
	}
	
	public ResponseEntity<Collection<Grade>> getGrades() {
		return ResponseEntity.ok().body(this.gradeRepository.findAll());
	}
	
	public ResponseEntity<Student> addStudent(CreateStudentCommand createStudentCommand) {
		Student newStudent = new Student(createStudentCommand.getFirstName(), createStudentCommand.getLastName(),
				createStudentCommand.getBirthDate(), createStudentCommand.getAge());
		this.studentRepository.save(newStudent);
		return new ResponseEntity<Student>(newStudent, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Grade> addGrade(Long customerId, CreateGradeCommand createGradeCommand) {
		Student oldStudent = this.studentRepository.findById(customerId).orElse(null);
		if (oldStudent == null) {
			return ((BodyBuilder) ResponseEntity.notFound()).body(null);
		}
		
		Grade newGrade = new Grade(createGradeCommand.getGrade(), createGradeCommand.getSchoolSubject());
		newGrade.setStudent(oldStudent);
		oldStudent.addGrade(newGrade);
		
		return new ResponseEntity<Grade>(newGrade, HttpStatus.CREATED);
	}
	
	public ResponseEntity<Collection<Grade>> getStudentGrades(Long studentId) {
		return ResponseEntity.ok().body(this.studentRepository.findById(studentId).orElse(null).getGrades());
	}
}
