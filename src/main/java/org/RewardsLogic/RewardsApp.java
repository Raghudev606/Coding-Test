package org.RewardsLogic;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

public class RewardsApp {
    public static void main(String[] args) {


        //1. Validate Arguments
        if (args.length != 1) {
            System.err.println("Invalid input. Please provide the CSV file containing the records as an input argument!");
            System.exit(-1);
        }

        try {
            //2. Read Input file
            List<String> result = Files.readAllLines(Paths.get(args[0]));


            //3. Convert input data into internal DB
            Map<String, List<Transaction>> map = RewardsProcessor.parseTransactionRecords(result);


            //4. Print rewards for each customer

            for (String customer : map.keySet()) {
                Map<String, Double> reward = RewardsProcessor.calculateRewardsPerCustomer(map.get(customer));
                System.out.printf("Rewards summary for %s:  %n", customer);

                double total=0;

                for (String month : reward.keySet()) {
                    double monthlyreward = reward.get(month);
                            total += monthlyreward ;
                    System.out.printf("\t %s  : %s %n", month, monthlyreward);
                }
                System.out.printf("\tTotal Rewards: %.2f%n%n", total);

            }

        } catch (IOException e) {
            System.err.println(e.getMessage() + "\nUnable to open the input file at " + args[0]);

        } catch (DateTimeParseException e) {
            System.err.println(e.getMessage() + "\nUnable to parse the data file");
        }


    }
}
