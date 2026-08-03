package com.iispl.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

import com.iispl.connectionpool.ConnectionPool;
import com.iispl.enums.ChequePriority;
import com.iispl.enums.ChequeStatus;
import com.iispl.exception.AccountNotFoundException;
import com.iispl.exception.DuplicateChequeNumberException;
import com.iispl.exception.InvalidPresentingBankException;
import com.iispl.model.Cheque;
import com.iispl.services.ChequeServiceImpl;

public class ChequedaoIMPL implements ChequeDAO {
	
	
	
	
	
	Scanner input=new Scanner(System.in);

    @Override
    public List<Cheque> getAllCheques() throws DuplicateChequeNumberException {

        List<Cheque> chequeList = new ArrayList<>();

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement =connection.prepareStatement("SELECT * FROM cheque");
             ResultSet resultSet = preparedStatement.executeQuery()) {
        	connection.setAutoCommit(false);
            while (resultSet.next()) {

            	
            	
            	Cheque cheque = new Cheque();

            	cheque.setChequeNumber(resultSet.getString("cheque_number"));

            	if (isChequeNumberExists(cheque.getChequeNumber())) {
            	    throw new DuplicateChequeNumberException(
            	        "Duplicate Cheque Number: " + cheque.getChequeNumber()
            	    );
            	}

            	cheque.setAccountNumber(resultSet.getString("account_number"));
            	cheque.setDrawerName(resultSet.getString("drawer_name"));
            	cheque.setPresentingBank(resultSet.getString("presenting_bank"));
            	cheque.setChequeAmount(resultSet.getBigDecimal("cheque_amount"));

            	if (resultSet.getDate("cheque_date") != null) {
            	    cheque.setChequeDate(resultSet.getDate("cheque_date").toLocalDate());
            	}

            	if (resultSet.getDate("presented_date") != null) {
            	    cheque.setPresentedDate(resultSet.getDate("presented_date").toLocalDate());
            	}

            	String priority = resultSet.getString("priority");
            	if (priority != null) {
            	    cheque.setPriority(ChequePriority.valueOf(priority.trim().toUpperCase()));
            	}

            	String status = resultSet.getString("status");
            	if (status != null) {
            	    cheque.setStatus(ChequeStatus.valueOf(status.trim().toUpperCase()));
            	}

            	cheque.setClearingZone(resultSet.getString("clearingzone"));

            	chequeList.add(cheque);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return chequeList;
    }

    @Override
    public boolean isChequeNumberExists(String chequeNumber) {

        String sql = "SELECT COUNT(*) AS total FROM cheque WHERE cheque_number=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, chequeNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("total") > 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public void updateChequeStatus(Cheque cheque) {

        String sql =
                "UPDATE cheque SET status=?, priority=? WHERE cheque_number=?";

        try (Connection connection = ConnectionPool.getDataSource().getConnection();
             PreparedStatement preparedStatement =
                     connection.prepareStatement(sql)) {

            // Status
            if (cheque.getStatus() != null) {
                preparedStatement.setString(1, cheque.getStatus().name());
            } else {
                preparedStatement.setNull(1, java.sql.Types.VARCHAR);
            }

            // Priority
            if (cheque.getPriority() != null) {
                preparedStatement.setString(2, cheque.getPriority().name());
            } else {
                preparedStatement.setNull(2, java.sql.Types.VARCHAR);
            }

            preparedStatement.setString(3, cheque.getChequeNumber());

            preparedStatement.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sortByChequeByPresentingBankAndAmount(List<Cheque> chequeList) throws InvalidPresentingBankException  {
    	
    	
        chequeList.sort(Comparator.comparing(Cheque::getPresentingBank).thenComparing(Cheque::getChequeAmount));

        System.out.printf(
                "%-15s %-15s %-20s %-20s %-15s %-15s %-15s %-12s %-12s%n",
                "Cheque No",
                "Account No",
                "Drawer Name",
                "Presenting Bank",
                "Amount",
                "Cheque Date",
                "Presented Date",
                "Priority",
                "Status");

        System.out.println(
                "-------------------------------------------------------------------------------------------------------------------------------");

        chequeList.forEach(cheque ->
                System.out.printf(
                        "%-15s %-15s %-20s %-20s %-15s %-15s %-15s %-12s %-12s%n",
                        cheque.getChequeNumber(),
                        cheque.getAccountNumber(),
                        cheque.getDrawerName(),
                        cheque.getPresentingBank(),
                        cheque.getChequeAmount(),
                        cheque.getChequeDate(),
                        cheque.getPresentedDate(),
                        cheque.getPriority(),
                        cheque.getStatus()));
    }

    @Override
    public void sortChequeByDate(List<Cheque> chequeList) {

        chequeList.sort(Comparator.comparing(Cheque::getChequeDate));

        chequeList.forEach(System.out::println);
    }

    @Override
    public void displayHighValuedCheques(List<Cheque> chequeList) {

        for (Cheque cheque : chequeList) {

            if (cheque.getPriority() == ChequePriority.HIGH) {

                System.out.println(cheque);
                System.out.println("--------------------------------------------");
            }
        }
    }

    @Override
    public void sortByAmountDescending(List<Cheque> allCheques) {

        allCheques.sort(Comparator.comparing(Cheque::getChequeAmount).reversed());

        allCheques.forEach(System.out::println);
    }

    @Override
    public void sortByAmountAscending(List<Cheque> allCheques) {

        Collections.sort(allCheques,Comparator.comparing(Cheque::getChequeAmount));

        allCheques.forEach(System.out::println);
    }

    @Override
    public void sortByPriorityAndStatus(List<Cheque> allCheques) {

        allCheques.sort(Comparator.comparing(Cheque::getPriority).thenComparing(Cheque::getStatus));

        allCheques.forEach(System.out::println);
    }
    
    public static void acceptedValuesChequesReport(List<Cheque> cheques) {

        int acceptedCount = 0;
        int highCount = 0;
        int mediumCount = 0;
        int lowCount = 0;
        int totalCheques = 0;

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal maxChequeAmount = BigDecimal.ZERO;
        BigDecimal minChequeAmount = null;

        for (Cheque cheque : cheques) {

            totalCheques++;

            if (cheque.getStatus() == ChequeStatus.ACCEPTED) {

                acceptedCount++;

                switch (cheque.getPriority()) {

                case HIGH:
                    highCount++;
                    break;

                case MEDIUM:
                    mediumCount++;
                    break;

                case LOW:
                    lowCount++;
                    break;
                }

                totalAmount = totalAmount.add(cheque.getChequeAmount());

                if (cheque.getChequeAmount().compareTo(maxChequeAmount) > 0) {
                  maxChequeAmount = cheque.getChequeAmount();
                }

                if (minChequeAmount == null|| cheque.getChequeAmount().compareTo(minChequeAmount) < 0) {
              minChequeAmount = cheque.getChequeAmount();
                }
            }
            
        }

        System.out.println("\n========= ACCEPTED CHEQUE PROCESSING REPORT =========");
        System.out.println("Total Cheques          : " + totalCheques);
        System.out.println("Accepted Cheques       : " + acceptedCount);
        System.out.println("High Priority          : " + highCount);
        System.out.println("Medium Priority        : " + mediumCount);
        System.out.println("Low Priority           : " + lowCount);
        System.out.println("Total Accepted Amount  : ₹" + totalAmount);
        System.out.println("Maximum Cheque Amount  : ₹" + maxChequeAmount);
        System.out.println("Minimum Cheque Amount  : ₹" + minChequeAmount);
    }
    public static void rejectedValuesChequesReport(List<Cheque> cheques) {

        int acceptedCount = 0;
        int highCount = 0;
        int mediumCount = 0;
        int lowCount = 0;
        int totalCheques = 0;

        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal maxChequeAmount = BigDecimal.ZERO;
        BigDecimal minChequeAmount = null;

        for (Cheque cheque : cheques) {

            totalCheques++;

            if (cheque.getStatus() == ChequeStatus.REJECTED) {

                acceptedCount++;

                switch (cheque.getPriority()) {

                case HIGH:
                    highCount++;
                    break;

                case MEDIUM:
                    mediumCount++;
                    break;

                case LOW:
                    lowCount++;
                    break;
                }


                if (cheque.getChequeAmount().compareTo(maxChequeAmount) > 0) {
                    maxChequeAmount = cheque.getChequeAmount();
                }

                if (minChequeAmount == null
                        || cheque.getChequeAmount().compareTo(minChequeAmount) < 0) {
                    minChequeAmount = cheque.getChequeAmount();
                }
            }
            
        }

        System.out.println("\n========= ACCEPTED CHEQUE PROCESSING REPORT =========");
        System.out.println("Total Cheques          : " + totalCheques);
        System.out.println("Rejected Cheques       : " + acceptedCount);
        System.out.println("High Priority          : " + highCount);
        System.out.println("Medium Priority        : " + mediumCount);
        System.out.println("Low Priority           : " + lowCount);
        System.out.println("Maximum Cheque Amount  : ₹" + maxChequeAmount);
        System.out.println("Minimum Cheque Amount  : ₹" + minChequeAmount);
    }
  
    
    public static void customerChequeProcessingReports(List<Cheque> cheques, String accountNumber) {

        int total = 0;
        int acceptedCount = 0;
        int rejectedCount = 0;

        BigDecimal acceptedAmount = BigDecimal.ZERO;

        System.out.println("\n========== CUSTOMER CHEQUE REPORT ==========");

        for (Cheque cheque : cheques) {
        	
        	if (!ChequeServiceImpl.isAccountExists(accountNumber)) {
        	    throw new AccountNotFoundException("AccountNot FoundException");
        	}

        	
        	      if (cheque.getAccountNumber().equals(accountNumber)) {

                total++;

                System.out.println("Cheque Number   : " + cheque.getChequeNumber());
                System.out.println("Drawer Name     : " + cheque.getDrawerName());
                System.out.println("Presenting Bank : " + cheque.getPresentingBank());
                System.out.println("Cheque Amount   : " + cheque.getChequeAmount());
                System.out.println("Priority        : " + cheque.getPriority());
                System.out.println("Status          : " + cheque.getStatus());

                if (cheque.getStatus() == ChequeStatus.ACCEPTED) {

                    acceptedCount++;
                    acceptedAmount = acceptedAmount.add(cheque.getChequeAmount());

                } else if (cheque.getStatus() == ChequeStatus.REJECTED) {

                    rejectedCount++;
                }
            }
            
        }

        System.out.println("\n============= REPORT =============");
        System.out.println("Account Number          : " + accountNumber);
        System.out.println("Total Cheques           : " + total);
        System.out.println("Accepted Cheques        : " + acceptedCount);
        System.out.println("Rejected Cheques        : " + rejectedCount);
        System.out.println("Accepted Cheque Amount  : ₹" + acceptedAmount);
    }
    @Override
    public void checkProcessingReports(List<Cheque> cheques) {

    	System.out.println("\n========== CHEQUE PROCESSING REPORTS ==========");
    	System.out.println("1. Accepted Cheques Report");
    	System.out.println("2. Rejected Cheques Report");
    	System.out.println("3. Customer Cheque Processing Report");
    	System.out.print("Enter your choice : ");

    	int option = input.nextInt();

    	switch (option) {

    	case 1:
    	    acceptedValuesChequesReport(cheques);
    	    break;

    	case 2:
    	    rejectedValuesChequesReport(cheques);
    	    break;

    	case 3:
    	    try {
    	        System.out.print("Enter Account Number : ");
    	        String accountNumber = input.next();

    	        customerChequeProcessingReports(cheques, accountNumber);

    	    } catch (AccountNotFoundException e) {
    	        System.out.println(e.getMessage());
    	    }
    	    break;

    	default:
    	    System.out.println("Invalid Choice! Please select a valid option.");
    	}
		
    }

    @Override
    public void sortByClearingZoneAndAmountComparator(List<Cheque> allCheques) {

        allCheques.sort(Comparator.comparing(Cheque::getClearingZone).thenComparing(Cheque::getChequeAmount));
        
        allCheques.forEach(System.out::println);
    }

	
}