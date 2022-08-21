package com.project.auditAuth.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.project.auditAuth.service.JwtUtil;
import com.project.auditAuth.service.UserServiceImpl;

import lombok.extern.slf4j.Slf4j;


@Component
@Slf4j
public class JwtFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private UserServiceImpl userService;
	
	@Value("${messages.logging.tokenNotFound}")
	String tokenNotFound;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,FilterChain filterChain)
	throws ServletException,IOException{
		final String authorizationHeader = request.getHeader("Authorization");
		String username = null;
		String jwtToken = null;
		log.debug(authorizationHeader);
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			jwtToken = authorizationHeader.substring(7);
			username = jwtUtil.extractUsername(jwtToken);
		} else
			log.warn(tokenNotFound);

		if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

			UserDetails userDetails = userService.loadUserByUsername(username);

			if (jwtUtil.validateToken(jwtToken)) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}

		}

		filterChain.doFilter(request, response);
	}

}
