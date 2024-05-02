package com.refugietransaction.services;

import java.util.List;

import com.refugietransaction.dto.UserDto;

public interface UserService {
	
	UserDto findById(Long id);
	
	List<UserDto> findAll();

	void enableUser(Long userId);

	void desableUser(Long userId);
}
