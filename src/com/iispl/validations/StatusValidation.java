package com.iispl.validations;

import com.iispl.enums.ChequeStatus;
import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class StatusValidation implements ValidationRule {

    @Override
    public ChequeValidationEnum validate(Cheque cheque) {

        if (isStatusInvalid(cheque.getStatus())) {
            return ChequeValidationEnum.INVALID_STATUS;
        }

        return ChequeValidationEnum.VALIDATION_SUCCESS;
    }

    private boolean isStatusInvalid(ChequeStatus status) {
        return status == null;
    }
}