package org.cefet.sd.tasks;

import java.util.concurrent.locks.ReentrantLock;
import org.cefet.sd.services.FileManagerService;

public class ReadTask extends Task {
    private final FileManagerService fileManagerService;

    public ReadTask(ReentrantLock lock) {
        super(lock);
        this.fileManagerService = new FileManagerService();
    }

    @Override
    public void handle(String message) {
        lock.lock();
        try {
            int lineCount = this.fileManagerService.getNumberOfLines();
            System.out.println(message + ": Arquivo tem: " + lineCount + " linhas");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}
