package com.project.auditAuth.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.auditAuth.exception.CustomLoginException;
import com.project.auditAuth.exception.CustomRegistrationException;
import com.project.auditAuth.model.LoginModel;
import com.project.auditAuth.model.UserCredentials;
import com.project.auditAuth.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	JwtUtil jwtUtilObject;
	
	@Override
	public void addUser(UserCredentials userCredentials) throws CustomRegistrationException {
		try {
			Optional<UserCredentials> existingUser = userRepository.findById(userCredentials.getUsername());
			if(!existingUser.isPresent())
					userRepository.save(userCredentials);
			else
				throw new CustomRegistrationException("User already exists");
			}
		catch(Exception e){
			throw new CustomRegistrationException(e);
			}
		}

	@Override
	public boolean validateJwtToken(LoginModel loginModel){
		return (loginModel.getToken() != null && jwtUtilObject.validateToken(loginModel));
	}
	
	@Override
	public LoginModel validateCredentialsUser(UserCredentials userLoginCredentials) throws CustomLoginException{
		final UserDetails userDetails = loadUserByUsername(userLoginCredentials.getUsername());
		if(userDetails.getPassword().equals(userLoginCredentials.getPassword())) {
			String token = jwtUtilObject.generateToken(userDetails);
			System.out.println(token);
			LoginModel loginModel = new LoginModel();
			loginModel.setToken(token);
			loginModel.setUsername(userDetails.getUsername());
			return loginModel;
		} else {
			throw new CustomLoginException("Invalid Details");
		}
	}

	@Override
	public boolean validateJwtToken(String token){
		return (token != null && jwtUtilObject.validateToken(token));
	}

	@Override
	public String getUsernameFromToken(String token){
		return jwtUtilObject.extractUsername(token);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException, InvalidDataAccessApiUsageException{
		UserCredentials userObject = userRepository.findById(username)
				.orElseThrow(() -> new UsernameNotFoundException(username+" was not found!"));
		return new User(userObject.getUsername(), userObject.getPassword(),new ArrayList<>());
	}

}
