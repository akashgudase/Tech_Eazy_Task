package edu.akash.techeazytask.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.akash.techeazytask.entity.Student;
import edu.akash.techeazytask.entity.Subject;
import edu.akash.techeazytask.repository.StudentRepository;
import edu.akash.techeazytask.repository.SubjectRepository;
import edu.akash.techeazytask.request.StudentRequest;
import edu.akash.techeazytask.response.StudentResponse;
import edu.akash.techeazytask.response.SubjectResponse;
import edu.akash.techeazytask.security.JwtService;

@RestController
public class StudentController {

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private JwtService jwtService;

	@PostMapping(path = "/student")
	public ResponseEntity<Object> addStudent(@RequestBody StudentRequest studentRequest,
			@RequestHeader(name = "Authorization") String header) {
		String token = header.substring(7);
		if (jwtService.extractRole(token).equals("STUDENT") && jwtService.isTokenValid(token)) {
			Student student = new Student();
			student.setName(studentRequest.getName());
			student.setAddress(studentRequest.getAddress());
			student.setSubjects(new ArrayList<Subject>());
			Student addedStudent = studentRepository.save(student);
			StudentResponse studentResponse = new StudentResponse();
			studentResponse.setName(addedStudent.getName());
			studentResponse.setAddress(addedStudent.getAddress());
			List<Subject> subjects = addedStudent.getSubjects();
			List<SubjectResponse> subjectResponses = new ArrayList<>();
			for (Subject subject : subjects) {
				SubjectResponse subjectResponse = new SubjectResponse();
				subjectResponse.setName(subject.getName());
				subjectResponses.add(subjectResponse);
			}
			studentResponse.setSubjects(subjectResponses);
			return new ResponseEntity<Object>(studentResponse, HttpStatus.CREATED);
		} else
			return new ResponseEntity<Object>("Access denied..", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping(path = "/students")
	public ResponseEntity<Object> findAllStudents(@RequestHeader(name = "Authorization") String header) {
		String token = header.substring(7);
		if (jwtService.extractRole(token).equals("ADMIN") && jwtService.isTokenValid(token)) {
			List<Student> students = studentRepository.findAll();
			if (students.size() > 0) {
				List<StudentResponse> studentResponses = new ArrayList<StudentResponse>();
				for (Student student : students) {
					StudentResponse studentResponse = new StudentResponse();
					studentResponse.setName(student.getName());
					studentResponse.setAddress(student.getAddress());
					List<Subject> subjects = student.getSubjects();
					List<SubjectResponse> subjectResponses = new ArrayList<>();
					for (Subject subject : subjects) {
						SubjectResponse subjectResponse = new SubjectResponse();
						subjectResponse.setName(subject.getName());
						subjectResponses.add(subjectResponse);
					}
					studentResponse.setSubjects(subjectResponses);
					studentResponses.add(studentResponse);
				}
				return new ResponseEntity<Object>(studentResponses, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<Object>("Empty list..", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<Object>("Access denied..", HttpStatus.UNAUTHORIZED);
	}

	@PutMapping(path = "/student")
	public ResponseEntity<Object> updateStudent(@RequestParam(name = "student") long studentId,
			@RequestParam(name = "subject") long subjectId, @RequestHeader(name = "Authorization") String header) {
		String token = header.substring(7);
		if (jwtService.extractRole(token).equals("ADMIN") && jwtService.isTokenValid(token)) {
			Student student = studentRepository.findById(studentId).get();
			Subject subject = subjectRepository.findById(subjectId).get();
			List<Subject> subjects = student.getSubjects();
			subjects.add(subject);
			student.setSubjects(subjects);
			Student updatedStudent = studentRepository.save(student);
			StudentResponse studentResponse = new StudentResponse();
			studentResponse.setName(updatedStudent.getName());
			studentResponse.setAddress(updatedStudent.getAddress());
			List<Subject> updatedSubjects = updatedStudent.getSubjects();
			List<SubjectResponse> subjectResponses = new ArrayList<>();
			for (Subject updatedSubject : updatedSubjects) {
				SubjectResponse subjectResponse = new SubjectResponse();
				subjectResponse.setName(updatedSubject.getName());
				subjectResponses.add(subjectResponse);
			}
			studentResponse.setSubjects(subjectResponses);
			return new ResponseEntity<Object>(studentResponse, HttpStatus.CREATED);
		} else
			return new ResponseEntity<Object>("Access denied..", HttpStatus.UNAUTHORIZED);
	}

}
