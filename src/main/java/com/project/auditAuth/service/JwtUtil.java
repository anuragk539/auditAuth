package com.project.auditAuth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.project.auditAuth.model.LoginModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	@Value("${jwt.secret}")
	String secretkey;
	@Value("${jwt.expireToken}")
	String tokenExp;
	

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}


	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}


	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}


	private Claims extractAllClaims(String token) {
		String token1 = token.trim().replace("\0xfffd", "");
		return Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token1).getBody();
	}


	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}


	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails.getUsername());
	}


	private String createToken(Map<String, Object> claims, String subject) {
		int tokenExpNum = Integer.parseInt(tokenExp);
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * tokenExpNum))
				.signWith(SignatureAlgorithm.HS256, secretkey).compact();
	}

	
	public Boolean validateToken(LoginModel loginModel) {
		final String username = extractUsername(loginModel.getToken());
		return (username.equals(loginModel.getUsername()) && !isTokenExpired(loginModel.getToken()));
	}


	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}
	
}
