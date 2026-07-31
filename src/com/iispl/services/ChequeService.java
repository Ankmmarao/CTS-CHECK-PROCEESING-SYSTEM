package com.iispl.services;

import java.util.List;

import com.iispl.model.Cheque;

public interface ChequeService {

    void displayAllCheques();

    void processAllCheques();

    void displayEligibleCheques();

    void sortByChequeByPresentingBankAndAmount(List<Cheque> chequeList);

	void sortChequeByDate(List<Cheque> allCheques);

	void displayHighValuedCheques(List<Cheque> allCheques);

}