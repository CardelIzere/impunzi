package com.refugietransaction.exceptions;

public enum ErrorCodes {
	
	  PRODUCT_NOT_FOUND(1000),
	  PRODUCT_NOT_VALID(1001),
	  PRODUCT_ALREADY_IN_USE(1002),
	  PRODUCT_ALREADY_EXISTS(1003),

	  CAMP_NOT_FOUND(2000),
	  CAMP_NOT_VALID(2001),
	  CAMP_ALREADY_IN_USE(2002),
	  CAMP_ALREADY_EXISTS(2003),

	  MENAGE_NOT_FOUND(3000),
	  MENAGE_NOT_VALID(3001),
	  MENAGE_ALREADY_IN_USE(3002),
	  MENAGE_ALREADY_EXISTS(3003),
	  MENAGE_PHONE_NUMBER_ALREADY_EXISTS(3004),

	  MVT_STK_NOT_FOUND(4000),
	  MVT_STK_NOT_VALID(4001),

	  USER_NOT_FOUND(5000),
	  USER_NOT_VALID(5001),
	  USER_ALREADY_EXISTS(5002),
	  USER_CHANGE_PASSWORD_OBJECT_NOT_VALID(5003),
	  USER_PHONE_NUMBER_ALREADY_EXISTS(5004),
	  
	  PRODUCT_TYPE_DISTRIBUTION_NOT_FOUND(6000),
	  PRODUCT_TYPE_DISTRIBUTION_NOT_VALID(6001),
	  AGENT_ALREADY_EXISTS(6002),
	  
	  SUPPLIER_NOT_FOUND(7000),
	  SUPPLIER_NOT_VALID(7001),
	  SUPPLIER_ALREADY_EXISTS(7002),
	  SUPPLIER_PHONE_NUMBER_ALREADY_EXISTS(7003),
	  SUPPLIER_ALREADY_IN_USE(7004),
	  
	  SUPERADMIN_NOT_FOUND(8000),
	  SUPERADMIN_NOT_VALID(8001),
	  SUPERADMIN_ALREADY_EXISTS(8003),
	  
	  ADMIN_NOT_FOUND(9000),
	  ADMIN_NOT_VALID(9001),
	  ADMIN_ALREADY_EXISTS(9002),
	  
	  MAGASINIER_NOT_FOUND(10000),
	  MAGASINIER_NOT_VALID(10001),
	  MAGASINIER_ALREADY_EXISTS(10002),
	  
	  PRODUCTTYPE_NOT_FOUND(11000),
	  PRODUCTTYPE_NOT_VALID(11001),
	  PRODUCTTYPE_ALREADY_EXISTS(11002),
	  PRODUCTTYPE_ALREADY_IN_USE(11003),
	  
	  SALESUNIT_NOT_FOUND(12000),
	  SALESUNIT_NOT_VALID(12001),
	  SALESUNIT_ALREADY_EXISTS(12002),
	  SALESUNIT_ALREADY_IN_USE(12003),
	  
	  MVTSTK_SUPPLIER_NOT_VALID(13000),
	  MVTSTK_MENAGE_NOT_VALID(13001),
	  MVTSTK_MENAGE_ALREADY_IN_USE(13002),
	  MVTSTK_SUPPLIER_ALREADY_IN_USE(13003),
	  
	  VENTE_NOT_VALID(14000),
	  VENTE_NOT_FOUND(14001),
	  VENTE_ALREADY_PAID(14002),
	  
	  TRANSACTION_NOT_VALID(15000),
	  TRANSACTION_NOT_FOUND(15001),
	  TRANSACTION_ALREADY_IN_USE(15002),
	  
	  REFRESH_TOKEN_INVALID(16000),
	  

	  BAD_CREDENTIALS(18003),

	 

	  // Liste des exception techniques
	  UNKNOWN_CONTEXT(14001)
	  ;

	  private int code;

	  ErrorCodes(int code) {
	    this.code = code;
	  }

	  public int getCode() {
	    return code;
	  }
}
