package com.iispl.validations;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class ChequeNumberValidation implements ValidationRule {

    @Override
    public ChequeValidationEnum validate(Cheque cheque) {

        if (!isChequeNumberValid(cheque.getChequeNumber())) {
            return ChequeValidationEnum.INVALID_CHEQUE_NUMBER;
        }

     return ChequeValidationEnum.VALIDATION_SUCCESS;
    }

    private boolean isChequeNumberValid(String chequeNumber) {
             return chequeNumber != null && !chequeNumber.trim().isEmpty();
    }
}