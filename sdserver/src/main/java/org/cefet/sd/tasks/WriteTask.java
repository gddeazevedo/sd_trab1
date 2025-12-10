package org.cefet.sd.tasks;

import org.cefet.sd.interfaces.MessageTypes;
import org.cefet.sd.services.GCDCalculatorService;
import org.cefet.sd.services.FileManagerService;
import org.cefet.sd.helpers.ServersManager;

public class WriteTask extends Task implements MessageTypes {
    private final GCDCalculatorService gcdCalculatorService;
    private final FileManagerService fileManagerService;

    public WriteTask() {
        this.gcdCalculatorService = new GCDCalculatorService();
        this.fileManagerService = new FileManagerService();
    }

    @Override
    public void handle(String message) {
        var parts = message.split("\\|");
        int firstNumber = Integer.parseInt(parts[1]);
        int secondNumber = Integer.parseInt(parts[2]);
        int gcd = this.gcdCalculatorService.calculateGCD(firstNumber, secondNumber);
        var messageToSave = "O MDC entre " + firstNumber + " e " + secondNumber + " é " + gcd;
        this.fileManagerService.write(messageToSave);
        ServersManager.addWriteRequest(message);
        ServersManager.replicateMessage(REPL + "|" + messageToSave);
    }
}
