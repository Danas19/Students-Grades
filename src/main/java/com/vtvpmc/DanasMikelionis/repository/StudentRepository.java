package com.vtvpmc.DanasMikelionis.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vtvpmc.DanasMikelionis.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
