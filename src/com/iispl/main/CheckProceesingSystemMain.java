package com.iispl.main;

import java.util.Scanner;

import com.iispl.services.ChequeService;
import com.iispl.services.ChequeServiceImpl;

public class CheckProceesingSystemMain {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        ChequeService chequeService = new ChequeServiceImpl();

        while (true) {

            System.out.println("\n========== CTS CHEQUE PROCESSING SYSTEM ==========");
            System.out.println("1. Display All Cheques");
            System.out.println("2. Process All Cheques");
            System.out.println("3. Display Eligible Cheques");
            System.out.println("4. Exit");
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

            case 4:
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