package com.refugietransaction.tokenRefresh;

import lombok.Data;

@Data
public class TokenRefreshRequest {
	
	private String token;
	
	public TokenRefreshRequest(String token) {
		this.token =token;
	}
	
	public TokenRefreshRequest() {
		
	}
}
