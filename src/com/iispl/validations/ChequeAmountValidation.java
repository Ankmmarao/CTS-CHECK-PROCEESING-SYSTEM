package com.iispl.validations;

import java.math.BigDecimal;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class ChequeAmountValidation implements ValidationRule {

    @Override
    public ChequeValidationEnum validate(Cheque cheque) {

        if (isChequeAmountInvalid(cheque.getChequeAmount())) {
            return ChequeValidationEnum.INVALID_CHEQUE_AMOUNT;
        }

        return ChequeValidationEnum.VALIDATION_SUCCESS;
    }

    private boolean isChequeAmountInvalid(BigDecimal chequeAmount) {

        return chequeAmount == null|| chequeAmount.compareTo(BigDecimal.ZERO) < 0;
    }
}