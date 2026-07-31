package com.iispl.main;

import java.util.Scanner;

import com.iispl.dto.ChequeDTO;
import com.iispl.dto.ChequedtoIMPL;
import com.iispl.services.ChequeService;
import com.iispl.services.ChequeServiceImpl;

public class CheckProceesingSystemMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ChequeService chequeService = new ChequeServiceImpl();
        ChequeDTO chequeDto = new ChequedtoIMPL();

        while (true) {

            System.out.println("\n========== CTS CHEQUE PROCESSING SYSTEM ==========");
            System.out.println("1. Display All Cheques");
            System.out.println("2. Process All Cheques");
            System.out.println("3. Display Eligible Cheques");
            System.out.println("6. Sort by Presenting Bank and Amount");
            System.out.println("7. Sort cheques by cheque_date");
            System.out.println("8. Display High Valued Cheques");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {

            case 1:
                chequeService.displayAllCheques();
                break;

            case 2:
                chequeService.processAllCheques();
                break;

            case 3:
                chequeService.displayEligibleCheques();
                break;
                
                
                
                
                
            case 6: 
            	chequeService.sortByChequeByPresentingBankAndAmount(chequeDto.getAllCheques());
                break;
                
            case 7:
            	chequeService.sortChequeByDate(chequeDto.getAllCheques());
            	break;
            case 8:
            	chequeService.displayHighValuedCheques(chequeDto.getAllCheques());
            	break;
            case 9:
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