package com.refugietransaction.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.refugietransaction.dto.UserDto;
import com.refugietransaction.dto.auth.AuthenticationRequest;
import com.refugietransaction.dto.auth.AuthenticationResponse;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.model.User;
import com.refugietransaction.repository.UserRepository;
import com.refugietransaction.services.UserService;
import com.refugietransaction.utils.JwtUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	
	@Autowired
	public UserServiceImpl(UserRepository userRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
	}

	@Override
	public UserDto findById(Long id) {
		
		if(id == null) {
			log.error("User ID is null");
		}

		return userRepository.findById(id)
				.map(UserDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun utilisateur avec l'ID = " +id+ " n' a ete trouve dans la BDD ",
						ErrorCodes.USER_NOT_FOUND)
				);
	}

	@Override
	@Transactional
	public void enableUser(Long userId) {

		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setIsUserActive(true);
			userRepository.save(user);
		} else {
			throw new EntityNotFoundException("Aucune utilisateur avec l'ID = " +userId+ " n' a ete trouve dans la BDD",ErrorCodes.USER_NOT_FOUND);
		}

	}

	@Override
	@Transactional
	public void desableUser(Long userId) {

		Optional<User> optionalUser = userRepository.findById(userId);
		if (optionalUser.isPresent()) {
			User user = optionalUser.get();
			user.setIsUserActive(false);
			userRepository.save(user);
		} else {
			throw new EntityNotFoundException("Aucune utilisateur avec l'ID = " +userId+ " n' a ete trouve dans la BDD",ErrorCodes.USER_NOT_FOUND);
		}

	}

	@Override
	public List<UserDto> findAll() {
		
		return userRepository.findAll().stream()
				.map(UserDto::fromEntity)
				.collect(Collectors.toList());
	}

	@Override
	public AuthenticationResponse authenticate(AuthenticationRequest request) {
		
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
		final String token = jwtUtils.generateToken(authentication);
		return AuthenticationResponse.builder()
				.token(token)
				.build();
	}

}
