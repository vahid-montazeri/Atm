package org.example;

import org.example.db.AccountDao;
import org.example.db.UserDao;
import org.example.enums.Gender;
import org.example.model.Account;
import org.example.model.User;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Scanner;

public class Atm {

    private final Scanner scanner;
    private final AccountDao accountDao;
    private final UserDao userDao;

    public Atm() {
        this.scanner = new Scanner(System.in);
        this.accountDao = new AccountDao();
        userDao = new UserDao();
    }

    public void showAtmMenu() {
        System.out.println("1- Register");
        System.out.println("2- Balance");
        System.out.println("3- Deposit");
        System.out.println("4- Withdraw");
        System.out.println("0- Exit");
        System.out.println();
        System.out.println("Select one of the above numbers: ");
    }

    public void withdraw() {
        Scanner scanner = new Scanner(System.in);
        AccountDao accountDao = new AccountDao();
        System.out.print("Please enter your account number: ");
        int accountNumber = scanner.nextInt();
        Double balance = accountDao.getBalanceByAccountNumber(accountNumber);
        if (balance == -1) {
            System.err.println("حساب مورد نظر یافت نشد.");
            return;
        }

        System.out.print("Please enter your withdraw value: ");
        double withdrawValue = scanner.nextDouble();

        if (balance > 0 && withdrawValue > balance) {
            System.err.println("مبلغ برداشت بیشتر از موجودی حساب است.");
            return;
        }

        double newBalance = balance - withdrawValue;
        accountDao.updateBalance(accountNumber, newBalance);
    }

    public static String formatCurrency(double currency) {
        DecimalFormat df = new DecimalFormat("###,###");
        return df.format(currency).concat(" ريال");
    }


    public void showBalance() {
        System.out.println("Please Enter Your AccountNumber:");
        int accountNumber = scanner.nextInt();
        Double balance = accountDao.getBalanceByAccountNumber(accountNumber);

        if (balance == -1) {
            System.err.println("شماره حساب مورد نظر یافت نشد");
            return;
        }

        System.out.println("Your balance is: " + formatCurrency(balance));
    }

    public void deposit() {
        // دریافت شماره حساب
        System.out.print("Please enter your account number: ");
        int accountNumber = scanner.nextInt();

        // دریافت موجودی حساب از دیتابیس
        Double balance = accountDao.getBalanceByAccountNumber(accountNumber);

        if (balance == -1) {
            System.err.println("حساب مورد نظر یافت نشد.");
            return;
        }

        // دریافت مبلغ واریز
        System.out.print("Please enter deposit value: ");
        double depositValue = scanner.nextDouble();

        // جمع مبلغ واریزی و موجودی حساب
        double newBalance = balance + depositValue;

        // ذخیره مبلغ موجودی جدید در دیتابیس
        accountDao.updateBalance(accountNumber, newBalance);

    }

    public void register() {
        System.out.print("Enter Your Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Your Family: ");
        String family = scanner.nextLine();

        System.out.print("Enter Your National Code: ");
        String nc = scanner.nextLine();

        if (nc.length() != 10) {
            throw new RuntimeException("کد ملی باید 10 رقمی باشد");
        }

        System.out.print("Enter Your Address: ");
        String address = scanner.nextLine();

        System.out.print("Enter Your Phone: ");
        String phone = scanner.nextLine();
        if (phone.length() != 11 || !phone.startsWith("0")) {
            throw new RuntimeException("شماره تلفن همراه اشتباه است ");
        }

        System.out.print("Enter Your Gender(M/F): ");
        String gender = scanner.nextLine();

        User user = new User(
                name,
                family,
                nc,
                address,
                phone,
                gender.trim().equalsIgnoreCase("m") ? Gender.MALE : Gender.FEMALE
        );

        Long userId = userDao.save(user);
        System.err.println("ثبت نام با موفقیت انجام شد. آیدی: " + userId);

        String accountNumber = String.valueOf(new Random().nextInt(1000, 9999));
        User savedUser = userDao.getById(userId);

        Account account = new Account(accountNumber, 0L, savedUser);
        accountDao.save(account);
        System.err.println("حساب شما با شماره " + accountNumber + " افتتاح گردید.");
    }
}
