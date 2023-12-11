package org.example;

import java.time.Instant;
import java.util.Date;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Atm atm = new Atm();

        System.err.println("******* WELCOME TO YOUR BANK *******");

        while (true) {
            atm.showAtmMenu();

            Scanner scanner = new Scanner(System.in);
            int userInput = scanner.nextInt();

            if (userInput == 0) {
                System.err.println("*** GOOD BYE ***");
                System.exit(0);
                break;
            }

            switch (userInput) {
                case 1 -> atm.register();
                case 2 -> atm.showBalance();
                case 3 -> atm.deposit();
                case 4 -> atm.withdraw();
                default -> {}
            }
        }


    }


}