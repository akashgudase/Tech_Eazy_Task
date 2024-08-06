package edu.akash.techeazytask.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

	private String name;
	private String password;
	private String role;
	private String token;

}
