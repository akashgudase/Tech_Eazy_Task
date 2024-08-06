package edu.akash.techeazytask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.akash.techeazytask.entity.Subject;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {

}
