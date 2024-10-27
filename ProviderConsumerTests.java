package ProviderConsumer;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.junit.Assert.assertEquals;

public class ProviderConsumerTests {

    private static ProviderConsumer pb;

    @BeforeClass
    public static void setupProducerBuyer(){
        pb = new ProviderConsumer();
    }

    @After
    public void resetProducerBuyer(){
        pb.reset();
    }

    @Test
//    Test 1: one subscriber and multiple publishers for same facility

    public void testOneSubMulPub(){

        List<String> expected = new ArrayList<>(Arrays.asList(
                "james notified swimming pool: planet fitness, mesa, 17:00 to 18:00",
                "james notified swimming pool: la fitness, tempe, 16:00 to 17:00",
                "james notified swimming pool: 24 hour fitness, phoenix, 14:00 to 15:00"
        ));

        pb.processInput("subscribe, James, swimming pool");
        pb.processInput("publish, planet fitness, swimming pool, Mesa, 17:00 to 18:00");
        pb.processInput("publish, LA Fitness, swimming pool, Tempe, 16:00 to 17:00");
        pb.processInput("publish, 24 HOUR Fitness, swimming pool, Phoenix, 14:00 to 15:00");

        List<String> actual = pb.getAggregatedOutput().stream()
                .map(String::toLowerCase)
                .map(String::strip)
                .collect(Collectors.toList());

        assertEquals(expected, actual);

    }

    @Test
//    Test 2: One subscriber and one publisher with different facilities being published

    public void testTwoOneSubOnePubDifferentFacilities(){

        List<String> expected = new ArrayList<>(Arrays.asList(
                "james notified swimming pool: planet fitness, mesa, 17:00 to 18:00",
                "james notified cardio: planet fitness, mesa, 16:00 to 17:00",
                "james notified yoga: planet fitness, mesa, 14:00 to 16:00"
        ));

        pb.processInput("subscribe, James, swimming pool");
        pb.processInput("subscribe, James, cardio");
        pb.processInput("subscribe, James, yoga");
        pb.processInput("publish, planet fitness, swimming pool, mesa, 17:00 to 18:00");
        pb.processInput("publish, planet fitness, cardio, mesa, 16:00 to 17:00");
        pb.processInput("publish, planet fitness, yoga, mesa, 14:00 to 16:00");

        List<String> actual = pb.getAggregatedOutput().stream()
                .map(String::toLowerCase)
                .map(String:: strip).collect(Collectors.toList());

        assertEquals(expected, actual);

    }


    @Test
//    Test 3 : illegal parameters in publishing

    public void testThreeIllegalParameters(){

        List<String> expected = new ArrayList<>();

        pb.processInput("subscribe, James, zumba");
        pb.processInput("publish, la fitness, zumba, scottsdale, $15000.00");

        List<String> actual = pb.getAggregatedOutput();

        assertEquals(expected, actual);
    }


    @Test
//    Test 4: Multiple Provider and Consumer with different facilities.
//
    public void testFourMulPubMulSubDiffFacility(){
        List<String> expected = new ArrayList<>(Arrays.asList(
                "james notified swimming pool: planet fitness, mesa, 13:00 to 16:00",
                "mary notified cardio: la fitness, mesa, 16:00 to 17:00",
                "james notified yoga: 24 hour fitness, mesa, 14:00 to 16:00"
        ));

        pb.processInput("subscribe, James, swimming pool");
        pb.processInput("subscribe, Mary, cardio");
        pb.processInput("subscribe, James, yoga");
        pb.processInput("publish, planet fitness, Swimming Pool, mesa, 13:00 to 16:00");
        pb.processInput("publish, la fitness, cardio, mesa, 16:00 to 17:00");
        pb.processInput("publish, 24 hour fitness, yoga, mesa, 14:00 to 16:00");

        List<String> actual = pb.getAggregatedOutput().stream()
                .map(String::toLowerCase)
                .map(String:: strip).collect(Collectors.toList());

        assertEquals(expected, actual);
    }


    @Test
//    Test 5: Multiple Provider and Consumer with different facilities. All three commands are fired
    public void testFiveMulPubMulSubWithAllThreeCommands(){
        List<String> expected = new ArrayList<>(Arrays.asList(
                "james notified swimming pool: planet fitness, mesa, 8:00 to 12:00",
                "mary notified cardio: la fitness, mesa, 9:00 to 11:00",
                "james notified yoga: 24 hour fitness, mesa, 6:00 to 8:00"
        ));

        pb.processInput("subscribe, James, swimming pool");
        pb.processInput("subscribe, Mary, cardio");
        pb.processInput("subscribe, James, yoga");
        pb.processInput("publish, planet fitness, Swimming Pool, mesa, 8:00 to 12:00");
        pb.processInput("publish, la fitness, cardio, mesa, 9:00 to 11:00");
        pb.processInput("publish, 24 hour fitness, yoga, mesa, 6:00 to 8:00");
        pb.processInput("unsubscribe, James, yoga");
        pb.processInput("publish, la fitness, yoga, mesa, 6:00 to 8:00");

        List<String> actual = pb.getAggregatedOutput().stream()
                .map(String::toLowerCase)
                .map(String:: strip).collect(Collectors.toList());

        assertEquals(expected, actual);
    }

}
