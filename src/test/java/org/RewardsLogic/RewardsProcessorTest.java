package org.RewardsLogic;

import org.junit.Test;

import java.text.ParseException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Unit test for simple App.
 */
public class RewardsProcessorTest {

    public static final double DELTA = 0.001;

    @Test
    public void testParseTransactionRecords() throws ParseException {
        Map<String, List<Transaction>> output = RewardsProcessor.parseTransactionRecords(
                List.of("01/14/2021,Gary,147\n",
                        "01/23/2021,Gary,109\n",
                        "12/09/2019,Rose,132"));

        assertEquals(output.size(), 1);
        assertEquals(output.get("Gary").size(), 2);


    }

    @Test(expected = DateTimeParseException.class)
    public void testParseTransactionRecordsThrowsParseException() throws DateTimeParseException {
        Map<String, List<Transaction>> output = RewardsProcessor.parseTransactionRecords(
                List.of("01/14/202,Gary,147\n" +
                        "01/23/2021,Gary,109\n" +
                        "12/09/2019,Rose,132"));

    }

    @Test
    public void testCalculateRewardsPerCustomer() {
        Map<String, List<Transaction>> output = RewardsProcessor.parseTransactionRecords(List.of("01/14/2021,Gary,147\n",
                "01/23/2021,Gary,109\n",
                "12/09/2021,Gary,132"));
        Map<String, Double> rewards = RewardsProcessor.calculateRewardsPerCustomer(output.get("Gary"));
        assertEquals(2, rewards.size());
        assertEquals(212, rewards.get("2021-Jan").doubleValue(), DELTA);

    }

    @Test
    public void testCalculateRewardByAmount() {
        assertEquals(0, RewardsProcessor.calculateRewardByAmount(0), DELTA);
        assertEquals(0, RewardsProcessor.calculateRewardByAmount(40), DELTA);
        assertEquals(0, RewardsProcessor.calculateRewardByAmount(50), DELTA);
        assertEquals(10, RewardsProcessor.calculateRewardByAmount(60), DELTA);
        assertEquals(49, RewardsProcessor.calculateRewardByAmount(99), DELTA);

        assertEquals(50, RewardsProcessor.calculateRewardByAmount(100), DELTA);
        assertEquals(90, RewardsProcessor.calculateRewardByAmount(120), DELTA);
        assertEquals(150, RewardsProcessor.calculateRewardByAmount(150), DELTA);


    }

}
