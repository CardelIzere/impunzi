package com.refugietransaction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.AuthenticationApi;
import com.refugietransaction.dto.auth.AuthenticationRequest;
import com.refugietransaction.dto.auth.AuthenticationResponse;
import com.refugietransaction.services.UserService;
import com.refugietransaction.tokenRefresh.TokenRefreshRequest;
import com.refugietransaction.tokenRefresh.TokenRefreshResponse;

@RestController
public class AuthenticationController implements AuthenticationApi {
	
	private final UserService userService;
	
	@Autowired
	public AuthenticationController(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
		
		return ResponseEntity.ok(userService.authenticate(request));
	}

	@Override
	public ResponseEntity<TokenRefreshResponse> refreshToken(String token) {
		
		TokenRefreshRequest request = new TokenRefreshRequest();
		request.setToken(token);
		return ResponseEntity.ok(userService.refreshToken(request));
	}

}
