package com.refugietransaction.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.ChangerMotDePasseUtilisateurDto;
import com.refugietransaction.dto.UserDto;
import com.refugietransaction.dto.auth.AuthenticationRequest;
import com.refugietransaction.dto.auth.AuthenticationResponse;

public interface UserService {
	
	UserDto findById(Long id);
	
	List<UserDto> findAll();

	void enableUser(Long userId);

	void desableUser(Long userId);
	
	AuthenticationResponse authenticate(AuthenticationRequest request);
	
	UserDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto);

	AuthenticationResponse refreshToken(String refreshToken);
}
