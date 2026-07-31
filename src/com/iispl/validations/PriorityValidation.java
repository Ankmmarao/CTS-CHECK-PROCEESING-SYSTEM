package com.iispl.validations;

import java.math.BigDecimal;

import com.iispl.enums.ChequePriority;
import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class PriorityValidation implements ValidationRule {

    @Override
    public ChequeValidationEnum validate(Cheque cheque) {

        if (cheque.getPriority() == null) {
            return ChequeValidationEnum.INVALID_PRIORITY;
        }

        BigDecimal amount = cheque.getChequeAmount();

        if (amount == null) {
            return ChequeValidationEnum.INVALID_CHEQUE_AMOUNT;
        }

        if (amount.compareTo(new BigDecimal("200000")) > 0
                && cheque.getPriority() != ChequePriority.HIGH) {
            return ChequeValidationEnum.INVALID_PRIORITY;
        }

        if (amount.compareTo(new BigDecimal("100000")) >= 0
                && amount.compareTo(new BigDecimal("200000")) <= 0
                && cheque.getPriority() != ChequePriority.MEDIUM) {
            return ChequeValidationEnum.INVALID_PRIORITY;
        }

        if (amount.compareTo(new BigDecimal("100000")) < 0
                && cheque.getPriority() != ChequePriority.LOW) {
            return ChequeValidationEnum.INVALID_PRIORITY;
        }

        return ChequeValidationEnum.VALIDATION_SUCCESS;
    }
}