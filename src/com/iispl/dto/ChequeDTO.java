package com.iispl.dto;

import java.util.List;

import com.iispl.model.Cheque;

public interface ChequeDTO {

    List<Cheque> getAllCheques();

    void updateChequeStatus(Cheque cheque);

	void sortByChequeByPresentingBankAndAmount(List<Cheque> chequeList);

	void sortChequeByDate(List<Cheque> cheques);

	void displayHighValuedCheques(List<Cheque> cheques);


    

	void sortByAmountDescending(List<Cheque> allCheques);

	void sortByAmountAscending(List<Cheque> allCheques);

	void sortByPriorityAndStatus(List<Cheque> allCheques);

	boolean isChequeNumberExists(String chequeNumber);
}