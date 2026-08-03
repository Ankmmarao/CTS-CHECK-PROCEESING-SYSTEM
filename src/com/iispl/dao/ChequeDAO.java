package com.iispl.dao;

import java.util.List;

import com.iispl.model.Cheque;

public interface ChequeDAO {

    List<Cheque> getAllCheques();

    void updateChequeStatus(Cheque cheque);

	void sortByChequeByPresentingBankAndAmount(List<Cheque> chequeList);

	void sortChequeByDate(List<Cheque> cheques);

	void displayHighValuedCheques(List<Cheque> cheques);


    

	void sortByAmountDescending(List<Cheque> allCheques);

	void sortByAmountAscending(List<Cheque> allCheques);

	void sortByPriorityAndStatus(List<Cheque> allCheques);

	boolean isChequeNumberExists(String chequeNumber);

	void checkProcessingReports(List<Cheque> cheque);
}