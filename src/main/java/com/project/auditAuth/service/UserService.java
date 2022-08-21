package com.project.auditAuth.service;

import com.project.auditAuth.exception.CustomLoginException;
import com.project.auditAuth.model.LoginModel;
import com.project.auditAuth.model.UserCredentials;

public interface UserService {
	public void addUser(UserCredentials userCredentials);

	boolean validateJwtToken(LoginModel loginModel);

	LoginModel validateCredentialsUser(UserCredentials userLoginCredentials) throws CustomLoginException;

	boolean validateJwtToken(String token);

	String getUsernameFromToken(String token);

}
