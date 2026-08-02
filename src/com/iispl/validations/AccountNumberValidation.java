package com.iispl.validations;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class AccountNumberValidation implements ValidationRule {

	    @Override
	    public ChequeValidationEnum validate(Cheque cheque) {
	    	
	    	if(!isAccountNumberPresent(cheque.getAccountNumber())) {
	    		return ChequeValidationEnum.INVALID_ACCOUNT_NUMBER;
	    	}
	    	    if(!isAccountNumberLengthValid(cheque.getAccountNumber())) {
	    		return ChequeValidationEnum.INVALID_ACCOUNT_NUMBER_LENGTH;
	    		
	    	}
	        return ChequeValidationEnum.VALIDATION_SUCCESS;
	    }

	    
	 private boolean isAccountNumberPresent(String accountNumber) {
	        return accountNumber != null && !accountNumber.trim().isEmpty();
	    }
	    
	 private boolean isAccountNumberLengthValid(String accountNumber) {
	        return accountNumber.length() == 12;
	    }
}
