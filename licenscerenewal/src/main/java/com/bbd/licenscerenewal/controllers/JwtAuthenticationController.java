package com.bbd.licenscerenewal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.bbd.licenscerenewal.config.JwtTokenUtil;
import com.bbd.licenscerenewal.models.JwtRequest;
import com.bbd.licenscerenewal.models.JwtResponse;
import com.bbd.licenscerenewal.services.JwtUserDetailsService;

import com.bbd.licenscerenewal.models.UserDB;

import javax.validation.Valid;
import java.sql.SQLException;
import java.sql.SQLTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
		private PasswordEncoder bcryptEncoder;

	@PostMapping(value = "/register", headers = "X-API-VERSION=1")
	public ResponseEntity<String> createUserInDB(@RequestBody UserDB user) throws SQLException {
		String result = userDetailsService.add(user);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PostMapping(value = "/authenticate", headers = "X-API-VERSION=1")
	public ResponseEntity<JwtResponse> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String encryptedPass = bcryptEncoder.encode(authenticationRequest.getPassword());

		if (bcryptEncoder.matches(authenticationRequest.getPassword(), userDetails.getPassword())) {
			final String token = jwtTokenUtil.generateToken(userDetails);
			return new ResponseEntity<>(new JwtResponse(token), HttpStatus.OK);
		}
		throw new UsernameNotFoundException("User not found with username: " + authenticationRequest.getUsername());
	}
}