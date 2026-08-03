package com.iispl.validations;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class DrawerNameValidation implements ValidationRule {

    @Override
    public ChequeValidationEnum validate(Cheque cheque) {

        if (isDrawerNameInvalid(cheque.getDrawerName())) {
            return ChequeValidationEnum.INVALID_DRAWER_NAME;
        }

        return ChequeValidationEnum.VALIDATION_SUCCESS;
    }

    private boolean isDrawerNameInvalid(String drawerName) {
        return drawerName == null || drawerName.trim().isEmpty();
    }
}