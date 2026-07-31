package com.iispl.validations;

import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;

public interface ValidationRule {

    ChequeValidationEnum validate(Cheque cheque);

}