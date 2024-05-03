package com.refugietransaction.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.refugietransaction.dto.UserDto;

public interface UserService {
	
	UserDto findById(Long id);
	
	Page<UserDto> findByUserFullNameLike(String search, Pageable pageable);

	void enableUser(Long userId);

	void desableUser(Long userId);
}
