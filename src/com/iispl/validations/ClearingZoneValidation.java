package com.iispl.validations;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class ClearingZoneValidation implements ValidationRule {

    @Override
    public ChequeValidationEnum validate(Cheque cheque) {

        if (isClearingZoneInvalid(cheque.getClearingZone())) {
            return ChequeValidationEnum.INVALID_CLEARING_ZONE;
        }

        return ChequeValidationEnum.VALIDATION_SUCCESS;
    }

    private boolean isClearingZoneInvalid(String clearingZone) {

        return clearingZone == null || clearingZone.trim().isEmpty();
    }
}