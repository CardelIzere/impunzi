package com.refugietransaction.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.ChangerMotDePasseUtilisateurDto;
import com.refugietransaction.dto.UserDto;
import com.refugietransaction.dto.auth.AuthenticationRequest;
import com.refugietransaction.dto.auth.AuthenticationResponse;
import com.refugietransaction.tokenRefresh.TokenRefreshRequest;
import com.refugietransaction.tokenRefresh.TokenRefreshResponse;

public interface UserService {
	
	UserDto findById(Long id);
	
	List<UserDto> findAll();

	void enableUser(Long userId);

	void desableUser(Long userId);
	
	AuthenticationResponse authenticate(AuthenticationRequest request);
	
	TokenRefreshResponse refreshToken(TokenRefreshRequest request);
	
	UserDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);
}
