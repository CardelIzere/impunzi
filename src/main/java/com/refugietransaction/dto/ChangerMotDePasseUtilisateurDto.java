package com.refugietransaction.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangerMotDePasseUtilisateurDto {
	
	private Integer id;
	private String currentPassword;
	private String motDePasse;
	private String confirmMotDePasse;
}
