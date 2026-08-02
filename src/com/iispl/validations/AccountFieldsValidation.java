package com.iispl.validations;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class AccountFieldsValidation implements ValidationRule {

    @Override
    public ChequeValidationEnum validate(Cheque cheque) {

        if (isAccountNumberInvalid(cheque.getAccountNumber())) {
         return ChequeValidationEnum.INVALID_ACCOUNT_NUMBER;
        }

      if (isDrawerNameInvalid(cheque.getDrawerName())) {
            return ChequeValidationEnum.INVALID_DRAWER_NAME;
        }
  
           if (isPresentingBankInvalid(cheque.getPresentingBank())) {
                return ChequeValidationEnum.INVALID_PRESENTING_BANK;
        }

        return ChequeValidationEnum.VALIDATION_SUCCESS;
    }

    private boolean isAccountNumberInvalid(String accountNumber) {
        return accountNumber == null || accountNumber.trim().isEmpty();
    }

    private boolean isDrawerNameInvalid(String drawerName) {
    return drawerName == null || drawerName.trim().isEmpty();
    }

    private boolean isPresentingBankInvalid(String presentingBank) {
        return presentingBank == null || presentingBank.trim().isEmpty();
    }
}