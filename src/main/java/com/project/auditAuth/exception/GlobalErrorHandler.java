package com.project.auditAuth.exception;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.auditAuth.model.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalErrorHandler {
	
	@Value("${messages.exceptions.registrationFailed}")
	String registrationFailedMessage;
	@Value("${messages.exceptions.loginFailed}")
	String loginFailedMessage;
	@Value("${messages.logging.end}")
	String exitMessage;
	@Value("${messages.logging.start}")
	String entryMessage;
	
	@ExceptionHandler(CustomRegistrationException.class)
	public ResponseEntity<ErrorResponse> handleIdNotFoundexception(CustomRegistrationException exception) {
		log.info(entryMessage);
		ErrorResponse response = new ErrorResponse(LocalDateTime.now(), HttpStatus.NOT_ACCEPTABLE,
				registrationFailedMessage, exception.getMessage());
		log.info(exitMessage);
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(CustomLoginException.class)
	public ResponseEntity<ErrorResponse> handleIdNotFoundexception(CustomLoginException exception) {
		log.info(entryMessage);
		ErrorResponse response = new ErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, loginFailedMessage,
				exception.getMessage());
		log.info(exitMessage);
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ErrorResponse> handleIdNotFoundexception(UsernameNotFoundException exception) {
		log.info(entryMessage);
		ErrorResponse response = new ErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, loginFailedMessage,
				exception.getMessage());
		log.info(exitMessage);
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(InvalidDataAccessApiUsageException.class)
	public ResponseEntity<ErrorResponse> handleIdNotFoundexception(InvalidDataAccessApiUsageException exception) {
		log.info(entryMessage);
		ErrorResponse response = new ErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, loginFailedMessage,
				exception.getMessage());
		log.info(exitMessage);
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<ErrorResponse> handleIdNotFoundexception(ExpiredJwtException exception) {
		log.info(entryMessage);
		ErrorResponse response = new ErrorResponse(LocalDateTime.now(), HttpStatus.UNAUTHORIZED, loginFailedMessage,
				exception.getMessage());
		log.info(exitMessage);
		return new ResponseEntity<ErrorResponse>(response, HttpStatus.UNAUTHORIZED);
	}
}
