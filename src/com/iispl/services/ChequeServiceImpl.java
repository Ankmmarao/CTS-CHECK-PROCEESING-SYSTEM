package com.iispl.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.iispl.dao.ChequeDAO;
import com.iispl.dao.ChequedaoIMPL;
import com.iispl.enums.ChequePriority;
import com.iispl.enums.ChequeStatus;
import com.iispl.enums.ChequeValidationEnum;
import com.iispl.exception.DuplicateChequeNumberException;
import com.iispl.model.Cheque;

import com.iispl.validations.ChequeNumberValidation;
import com.iispl.validations.ClearingZoneValidation;
import com.iispl.validations.AccountFieldsValidation;
import com.iispl.validations.ChequeAmountValidation;
import com.iispl.validations.ChequeNumberValidation;
import com.iispl.validations.PresentedDateValidation;
import com.iispl.validations.PresentingBankValidation;
import com.iispl.validations.PriorityValidation;
import com.iispl.validations.StatusValidation;
import com.iispl.validations.ValidationRule;

public class ChequeServiceImpl implements ChequeService {

    private static ChequeDAO chequeDTO = new ChequedaoIMPL();

    private List<Cheque> eligibleChequeList = new ArrayList<>();
    static Set<String> chequeNumbers = new HashSet<>();

    public static boolean isChequeNumberExists(String chequeNumber) {

        Set<String> chequeNumbers = new HashSet<>();

        for (Cheque cheque : chequeDTO.getAllCheques()) {

            if (!chequeNumbers.add(cheque.getChequeNumber())) {
                return true;
            }
        }

        return false;
    }
    @Override
    public void displayAllCheques() {

        List<Cheque> chequeList = chequeDTO.getAllCheques();

        for (Cheque cheque : chequeList) {
        	
        	if(cheque.getChequeNumber()!=null || cheque.getPresentingBank()!=null || cheque.getDrawerName()!=null || cheque.getAccountNumber()!=null) {
        		 System.out.println(cheque);
        	}
           
        }
    }

    @Override
    public void processAllCheques()  {

        List<Cheque> chequeList = chequeDTO.getAllCheques();

        List<ValidationRule> validations = new ArrayList<>();

        validations.add(new ChequeNumberValidation());
        validations.add(new ChequeAmountValidation());
        validations.add(new PresentedDateValidation());
        validations.add(new PresentingBankValidation());
        validations.add(new AccountFieldsValidation());
        validations.add(new PriorityValidation());
        validations.add(new StatusValidation());
        validations.add(new ClearingZoneValidation());
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
	public void sortByChequeByPresentingBankAndAmount() {
		 chequeDTO.sortByChequeByPresentingBankAndAmount( eligibleChequeList);
	}
    

    @Override
    public void sortChequeByDate() {
        chequeDTO.sortChequeByDate( eligibleChequeList);
    }

    @Override
    public void displayHighValuedCheques() {
        chequeDTO.displayHighValuedCheques( eligibleChequeList);
    }

	@Override
	public void sortByAmountDescending() {
		// TODO Auto-generated method stub
		chequeDTO.sortByAmountDescending( eligibleChequeList);
		
	}

	

	
	public void sortByAmountAscending() {
		// TODO Auto-generated method stub
		chequeDTO.sortByAmountAscending( eligibleChequeList);
	}

	@Override
	public void sortByPriorityAndStatus() {
		// TODO Auto-generated method stub
		chequeDTO.sortByPriorityAndStatus( eligibleChequeList);
		
	}

	@Override
	public void checkProcessingReports() {
		// TODO Auto-generated method stub
		chequeDTO.checkProcessingReports( eligibleChequeList);
		
	}

	public static boolean isAccountExists(String accountNumber) {

	    if (accountNumber == null || accountNumber.trim().isEmpty()) {
	        return false;
	        }

	    for (Cheque cheque : chequeDTO.getAllCheques()) {
        if (accountNumber.equals(cheque.getAccountNumber())) {
	            return true;
	        }
	    }

	    return false;
	}
}