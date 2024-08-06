package edu.akash.techeazytask.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import edu.akash.techeazytask.entity.Subject;
import edu.akash.techeazytask.repository.SubjectRepository;
import edu.akash.techeazytask.request.SubjectRequest;
import edu.akash.techeazytask.response.SubjectResponse;
import edu.akash.techeazytask.security.JwtService;

@RestController
public class SubjectController {

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private JwtService jwtService;

	@PostMapping(path = "/subject")
	public ResponseEntity<Object> addSubject(@RequestBody SubjectRequest subjectRequest,
			@RequestHeader(name = "Authorization") String header) {
		String token = header.substring(7);
		if (jwtService.extractRole(token).equals("ADMIN") && jwtService.isTokenValid(token)) {
			Subject subject = new Subject();
			subject.setName(subjectRequest.getName());
			Subject addedSubject = subjectRepository.save(subject);
			SubjectResponse subjectResponse = new SubjectResponse();
			subjectResponse.setName(addedSubject.getName());
			return new ResponseEntity<Object>(subjectResponse, HttpStatus.CREATED);
		} else
			return new ResponseEntity<Object>("Access denied..", HttpStatus.UNAUTHORIZED);
	}

	@GetMapping(path = "/subjects")
	public ResponseEntity<Object> findAllSubjects(@RequestHeader(name = "Authorization") String header) {
		String token = header.substring(7);
		if (jwtService.extractRole(token).equals("ADMIN")
				|| jwtService.extractRole(token).equals("STUDENT") && jwtService.isTokenValid(token)) {
			List<Subject> subjects = subjectRepository.findAll();
			if (subjects.size() > 0) {
				List<SubjectResponse> subjectResponses = new ArrayList<SubjectResponse>();
				for (Subject subject : subjects) {
					SubjectResponse subjectResponse = new SubjectResponse();
					subjectResponse.setName(subject.getName());
					subjectResponses.add(subjectResponse);
				}
				return new ResponseEntity<Object>(subjectResponses, HttpStatus.FOUND);
			} else {
				return new ResponseEntity<Object>("Empty list..", HttpStatus.NOT_FOUND);
			}
		} else
			return new ResponseEntity<Object>("Access denied..", HttpStatus.UNAUTHORIZED);
	}

}
