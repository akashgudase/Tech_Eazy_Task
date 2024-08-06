package edu.akash.techeazytask.security;

import java.security.Key;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.akash.techeazytask.entity.AppUser;
import edu.akash.techeazytask.repository.AppUserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	private static final String SECRET_KEY = "QWERTYUIOPASDFGHJKLZXCVBNMrgqrgwerbuqweygwueygweurgwiehurtiuerhteurtheieurthe8tgeuyreriegrhuyergter";

	@Autowired
	private AppUserRepository appUserRepository;

	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
	}

	public String getToken(String userName) {
		Date dateOfIssue = new Date(System.currentTimeMillis());
		Date dateOfExpiry = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
		return Jwts.builder().setIssuedAt(dateOfIssue).setExpiration(dateOfExpiry).setSubject(userName)
				.signWith(key(), SignatureAlgorithm.HS256).compact();
	}

	private String extractUserName(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}

	public boolean isTokenValid(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getExpiration()
				.after(new Date(System.currentTimeMillis()));
	}

	public String extractRole(String token) {
		String role = null;
		String userName = extractUserName(token);
		Optional<AppUser> optionalUser = appUserRepository.findAppUserByName(userName);
		if (optionalUser.isPresent()) {
			role = optionalUser.get().getRole();
		}
		return role;
	}

}
