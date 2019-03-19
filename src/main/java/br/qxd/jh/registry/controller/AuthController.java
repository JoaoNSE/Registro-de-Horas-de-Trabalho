package br.qxd.jh.registry.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.qxd.jh.registry.dto.LoginDTO;
import br.qxd.jh.registry.security.JwtTokenProvider;
import br.qxd.jh.registry.service.UserService;

@RestController
public class AuthController {

	@Autowired
	private UserService userService;
	
	@Autowired
    AuthenticationManager authenticationManager;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginDTO login) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						login.getUsername(), 
						login.getPassword())
				);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtTokenProvider.generateToken(authentication);
		
		HttpHeaders headers = new HttpHeaders();
		//headers.add("Authorization", "Bearer " + jwt);
		return ResponseEntity.ok().body("Bearer" + " " + jwt);
	}
}
