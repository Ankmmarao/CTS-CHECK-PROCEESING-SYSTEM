package com.iispl.validations;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class ChequeNumberValidation implements ValidationRule {

    @Override
    public ChequeValidationEnum validate(Cheque cheque) {

        if (cheque.getChequeNumber() == null || cheque.getChequeNumber().trim().isEmpty()) {
            return ChequeValidationEnum.INVALID_CHEQUE_NUMBER;
        }

        if (Integer.parseInt(cheque.getChequeNumber()) > 0) {
            return ChequeValidationEnum.VALIDATION_SUCCESS;
        }

        return ChequeValidationEnum.INVALID_CHEQUE_NUMBER;
    }
}