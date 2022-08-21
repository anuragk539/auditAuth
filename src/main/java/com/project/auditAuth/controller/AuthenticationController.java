package com.project.auditAuth.controller;

import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.crypto.engines.ISAACEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.project.auditAuth.exception.CustomLoginException;
import com.project.auditAuth.exception.CustomRegistrationException;
import com.project.auditAuth.model.AuthResponse;
import com.project.auditAuth.model.LoginModel;
import com.project.auditAuth.model.UserCredentials;
import com.project.auditAuth.service.UserServiceImpl;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice.This;

@Slf4j
@RestController
public class AuthenticationController {
	
	@Autowired
	UserServiceImpl userService;
	@Value("${messages.logging.userCreated}")
	String userCreatedMessage;
	@Value("${messages.logging.loginSuccessful}")
	String loginSuccessful;
	@Value("${messages.logging.validJwtToken}")
	String validTokenMessage;
	@Value("${messages.logging.invalidJwtToken}")
	String invalidTokenMessage;
	
	@GetMapping("/test")
	public String test() {
		return "This is test target";
	}
	
	@PostMapping("/register")
	public ResponseEntity<AuthResponse> registerUser(@RequestBody UserCredentials userRegistrationCredentials)
		throws CustomRegistrationException {
		userService.addUser(userRegistrationCredentials);
		AuthResponse authResponse = new AuthResponse(userRegistrationCredentials.getUsername(),true);
		log.info(userCreatedMessage);
		return new ResponseEntity<AuthResponse>(authResponse, HttpStatus.CREATED);
	}
	
	@PostMapping("/login")
	public ResponseEntity<LoginModel> loginUser(@RequestBody UserCredentials userLoginCredentials)
		throws CustomLoginException, UsernameNotFoundException, ExpiredJwtException, InvalidDataAccessApiUsageException {
		LoginModel loginResponse = userService.validateCredentialsUser(userLoginCredentials);
		log.info(loginSuccessful);
		return new ResponseEntity<LoginModel>(loginResponse,HttpStatus.OK);
	}
	
	
	@GetMapping("/validate")
	public ResponseEntity<AuthResponse> validateUser(@RequestHeader("Authorization") String token)
		throws ExpiredJwtException {
		log.debug("Token:"+token);
		AuthResponse authResponse = new AuthResponse(null,false);
		if(userService.validateJwtToken(token)) {
			log.info(validTokenMessage);
			authResponse.setUid(userService.getUsernameFromToken(token));
			authResponse.setValid(true);
			return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
		} else {
			log.info(invalidTokenMessage);
			return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.UNAUTHORIZED);
		}
	}

	
}
