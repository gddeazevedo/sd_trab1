package org.cefet.sd.tasks;

import java.util.concurrent.locks.ReentrantLock;
import org.cefet.sd.interfaces.MessageTypes;
import org.cefet.sd.services.CalculatorService;
import org.cefet.sd.services.FileManagerService;
import org.cefet.sd.helpers.ServersManager;

public class WriteTask extends Task implements MessageTypes {
    private final CalculatorService CalculatorService;
    private final FileManagerService fileManagerService;

    public WriteTask(ReentrantLock lock) {
        super(lock);
        this.CalculatorService = new CalculatorService();
        this.fileManagerService = new FileManagerService();
    }

    @Override
    public void handle(String message) {
        var parts = message.split("\\|");

        int firstNumber = Integer.parseInt(parts[1]);
        int secondNumber = Integer.parseInt(parts[2]);
        int mdc = this.CalculatorService.calculate(firstNumber, secondNumber);

        var messageToSave = "O MDC entre " + firstNumber + " e " + secondNumber + " é " + mdc;

        lock.lock();
        try {
            this.fileManagerService.write(messageToSave);
            ServersManager.addWriteRequest(message);
        } finally {
            lock.unlock();
        }

        ServersManager.replicateMessage(REPL + "|" + messageToSave);
    }
}
