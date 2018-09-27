package kafka;

/**
 * Created by jinyan on 5/3/17.
 */
public class KafkaConsumerDemo {
    public static void main(String[] args) {
        boolean isAsync = args.length == 0 || !args[0].trim().equalsIgnoreCase("sync");


        Consumer consumerThread = new Consumer(KafkaProperties.TOPIC);
        consumerThread.start();

    }
}
