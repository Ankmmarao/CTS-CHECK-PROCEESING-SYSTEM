
package com.iispl.services;

import java.util.List;

import com.iispl.model.Cheque;

public interface ChequeService {

    void displayAllCheques();

    void processAllCheques();

    void displayEligibleCheques();
   

        void sortByChequeByPresentingBankAndAmount();

        void sortChequeByDate();

        void displayHighValuedCheques();

        void sortByAmountDescending();

        void sortByAmountAscending();

        void sortByPriorityAndStatus();

        void checkProcessingReports();
    

    /*void sortByChequeByPresentingBankAndAmount(List<Cheque> chequeList);

	void sortChequeByDate(List<Cheque> allCheques);

	void displayHighValuedCheques(List<Cheque> allCheques);


	
	void sortByAmountDescending(List<Cheque> allCheques);
	
	void sortByPriorityAndStatus(List<Cheque> allCheques);
	
	void sortByAmountAscending(List<Cheque> allCheques);
	
	void checkProcessingReports(List<Cheque> cheque);
	*/
}