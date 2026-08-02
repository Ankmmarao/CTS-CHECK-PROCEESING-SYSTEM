package com.iispl.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.iispl.dto.ChequeDAO;
import com.iispl.dto.ChequedaoIMPL;
import com.iispl.enums.ChequePriority;
import com.iispl.enums.ChequeStatus;
import com.iispl.enums.ChequeValidationEnum;
import com.iispl.exception.DuplicateChequeNumberException;
import com.iispl.model.Cheque;

import com.iispl.validations.ChequeNumberValidation;
import com.iispl.validations.AccountFieldsValidation;
import com.iispl.validations.ChequeAmountValidation;
import com.iispl.validations.ChequeNumberValidation;
import com.iispl.validations.PresentedDateValidation;
import com.iispl.validations.PriorityValidation;
import com.iispl.validations.StatusValidation;
import com.iispl.validations.ValidationRule;

public class ChequeServiceImpl implements ChequeService {

    private static ChequeDAO chequeDTO = new ChequedaoIMPL();

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
        validations.add(new AccountFieldsValidation());
        validations.add(new PriorityValidation());
        validations.add(new StatusValidation());
        
        System.out.println("------------------------------------------------------------------------------------------------------------------------");
        System.out.printf(
                "| %-12s | %-12s | %-12s | %-10s | %-20s |%n",
                "Cheque No",
                "Amount",
                "Priority",
                "Status",
                "Result");
        System.out.println("------------------------------------------------------------------------------------------------------------------------");

        for (Cheque cheque : chequeList) {

            boolean valid = true;

            for (ValidationRule rule : validations) {

                ChequeValidationEnum result = rule.validate(cheque);

                if (result != ChequeValidationEnum.VALIDATION_SUCCESS) {

                    cheque.setStatus(ChequeStatus.REJECTED);
                    chequeDTO.updateChequeStatus(cheque);

                    System.out.printf(
                            "| %-12s | %-12s | %-12s | %-10s | %-20s |%n",
                            cheque.getChequeNumber(),
                            cheque.getChequeAmount(),
                            cheque.getPriority(),
                            cheque.getStatus(),
                            result);
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
                


                cheque.setStatus(ChequeStatus.ACCEPTED);

              
                BigDecimal amount = cheque.getChequeAmount();

               
                if (amount.compareTo(new BigDecimal("200000")) > 0) {

                    cheque.setPriority(ChequePriority.HIGH);

                } else if (amount.compareTo(new BigDecimal("100000")) >= 0) {

                    cheque.setPriority(ChequePriority.MEDIUM);

                } else {

                    cheque.setPriority(ChequePriority.LOW);
                }

            
                chequeDTO.updateChequeStatus(cheque);

             
                eligibleChequeList.add(cheque);

                System.out.printf(
                        "| %-12s | %-12s | %-12s | %-10s | %-20s |%n",
                        cheque.getChequeNumber(),
                        cheque.getChequeAmount(),
                        cheque.getPriority(),
                        cheque.getStatus(),
                        "VALIDATION SUCCESS");
            }
        }
    }
    @Override
    public void displayEligibleCheques() {

        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.printf(
                "| %-12s | %-15s | %-20s | %-20s | %-12s | %-12s | %-15s | %-8s | %-10s |%n",
                "Cheque No",
                "Account No",
                "Drawer Name",
                "Presenting Bank",
                "Amount",
                "Cheque Date",
                "Presented Date",
                "Priority",
                "Status");

        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (Cheque cheque : eligibleChequeList) {

            System.out.printf(
                    "| %-12s | %-15s | %-20s | %-20s | %-12s | %-12s | %-15s | %-8s | %-10s |%n",
                    cheque.getChequeNumber(),
                    cheque.getAccountNumber(),
                    cheque.getDrawerName(),
                    cheque.getPresentingBank(),
                    cheque.getChequeAmount(),
                    cheque.getChequeDate(),
                    cheque.getPresentedDate(),
                    cheque.getPriority(),
                    cheque.getStatus());
        }

        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
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

	@Override
	public void checkProcessingReports(List<Cheque> cheque) {
		// TODO Auto-generated method stub
		chequeDTO.checkProcessingReports(cheque);
		
	}

	public static boolean isAccountExists(String accountNumber) {

	    if (accountNumber == null || accountNumber.trim().isEmpty()) {
	        return false;
	    }

	    for (Cheque cheque : chequeDTO.getAllCheques()) {
            if (cheque.getAccountNumber().equals(accountNumber)) {
	            return true;
	        }
	    }

	    return false;
	}
}