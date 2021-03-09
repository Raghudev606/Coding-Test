package org.RewardsLogic;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.summingDouble;

/**
 * RewardsProcessor
 */
public class RewardsProcessor {

    static DateTimeFormatter INPUT_DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    static DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MMM");

    static LocalDate cutoff = LocalDate.now().minusMonths(3);

    public static Map<String, List<Transaction>> parseTransactionRecords(List<String> records) throws DateTimeParseException {
        Map<String, List<Transaction>> txns = new HashMap<>();
        for (String l : records) {
            String[] terms = l.split(",");
            String name = terms[1];
            LocalDate txnDate = LocalDate.parse(terms[0], INPUT_DATE_FORMATTER);
            if (txnDate.isAfter(cutoff)) {
                String month = txnDate.format(YEAR_MONTH_FORMATTER);

                Transaction t = new Transaction(month, Double.parseDouble(terms[2]));

                List<Transaction> list = txns.get(name);
                if (list == null)
                    list = new LinkedList<>();
                list.add(t);
                txns.put(name, list);

            }

        }
        return txns;

    }


    public static Map<String, Double> calculateRewardsPerCustomer(List<Transaction> customerTransactionList) {
        return customerTransactionList.stream().collect(Collectors.groupingBy(Transaction::getYearMoth, TreeMap::new,summingDouble(t->calculateRewardByAmount(t.getAmount()))));

    }

    public static double calculateRewardByAmount(double amount) {
        return amount < 50 ? 0 : amount < 100.0 ? (amount - 50) : 50 + (amount - 100) * 2;
    }
}

   


