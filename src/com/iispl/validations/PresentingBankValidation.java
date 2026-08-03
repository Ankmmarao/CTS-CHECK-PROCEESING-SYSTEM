package com.iispl.validations;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class PresentingBankValidation implements ValidationRule {

    @Override
    public ChequeValidationEnum validate(Cheque cheque) {

        if (isPresentingBankInvalid(cheque.getPresentingBank())) {
            return ChequeValidationEnum.INVALID_PRESENTING_BANK;
        }

        return ChequeValidationEnum.VALIDATION_SUCCESS;
    }

    private boolean isPresentingBankInvalid(String presentingBank) {
        return presentingBank == null || presentingBank.trim().isEmpty();
    }
}