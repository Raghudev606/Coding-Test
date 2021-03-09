package org.RewardsLogic;

import java.time.LocalDate;
import java.util.Date;

public class Transaction {

    private String yearMoth;
    private double amount;

    public Transaction(String yearMoth, double amount) {
        this.yearMoth = yearMoth;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public String getYearMoth() {
        return yearMoth;
    }
}
