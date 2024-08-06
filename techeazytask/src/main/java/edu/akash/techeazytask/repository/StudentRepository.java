package edu.akash.techeazytask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.akash.techeazytask.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
