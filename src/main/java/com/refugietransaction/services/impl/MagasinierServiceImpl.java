package com.refugietransaction.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.refugietransaction.dto.CampDto;
import com.refugietransaction.dto.MagasinierDto;
import com.refugietransaction.dto.SupplierDto;
import com.refugietransaction.dto.UserDto;
import com.refugietransaction.exceptions.EntityNotFoundException;
import com.refugietransaction.exceptions.ErrorCodes;
import com.refugietransaction.exceptions.InvalidEntityException;
import com.refugietransaction.model.Magasinier;
import com.refugietransaction.model.User;
import com.refugietransaction.model.UserRoleEnum;
import com.refugietransaction.repository.MagasinierRepository;
import com.refugietransaction.repository.UserRepository;
import com.refugietransaction.services.MagasinierService;
import com.refugietransaction.services.MailSenderService;
import com.refugietransaction.validator.MagasinierValidator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MagasinierServiceImpl implements MagasinierService {
	
	private final MagasinierRepository magasinierRepository;
	private final UserRepository userRepository;
	private final MailSenderService mailService;
	
	@Autowired
	public MagasinierServiceImpl(MagasinierRepository magasinierRepository, UserRepository userRepository, MailSenderService mailService) {
		this.magasinierRepository = magasinierRepository;
		this.userRepository = userRepository;
		this.mailService = mailService;
	}

	@Override
	public MagasinierDto save(MagasinierDto magasinierDto) {

		List<String> errors = MagasinierValidator.validate(magasinierDto);
		if(!errors.isEmpty()) {
			log.error("Magasinier is not valid {}", magasinierDto);
			throw new InvalidEntityException("Le magasinier n'est pas valide", ErrorCodes.MAGASINIER_NOT_VALID, errors);
		}

		if (magasinierDto.getId() ==null || magasinierDto.getId().compareTo(0L) == 0){

			if (userAlreadyExists(magasinierDto.getUser().getUserEmail())){

				throw new InvalidEntityException("Un autre utilisateur avec le meme email existe deja", ErrorCodes.USER_ALREADY_EXISTS,
						Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la BDD"));

			}
			if(phoneNumberAlreadyExists(magasinierDto.getUser().getUserPhoneNumber())) {
				throw new InvalidEntityException("Un autre utilisateur avec le meme numero de telephone existe deja", ErrorCodes.USER_PHONE_NUMBER_ALREADY_EXISTS,
						Collections.singletonList("Un autre utilisateur avec le meme numero de telephone existe deja dans la BDD"));
			}

			String noEncrypted_password=generateCommonLangPassword();
			magasinierDto.getUser().setUserRoleEnum(UserRoleEnum.MAGASINIER);
			magasinierDto.getUser().setIsUserActive(true);
			magasinierDto.getUser().setUserPassword(generateCommonLangPassword());

			UserDto savedUser = UserDto.fromEntity(
					userRepository.save(UserDto.toEntity(magasinierDto.getUser()))
			);

			MagasinierDto magasinierDto1 = fromUser(savedUser,magasinierDto.getSupplier(),magasinierDto.getCamp());
			MagasinierDto savedMagasinier = MagasinierDto.fromEntity(
					magasinierRepository.save(MagasinierDto.toEntity(magasinierDto1))
			);

            //send email
			mailService.sendNewMail(magasinierDto.getUser().getUserEmail().trim(), "User password", "Dear user this is your password "+noEncrypted_password);

			return savedMagasinier;
		}

		//update section
		User existingUser=userRepository.findUserByIdUser(magasinierDto.getUser().getId());
		if (existingUser !=null && !existingUser.getUserEmail().equals(magasinierDto.getUser().getUserEmail())){

			if (userAlreadyExists(magasinierDto.getUser().getUserEmail())){
				throw new InvalidEntityException("Un autre utilisateur avec le meme email existe deja", ErrorCodes.USER_ALREADY_EXISTS,
						Collections.singletonList("Un autre utilisateur avec le meme email existe deja dans la BDD"));

			}
		}
		if (existingUser !=null && !existingUser.getUserPhoneNumber().equals(magasinierDto.getUser().getUserPhoneNumber())){

			if(phoneNumberAlreadyExists(magasinierDto.getUser().getUserPhoneNumber())) {
				throw new InvalidEntityException("Un autre utilisateur avec le meme numero de telephone existe deja", ErrorCodes.USER_PHONE_NUMBER_ALREADY_EXISTS,
						Collections.singletonList("Un autre utilisateur avec le meme numero de telephone existe deja dans la BDD"));
			}
		}

		String pswd="";
		if (!magasinierDto.getUser().getUserEmail().equals(userEmail(magasinierDto.getUser().getId())) ){

			pswd=generateCommonLangPassword();
		}
		else{
			//get existing password
			pswd=userRepository.findUserByIdUser(magasinierDto.getUser().getId()).getUserPassword();
		}


		Magasinier magasinier=magasinierRepository.findMagasinierById(magasinierDto.getId());
		User user=magasinier.getUser();
		user.setUserFullName(magasinierDto.getUser().getUserFullName());
		user.setUserEmail(magasinierDto.getUser().getUserEmail());
		user.setUserPhoneNumber(magasinierDto.getUser().getUserPhoneNumber());
		user.setUserPassword(pswd);
		userRepository.save(user);

		MagasinierDto savedMagasinier=MagasinierDto.fromEntity(magasinierRepository.save(MagasinierDto.toEntity(magasinierDto)));

		if (!magasinierDto.getUser().getUserEmail().equals(userEmail(magasinierDto.getUser().getId())) ){

			//send email
			mailService.sendNewMail(magasinierDto.getUser().getUserEmail().trim(), "Passord", "Dear user, this is your password "+pswd);
		}

		return savedMagasinier;
	}

	private MagasinierDto fromUser(UserDto dto,SupplierDto supplierDto,CampDto campDto) {

		return MagasinierDto.builder()
				.user(dto)
				.supplier(supplierDto)
				.camp(campDto)
				.build();
	}

	private boolean phoneNumberAlreadyExists(String phoneNumber) {
		Optional<User> user = userRepository.findUserByPhoneNumber(phoneNumber);
		return user.isPresent();
	}

	private boolean userAlreadyExists(String email) {
		Optional<User> user = userRepository.findUserByEmail(email);
		return user.isPresent();
	}

	public String generateCommonLangPassword() {
		String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
		String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
		String numbers = RandomStringUtils.randomNumeric(2);
		String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
		String totalChars = RandomStringUtils.randomAlphanumeric(2);
		String combinedChars = upperCaseLetters.concat(lowerCaseLetters)
				.concat(numbers)
				.concat(specialChar)
				.concat(totalChars);
		List<Character> pwdChars = combinedChars.chars()
				.mapToObj(c -> (char) c)
				.collect(Collectors.toList());
		Collections.shuffle(pwdChars);
		return pwdChars.stream()
				.collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();
	}

	private String userEmail(Long id){
		Optional<User> utilisateur = userRepository.findById(id);

		String user_email="";
		if(utilisateur.isPresent()) {
			User existingUser = utilisateur.get();
			user_email = existingUser.getUserEmail();
			//operate on existingCustomer
		}

		return user_email;

	}

	private String userPhoneNumber(Long id){
		Optional<User> utilisateur = userRepository.findById(id);

		String user_phone_number="";
		if(utilisateur.isPresent()) {
			User existingUser = utilisateur.get();
			user_phone_number = existingUser.getUserPhoneNumber();
			//operate on existingCustomer
		}

		return user_phone_number;
	}

	@Override
	public MagasinierDto findById(Long id) {
		if(id == null) {
			log.error("Magasinier ID is null");
		}
		return magasinierRepository.findById(id)
				.map(MagasinierDto::fromEntity)
				.orElseThrow(()->new EntityNotFoundException(
						"Aucun agent avec l'ID = " +id+ " n'a ete trouve dans la BDD", 
						ErrorCodes.MAGASINIER_NOT_FOUND));
	}

	@Override
	public Page<MagasinierDto> findSupplierMagasiniers(Long idSupplier, String search, Pageable pageable) {

		Page<Magasinier> magasiniers;
		if (search != null) {
			magasiniers = magasinierRepository.findByNameEmailPhoneLike(idSupplier,search, pageable);
		} else {
			magasiniers = magasinierRepository.findAllSupplierMagasiniers(idSupplier,pageable);
		}

		return magasiniers.map(MagasinierDto::fromEntity);
	}


	@Override
	public void delete(Long id) {
		if(id == null) {
			log.error("Agent ID is null");
		}

		magasinierRepository.deleteById(id);
		
	}

	@Override
	public List<MagasinierDto> findAll() {
		
		return magasinierRepository.findAll().stream()
				.map(MagasinierDto::fromEntity)
				.collect(Collectors.toList());
	}
}
