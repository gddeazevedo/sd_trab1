package org.cefet.sd.tasks;

import java.util.Random;
import org.cefet.sd.services.requestSender.ReadRequestSenderService;
import org.cefet.sd.services.requestSender.WriteRequestSenderService;


public class ClientTask {
    private static final double PROBABILITY_BOUND = 0.5;
    private static final int SLEEP_MS_LOWER_BOUND = 20;
    private static final int SLEEP_MS_UPPER_BOUND = 51; // 51 because in nextInt the second argument is exclusive

    private final Random random;
    private final ReadRequestSenderService readSenderService;
    private final WriteRequestSenderService writeSenderService;

    public ClientTask() {
        this.random = new Random();
        this.readSenderService = new ReadRequestSenderService();
        this.writeSenderService = new WriteRequestSenderService();
    }

    public void execute() {
        double probability = random.nextDouble();

        if (probability < PROBABILITY_BOUND) {
            writeSenderService.send();
        } else {
            readSenderService.send();
        }

        try {
            int sleepTime = random.nextInt(SLEEP_MS_LOWER_BOUND, SLEEP_MS_UPPER_BOUND);
            Thread.sleep(sleepTime);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
