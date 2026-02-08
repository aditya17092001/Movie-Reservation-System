package com.movie.reservation.system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.movie.reservation.system.dto.UserSignInDTO;
import com.movie.reservation.system.model.Users;
import com.movie.reservation.system.repository.UserRepo;

@Service
public class UserService {
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	public String verify(UserSignInDTO req) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword()));
		
		if(authentication.isAuthenticated()) {
			Users user = repo.findByEmail(req.getEmail());
			String jwt = jwtService.generateToken(user.getUser_id());
			return jwt;
		}
		
		return "";
	}
}

