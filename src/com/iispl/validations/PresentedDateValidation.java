package com.iispl.validations;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;


public class PresentedDateValidation implements ValidationRule{

	@Override
	public ChequeValidationEnum validate(Cheque cheque) {
		
		if(cheque.getPresentedDate()==null|| cheque.getPresentedDate().compareTo(cheque.getChequeDate())<0||cheque.getPresentedDate().compareTo(LocalDate.now())>0) {
			return ChequeValidationEnum.INVALID_PRESENTED_DATE;
		}else if(ChronoUnit.DAYS.between(cheque.getChequeDate(),cheque.getPresentedDate())>90){
			return ChequeValidationEnum.PRESENTED_DATE_EXCEEDS_90_DAYS;
		}else {
			return ChequeValidationEnum.VALIDATION_SUCCESS;
		}		
	}
	
}
