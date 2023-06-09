package com.jwtsecurity.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwtsecurity.config.JwtService;
import com.jwtsecurity.model.Role;
import com.jwtsecurity.model.UserEntity;
import com.jwtsecurity.repo.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.var;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	
	private final  UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final JwtService jwtService;
	
	private final AuthenticationManager authenticationManager;
	
	public AuthenticationResponse register(RegisterRequest request) {
		var user = UserEntity.builder()
				.firstname(request.getFirstname())
				.lastname(request.getLastname())
				.username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword()))
				.userRole(Role.USER)
				.build();
		 userRepository.save(user);
		 var jwtToken = jwtService.generateToken(user);
		 return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();
	}

	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
				);
		var user = userRepository.findByUsername(request.getUsername())
				.orElseThrow();
		var jwtToken = jwtService.generateToken(user);
		return AuthenticationResponse.builder()
				.token(jwtToken)
				.build();	
	}

}
