package org.cefet.sd.tasks;

import java.util.concurrent.locks.ReentrantLock;

import org.cefet.sd.helpers.ServersManager;
import org.cefet.sd.services.FileManagerService;

public class ReplicateTask extends Task {
    private final ReentrantLock lock;
    private final FileManagerService fileManagerService;

    public ReplicateTask(ReentrantLock lock) {
        this.lock = lock;
        this.fileManagerService = new FileManagerService();
    }

    @Override
    public void handle(String message) {
        var parts = message.split("\\|");
        var messageToSave = parts[1];

        lock.lock();
        try {
            this.fileManagerService.write(messageToSave);
            ServersManager.addWriteRequest(message);
        } catch (Exception e) {
            System.out.println("Error 6: " + e.getMessage());
        } finally {
            lock.unlock();
        }
    }
}
