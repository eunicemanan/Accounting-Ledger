package com.pluralsight;
//Import essential tools
import java.io.*;
import java.time.*;
import java.time.format.DateTimeParseException;
import java.util.*;
//This is the man app where the whole program runs
public class App {
    //Declares where the program will save and load the transaction data
    private static final String FILE_PATH = "dataFiles/transactions.csv"; // accessing .csv file through directory

    // Main method
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // allows user input
        boolean running = true; // if true continue
        createCsvIfNotExist();

        while (running) {
            showMenu();
            String choice = input.nextLine().toUpperCase();

            switch (choice) { // if not x, try this, if not this, try y etc.
                case "D":
                    addTransaction(input, true);
                    break; // end
                case "P":
                    addTransaction(input, false);
                    break;
                case "L":
                    viewLedger(input);
                    break;
                case "X":
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }

        input.close();
    }

    private static void showMenu() {
        System.out.println("\n--- Accounting Menu ---\n"); // Displays the menu to the user!
        System.out.println("    (D) Add Deposit");
        System.out.println("    (P) Add Payment");
        System.out.println("    (L) View Ledger");
        System.out.println("    (X) Exit\n");
        System.out.print("    Choose an option:  ");
    }

    // Java methpod that adds new transaction into CSV file
    private static void addTransaction(Scanner input, boolean isDeposit) {
        //If isDeposit is true → use "Add Deposit"
        //If isDeposit is false → use "Add Payment"
        System.out.println(isDeposit ? "\n--- Add Deposit ---" : "\n--- Add Payment ---");
        System.out.print("Description: ");
        String description = input.nextLine();
        System.out.print("Vendor: ");
        String vendor = input.nextLine();
        System.out.print("Amount: ");
        double amount = input.nextDouble();
        input.nextLine(); // clear newline

        if (!isDeposit) amount *= -1;

        String date = LocalDate.now().toString();
        String time = LocalTime.now().withNano(0).toString(); // remove nanoseconds
        String entry = String.format("%s|%s|%s|%s|%.2f", date, time, description, vendor, amount);

        writeToCsv(entry); // take this transaction and save it to the file
        System.out.println("\nTransaction Accepted!");
    }

    private static void viewLedger(Scanner input) { // displays a report menu
        boolean viewing = true;

        while (viewing) {
            System.out.println("\n--- Ledger ---\n");
            System.out.println("    (A) All Entries");
            System.out.println("    (D) Deposits");
            System.out.println("    (P) Payments");
            System.out.println("    (R) Reports");
            System.out.println("    (0) Back to Main Menu");
            System.out.print("     Choose an option: ");

            String choice = input.nextLine().toUpperCase();
            List<String> lines = readFromCsv(); // "Make a list of lines by reading them from the CSV file."


            switch (choice) {
                case "A":
                    printFiltered(lines, "ALL");
                    break;
                case "D":
                    printFiltered(lines, "DEPOSIT");
                    break;
                case "P":
                    printFiltered(lines, "PAYMENT");
                    break;
                case "R":
                    runReportsMenu(input);
                    break;
                case "0":
                    viewing = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    private static void runReportsMenu(Scanner input) {
        boolean inReports = true;

        while (inReports) {
            System.out.println("\n--- Reports ---\n");
            System.out.println("    (1) Month to Date");
            System.out.println("    (2) Previous Month");
            System.out.println("    (3) Year to Date");
            System.out.println("    (4) Previous Year");
            System.out.println("    (5) Search by Vendor");
            System.out.println("    (0) Go Back to Ledger");
            System.out.println("    (H) Go to Main Menu");
            System.out.print("     Choose an option: ");

            String choice = input.nextLine().toUpperCase();

            switch (choice) {
                case "1":
                    monthToDate();
                    break;
                case "2":
                    previousMonth();
                    break;
                case "3":
                    yearToDate();
                    break;
                case "4":
                    previousYear();
                    break;
                case "5":
                    System.out.print("Enter vendor name: ");
                    String vendor = input.nextLine();
                    searchByVendor(vendor);
                    break;
                case "0":
                    inReports = false;
                    break;
                case "H":
                    return; // Exit to main menu
                default:
                    System.out.println("Invalid report option.");
            }
        }
    }

    private static void printFiltered(List<String> lines, String type) { // reads every line from .csv
        for (String line : lines) {                                      // Filters each line based on report type
            String[] parts = line.split("\\|"); // Uses pipes to split a line from my .csv file
            if (parts.length < 5) continue; //This skips any lines that don’t have at least 5 sections.
                                            //Prevents errors from broken or incomplete lines.

            double amount;
            try {
                amount = Double.parseDouble(parts[4].replace(",", "")); // removes ,
            } catch (NumberFormatException e) {
                continue; // skip lines with bad amount format
            }

            if (type.equals("ALL") ||
                    (type.equals("DEPOSIT") && amount > 0) ||
                    (type.equals("PAYMENT") && amount < 0)) {
                System.out.println(line);
            }
        }
    }

    private static void writeToCsv(String line) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Failed to write to file.");
        }
    }

    private static List<String> readFromCsv() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Failed to read from file.");
        }
        return lines;
    }

    private static void createCsvIfNotExist() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Failed to create file.");
            }
        }
    }

    private static void monthToDate() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);

        System.out.println("\n--- Month to Date ---");
        filterByDate(startOfMonth, today);
    }

    private static void previousMonth() {
        LocalDate today = LocalDate.now();
        LocalDate start = today.minusMonths(1).withDayOfMonth(1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        System.out.println("\n--- Previous Month ---");
        filterByDate(start, end);
    }

    private static void yearToDate() {
        LocalDate today = LocalDate.now();
        LocalDate startOfYear = LocalDate.of(today.getYear(), 1, 1);

        System.out.println("\n--- Year to Date ---");
        filterByDate(startOfYear, today);
    }

    private static void previousYear() {
        LocalDate today = LocalDate.now();
        LocalDate start = LocalDate.of(today.getYear() - 1, 1, 1);
        LocalDate end = LocalDate.of(today.getYear() - 1, 12, 31);

        System.out.println("\n--- Previous Year ---");
        filterByDate(start, end);
    }

    private static void searchByVendor(String vendor) {
        List<String> lines = readFromCsv();
        System.out.println("\n--- Transactions for Vendor: " + vendor + " ---");
        for (String line : lines) {
            String[] parts = line.split("\\|"); // parts.length is how it seperates the data and
            if (parts.length >= 4 && parts[3].equalsIgnoreCase(vendor)) {
                System.out.println(line);
            }
        }
    }

    private static void filterByDate(LocalDate start, LocalDate end) {
        List<String> lines = readFromCsv();
        for (String line : lines) {
            String[] parts = line.split("\\|");
            try {
                LocalDate date = LocalDate.parse(parts[0]);
                if ((date.isEqual(start) || date.isAfter(start)) && (date.isEqual(end) || date.isBefore(end))) {
                    System.out.println(line);
                }
            } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
                System.out.println("Skipping invalid line: " + line);
            }
        }
    }
}

