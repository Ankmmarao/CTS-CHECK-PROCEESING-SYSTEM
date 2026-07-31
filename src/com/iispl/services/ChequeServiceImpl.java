package com.iispl.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.iispl.dto.ChequeDTO;
import com.iispl.dto.ChequedtoIMPL;
import com.iispl.enums.ChequePriority;
import com.iispl.enums.ChequeStatus;
import com.iispl.enums.ChequeValidationEnum;
import com.iispl.model.Cheque;
import com.iispl.validations.ChequeNumberValidation;
import com.iispl.validations.ValidationRule;

public class ChequeServiceImpl implements ChequeService {

    private ChequeDTO chequeDTO = new ChequedtoIMPL();

    private List<Cheque> eligibleChequeList = new ArrayList<>();

    @Override
    public void displayAllCheques() {

        List<Cheque> chequeList = chequeDTO.getAllCheques();

        for (Cheque cheque : chequeList) {
            System.out.println(cheque);
        }
    }

    @Override
    public void processAllCheques() {

        List<Cheque> chequeList = chequeDTO.getAllCheques();

        List<ValidationRule> validations = new ArrayList<>();

        validations.add(new ChequeNumberValidation());
       
       

        for (Cheque cheque : chequeList) {

            boolean valid = true;

            for (ValidationRule rule : validations) {

                ChequeValidationEnum result = rule.validate(cheque);

                if (result != ChequeValidationEnum.VALIDATION_SUCCESS) {

                   cheque.setStatus(ChequeStatus.REJECTED);
                    chequeDTO.updateChequeStatus(cheque);

                    System.out.println(cheque.getChequeNumber() + " : " + result);

                    valid = false;
                    break;
                }
            }
            
            if (valid) {
            	if(cheque.getChequeAmount().compareTo(new BigDecimal("200000")) > 0) {
            		cheque.setPriority(ChequePriority.HIGH);	
            	}
            	
            else {
                cheque.setStatus(ChequeStatus.ACCEPTED);
            }
                
                chequeDTO.updateChequeStatus(cheque);

                eligibleChequeList.add(cheque);

                System.out.println(cheque.getChequeNumber() + " : ACCEPTED");
            }
        }
    }

    @Override
    public void displayEligibleCheques() {

        for (Cheque cheque : eligibleChequeList) {
            System.out.println(cheque);
        }
    }

	@Override
	public void sortByChequeByPresentingBankAndAmount(List<Cheque> chequeList) {
		 chequeDTO.sortByChequeByPresentingBankAndAmount(chequeList);
		
	}

	@Override
	public void sortChequeByDate(List<Cheque> cheques) {
		chequeDTO.sortChequeByDate(cheques);
		
	}

	@Override
	public void displayHighValuedCheques(List<Cheque> cheques) {
		chequeDTO.displayHighValuedCheques(cheques);
		
	}
}