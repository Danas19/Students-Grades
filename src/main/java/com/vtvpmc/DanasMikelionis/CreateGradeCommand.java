package com.vtvpmc.DanasMikelionis;

import javax.validation.constraints.NotNull;

import com.vtvpmc.DanasMikelionis.model.Subject;

public class CreateGradeCommand {
	private int grade;
	@NotNull
	private Subject schoolSubject;
	
	public CreateGradeCommand() { }

	public int getGrade() {
		return grade;
	}
	
	public Subject getSchoolSubject() {
		return schoolSubject;
	}
	
	
}
