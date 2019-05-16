package com.vtvpmc.DanasMikelionis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.vtvpmc.DanasMikelionis.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
	@Query("SELECT u FROM Student u ORDER BY :orderBy1, :orderBy2")
	List<Student> getStudentsQueryOrder(
			@Param("orderBy1")String orderBy1,
			@Param("orderBy2")String orderBy2
	);
}
