package com.vtvpmc.DanasMikelionis.model.createCommands;

import java.sql.Date;

import javax.validation.constraints.NotNull;

public class CreateStudentCommand {
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	private Date birthDate;
	private int age;
	
	public CreateStudentCommand() { }

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
	
	
}
