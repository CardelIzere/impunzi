package com.refugietransaction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RestController;

import com.refugietransaction.controller.api.UserApi;
import com.refugietransaction.dto.ChangerMotDePasseUtilisateurDto;
import com.refugietransaction.dto.UserDto;
import com.refugietransaction.services.UserService;

@RestController
public class UserController implements UserApi {
	
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDto findById(Long id) {
		
		return userService.findById(id);
	}

	@Override
	public void enableUser(Long id) {
		userService.enableUser(id);
	}

	@Override
	public void desableUser(Long id) {

		userService.desableUser(id);
	}

	@Override
	public List<UserDto> findAll() {
		
		return userService.findAll();
	}

	@Override
	public UserDto changerMotDePasse(ChangerMotDePasseUtilisateurDto dto) {
		
		return userService.changerMotDePasse(dto);
	}
}
