package ProviderConsumer;

public class Main {
    public static void main(String[] args)
    {

        ProviderConsumer pb = new ProviderConsumer();

        String command = "subscribe, James, swimming pool";
        pb.processInput(command);

        command = "publish, planet fitness, swimming pool, Mesa, 17:00 to 18:00";
        pb.processInput(command);

        command = "publish, LA Fitness, swimming pool, Tempe, 16:00 to 17:00";
        pb.processInput(command);

        Event event = new Event("la", "zumba", "scottdale", "$1500");
        System.out.println(event.isValidEvent());
        command = "publish, la fitness, zumba, scottsdale, $15000.00";
        pb.processInput(command);
    }
}
