package com.iispl.validations;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class AccountFiledsrValidation implements ValidationRule {

    @Override
    public ChequeValidationEnum validate(Cheque cheque) {

        if (cheque.getAccountNumber() == null || cheque.getAccountNumber().trim().isEmpty()) {
        	
                return ChequeValidationEnum.INVALID_ACCOUNT_NUMBER;
        }

        if (cheque.getDrawerName() == null || cheque.getDrawerName().trim().isEmpty()) {
                      return ChequeValidationEnum.INVALID_DRAWER_NAME;
        }

        if (cheque.getPresentingBank() == null   || cheque.getPresentingBank().trim().isEmpty()) {
        	
        	
            return ChequeValidationEnum.INVALID_PRESENTING_BANK;
        }

        return ChequeValidationEnum.VALIDATION_SUCCESS;
    }
}