package com.iispl.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.iispl.dto.ChequeDTO;
import com.iispl.dto.ChequedtoIMPL;
import com.iispl.enums.ChequePriority;
import com.iispl.enums.ChequeStatus;
import com.iispl.enums.ChequeValidationEnum;
import com.iispl.exception.DuplicateChequeNumberException;
import com.iispl.model.Cheque;

import com.iispl.validations.ChequeNumberValidation;

import com.iispl.validations.AccountFiledsrValidation;
import com.iispl.validations.ChequeAmountValidation;
import com.iispl.validations.ChequeNumberValidation;
import com.iispl.validations.PresentedDateValidation;
import com.iispl.validations.PriorityValidation;
import com.iispl.validations.StatusValidation;
import com.iispl.validations.ValidationRule;

public class ChequeServiceImpl implements ChequeService {

    private ChequeDTO chequeDTO = new ChequedtoIMPL();

    private List<Cheque> eligibleChequeList = new ArrayList<>();
    
    private boolean isChequeNumberExists(String chequeNumber) {
        return chequeDTO.isChequeNumberExists(chequeNumber);
    }

    @Override
    public void displayAllCheques() {

        List<Cheque> chequeList = chequeDTO.getAllCheques();

        for (Cheque cheque : chequeList) {
            System.out.println(cheque);
        }
    }

    @Override
    public void processAllCheques()  {

        List<Cheque> chequeList = chequeDTO.getAllCheques();

        List<ValidationRule> validations = new ArrayList<>();

        validations.add(new ChequeNumberValidation());
        validations.add(new ChequeAmountValidation());
        validations.add(new PresentedDateValidation());
        validations.add(new AccountFiledsrValidation());
        validations.add(new PriorityValidation());
        validations.add(new StatusValidation());

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
                


                // Accept the cheque
                cheque.setStatus(ChequeStatus.ACCEPTED);

                // Get cheque amount
                BigDecimal amount = cheque.getChequeAmount();

                // Assign priority
                if (amount.compareTo(new BigDecimal("200000")) > 0) {

                    cheque.setPriority(ChequePriority.HIGH);

                } else if (amount.compareTo(new BigDecimal("100000")) >= 0) {

                    cheque.setPriority(ChequePriority.MEDIUM);

                } else {

                    cheque.setPriority(ChequePriority.LOW);
                }

                // Update database
                chequeDTO.updateChequeStatus(cheque);

                // Add to eligible list
                eligibleChequeList.add(cheque);

                System.out.println(cheque.getChequeNumber()
                        + " : ACCEPTED (" + cheque.getPriority() + ")");
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
    public void sortChequeByDate(List<Cheque> chequeList) {
        chequeDTO.sortChequeByDate(chequeList);
    }

    @Override
    public void displayHighValuedCheques(List<Cheque> chequeList) {
        chequeDTO.displayHighValuedCheques(chequeList);
    }

	@Override
	public void sortByAmountDescending(List<Cheque> allCheques) {
		// TODO Auto-generated method stub
		chequeDTO.sortByAmountDescending(allCheques);
		
	}

	

	
	public void sortByAmountAscending(List<Cheque> allCheques) {
		// TODO Auto-generated method stub
		chequeDTO.sortByAmountAscending(allCheques);
	}

	@Override
	public void sortByPriorityAndStatus(List<Cheque> allCheques) {
		// TODO Auto-generated method stub
		chequeDTO.sortByPriorityAndStatus(allCheques);
		
	}
}