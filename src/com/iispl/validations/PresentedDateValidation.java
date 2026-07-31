package com.iispl.validations;

import java.time.LocalDate;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;


public class PresentedDateValidation implements ValidationRule{

	@Override
	public ChequeValidationEnum validate(Cheque cheque) {
		
		if(cheque.getPresentedDate().compareTo(cheque.getChequeDate())<0||cheque.getPresentedDate().compareTo(LocalDate.now())>0) {
			return ChequeValidationEnum.INVALID_PRESENTED_DATE;
		}else {
			return ChequeValidationEnum.VALIDATION_SUCCESS;
		}
		
	}
	
}
