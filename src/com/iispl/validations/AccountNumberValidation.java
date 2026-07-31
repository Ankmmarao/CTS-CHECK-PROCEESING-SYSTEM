package com.iispl.validations;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class AccountNumberValidation implements ValidationRule {

	    @Override
	    public ChequeValidationEnum validate(Cheque cheque) {

	        if (cheque.getAccountNumber() == null || cheque.getAccountNumber().trim().isEmpty()) {
	            return ChequeValidationEnum.INVALID_ACCOUNT_NUMBER;
	        }

	        if (Integer.parseInt(cheque.getAccountNumber()) > 0) {
	            return ChequeValidationEnum.INVALID_ACCOUNT_NUMBER;
	        }

	        return ChequeValidationEnum.VALIDATION_SUCCESS;
	    }

}
