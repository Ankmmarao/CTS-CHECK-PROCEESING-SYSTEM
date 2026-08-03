package com.iispl.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.iispl.enums.ChequePriority;
import com.iispl.enums.ChequeStatus;

public class Cheque implements Comparable<Cheque>{

    private String chequeNumber;
    private String accountNumber;
    private String drawerName;
    private String presentingBank;
    private BigDecimal chequeAmount;
    private LocalDate chequeDate;
    private LocalDate presentedDate;
    private ChequePriority priority;
    
    private ChequeStatus status;
    private String clearingZone;

    
	public Cheque() {
		super();
		this.chequeNumber = chequeNumber;
		this.accountNumber = accountNumber;
		this.drawerName = drawerName;
		this.presentingBank = presentingBank;
		this.chequeAmount = chequeAmount;
		this.chequeDate = chequeDate;
		this.presentedDate = presentedDate;
		this.priority = priority;
		this.status = status;
		this.clearingZone = clearingZone;
	}


	public String getChequeNumber() {
		return chequeNumber;
	}


	public void setChequeNumber(String chequeNumber) {
		this.chequeNumber = chequeNumber;
	}


	public String getAccountNumber() {
		return accountNumber;
	}


	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	public String getDrawerName() {
		return drawerName;
	}


	public void setDrawerName(String drawerName) {
		this.drawerName = drawerName;
	}


	public String getPresentingBank() {
		return presentingBank;
	}


	public void setPresentingBank(String presentingBank) {
		this.presentingBank = presentingBank;
	}


	public BigDecimal getChequeAmount() {
		return chequeAmount;
	}


	public void setChequeAmount(BigDecimal chequeAmount) {
		this.chequeAmount = chequeAmount;
	}


	public LocalDate getChequeDate() {
		return chequeDate;
	}


	public void setChequeDate(LocalDate chequeDate) {
		this.chequeDate = chequeDate;
	}


	public LocalDate getPresentedDate() {
		return presentedDate;
	}


	public void setPresentedDate(LocalDate presentedDate) {
		this.presentedDate = presentedDate;
	}


	public ChequePriority getPriority() {
		return priority;
	}


	public void setPriority(ChequePriority priority) {
		this.priority = priority;
	}


	public ChequeStatus getStatus() {
		return status;
	}


	public void setStatus(ChequeStatus status) {
		this.status = status;
	}


	@Override
	public String toString() {
		return "Cheque [chequeNumber=" + chequeNumber + ", accountNumber=" + accountNumber + ", drawerName="
				+ drawerName + ", presentingBank=" + presentingBank + ", chequeAmount=" + chequeAmount + ", chequeDate="
				+ chequeDate + ", presentedDate=" + presentedDate + ", priority=" + priority + ", status=" + status
				+ ", clearingZone=" + clearingZone + "]";
	}


	public String getClearingZone() {
		return clearingZone;
	}


	public void setClearingZone(String clearingZone) {
		this.clearingZone = clearingZone;
	}


	@Override
	public int compareTo(Cheque o) {
		// TODO Auto-generated method stub
		return this.getChequeNumber().compareTo(o.getChequeNumber());
	}
}