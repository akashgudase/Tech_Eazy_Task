package edu.akash.techeazytask.response;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentResponse {

	private String name;
	private String address;
	private List<SubjectResponse> subjects;
}
