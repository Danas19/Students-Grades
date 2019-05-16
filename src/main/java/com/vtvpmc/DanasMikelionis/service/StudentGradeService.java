package com.vtvpmc.DanasMikelionis.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.BodyBuilder;
import org.springframework.stereotype.Service;

import com.vtvpmc.DanasMikelionis.model.Grade;
import com.vtvpmc.DanasMikelionis.model.Student;
import com.vtvpmc.DanasMikelionis.model.createCommands.CreateGradeCommand;
import com.vtvpmc.DanasMikelionis.model.createCommands.CreateStudentCommand;
import com.vtvpmc.DanasMikelionis.model.enums.SortingType;
import com.vtvpmc.DanasMikelionis.model.enums.Subject;
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
	
	public Collection<Student> getStudents(String sortingTypeString) {
		if (SortingType.valueOf(sortingTypeString) == SortingType.LAST_NAME_ASC_FIRST_NAME_ASC) {
			return this.studentRepository.findAll(
					Sort.by("lastName")
					.and(Sort.by("firstName"))
			);
		} else if (SortingType.valueOf(sortingTypeString) == SortingType.LAST_NAME_DESC_FIRST_NAME_DESC) {
			return this.studentRepository.findAll(
					Sort.by("lastName").descending()
					.and(Sort.by("firstName").descending())
			);
		}
		return this.studentRepository.findAll();
	}
	
	public Collection<Student> getStudentsQueryOrder(String orderBy1, String orderBy2) {
		return this.studentRepository.getStudentsQueryOrder(orderBy1, orderBy2);
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
	
	public Collection<Grade> getGrades() {
		return this.gradeRepository.findAll();
	}
	
	public Student addStudent(CreateStudentCommand createStudentCommand) {
		Student newStudent = new Student(createStudentCommand.getFirstName(), createStudentCommand.getLastName(),
				createStudentCommand.getBirthDate(), createStudentCommand.getAge());
		this.studentRepository.save(newStudent);
		return newStudent;
	}
	
	public Grade addGrade(Long studentId, CreateGradeCommand createGradeCommand) {
		Student oldStudent = this.studentRepository.findById(studentId).orElse(null);
		if (oldStudent == null) {
			return null;
		}
		
		Grade newGrade = new Grade(createGradeCommand.getGrade(), createGradeCommand.getSchoolSubject());
		oldStudent.addGrade(newGrade);
		newGrade.setStudent(oldStudent);
		
		this.gradeRepository.save(newGrade);
		
		return newGrade;
	}
	
	public Collection<Grade> getStudentGrades(Long studentId) {
		return this.studentRepository.findById(studentId).orElse(null).getGrades();
	}
}
