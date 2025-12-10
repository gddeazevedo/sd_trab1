package org.cefet.sd.services;

import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

public class LoggerService {
    private final String fileName;

    public LoggerService() {
        this.fileName = "./data/client_log.txt";
        this.createLogFile();
    }
    
    public void registerLog(int firstNumber, int secondNumber) {
        boolean mustAppend = true;

        try {
            var fileWriter = new FileWriter(this.fileName, mustAppend);
            var text = this.getTextToRegister(firstNumber, secondNumber);
            fileWriter.write(text);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String getTextToRegister(int firstNumber, int secondNumber) {
        return firstNumber + ", " + secondNumber + "\n";
    }

    private void createLogFile() {
        try {
            var file = new File(this.fileName);

            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
