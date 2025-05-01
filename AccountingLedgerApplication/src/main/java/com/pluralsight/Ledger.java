package com.pluralsight;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ledger {
    private LocalDateTime dateTime;
    private String description;
    private String vendor;
    private double amount;

    // Constructor
    public Ledger(LocalDateTime dateTime, String description, String vendor, double amount) {
        this.dateTime = dateTime;
        this.description = description;
        this.vendor = vendor;
        this.amount = amount;
    }

    // Getters
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public String getVendor() {
        return vendor;
    }

    public double getAmount() {
        return amount;
    }

    // Convert to CSV-friendly string
    public String toCsvString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd|HH:mm");
        return String.format("%s | %s | %s | %.2f", dateTime.format(format), description, vendor, amount);
    }

    // Display nicely in console
    @Override
    public String toString() {
        return String.format("Date: %s, Description: %s, Vendor: %s, Amount: %.2f",
                dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")),
                description, vendor, amount);
    }
}
