package com.iispl.validations;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class StatusValidation implements ValidationRule {

    @Override
    public ChequeValidationEnum validate(Cheque cheque) {

        if (cheque.getStatus() == null) {
            return ChequeValidationEnum.INVALID_STATUS;
        }

        return ChequeValidationEnum.VALIDATION_SUCCESS;
    }
}