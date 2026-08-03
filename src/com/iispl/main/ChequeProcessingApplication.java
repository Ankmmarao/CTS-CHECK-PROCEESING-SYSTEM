package com.iispl.main;

import java.util.List;
import java.util.Scanner;

import com.iispl.dao.ChequeDAO;
import com.iispl.dao.ChequedaoIMPL;
import com.iispl.exception.DuplicateChequeNumberException;
import com.iispl.model.Cheque;
import com.iispl.services.ChequeService;
import com.iispl.services.ChequeServiceImpl;

public class ChequeProcessingApplication {

    public static void startApplication() {

        Scanner scanner = new Scanner(System.in);

        ChequeService chequeService = new ChequeServiceImpl();
        ChequeDAO chequeDTO = new ChequedaoIMPL();

        while (true) {

            System.out.println("\n========== CTS CHEQUE PROCESSING SYSTEM ==========");
            System.out.println("1. Display All Cheques");
            System.out.println("2. Process All Cheques");
            System.out.println("3. Display Eligible Cheques");
            System.out.println("4. Sort By Presenting Bank And Amount");
            System.out.println("5. Sort By Cheque Date");
            System.out.println("6. Display High Value Cheques");
            System.out.println("7. Sort By Amount Ascending");
            System.out.println("8. Sort By Amount Descending");
            System.out.println("9. Sort By Priority And Status");
            System.out.println("10.sortByClearingZoneAndAmount");
            System.out.println("11. Report");
            System.out.println("12. Exit");
            System.out.print("Enter your choice : ");

            int choice = scanner.nextInt();

            List<Cheque> chequeList = chequeDTO.getAllCheques();

            switch (choice) {
            
            case 1:
                chequeService.displayAllCheques();
                break;

            case 2:
                try {
                    chequeService.processAllCheques();
                } catch (DuplicateChequeNumberException e) {
                    System.out.println(e.getMessage());
                }
                break;

            case 3:
                chequeService.displayEligibleCheques();
                break;

            case 4:
                chequeService.sortByChequeByPresentingBankAndAmount();
                break;

            case 5:
                chequeService.sortChequeByDate();
                break;

            case 6:
                chequeService.displayHighValuedCheques();
                break;

            case 7:
                chequeService.sortByAmountAscending();
                break;

            case 8:
                chequeService.sortByAmountDescending();
                break;

            case 9:
                chequeService.sortByPriorityAndStatus();
                break;

            case 10:
                chequeService.sortByClearingZoneAndAmount();
                break;
                
            case 11:
            	chequeService.checkProcessingReports();
            	break;
           
            case 12:
                System.out.println("Thank You...");
                scanner.close();
                System.exit(0);
                break;
            	

            default:
                System.out.println("Invalid Choice.");
            }
        }
    }
}