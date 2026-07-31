package com.iispl.validations;

import java.math.BigDecimal;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public class ChequeAmountValidation implements ValidationRule{

	@Override
	public ChequeValidationEnum validate(Cheque cheque) {
		if(cheque.getChequeAmount().compareTo(BigDecimal.ZERO)<0 || cheque.getChequeAmount()==null)
		{
			return ChequeValidationEnum.INVALID_CHEQUE_AMOUNT;
		}
		return ChequeValidationEnum.VALIDATION_SUCCESS;
	}
	
}
