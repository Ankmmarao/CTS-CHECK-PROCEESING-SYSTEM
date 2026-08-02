package com.iispl.validations;

import java.math.BigDecimal;

import com.iispl.enums.ChequePriority;
import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class PriorityValidation implements ValidationRule {

	@Override
	public ChequeValidationEnum validate(Cheque cheque) {

	    if (isPriorityInvalid(cheque.getPriority())) {
	        return ChequeValidationEnum.INVALID_PRIORITY;
	    }

	    return ChequeValidationEnum.VALIDATION_SUCCESS;
	}

	private boolean isPriorityInvalid(ChequePriority priority) {
	    return priority == null;
	}
}