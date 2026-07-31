package com.iispl.dto;

import java.util.List;

import com.iispl.model.Cheque;

public interface ChequeDTO {

    List<Cheque> getAllCheques();

    void updateChequeStatus(Cheque cheque);

	void sortByChequeByPresentingBankAndAmount(List<Cheque> chequeList);

}