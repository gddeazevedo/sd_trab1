package org.cefet.sd.workers;

import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.LinkedBlockingQueue;
import org.cefet.sd.tasks.WriteTask;
import org.cefet.sd.interfaces.MessageTypes;
import org.cefet.sd.helpers.ServersManager;

public class QueueWorker extends Thread implements MessageTypes {
    private final LinkedBlockingQueue<String> writeRequestQueue;
    private final WriteTask writeTask;

    public QueueWorker(LinkedBlockingQueue<String> writeRequestQueue, ReentrantLock lock) {
        this.writeRequestQueue = writeRequestQueue;
        this.writeTask = new WriteTask(lock);
    }

    @Override
    public void run() {
        while (true) {
            try {
                if (writeRequestQueue.isEmpty()) {
                    Thread.sleep(10);
                    continue;
                }

                if (!ServersManager.verifyConsistency()) {
                    System.out.println("Waiting for cosistency");
                    Thread.sleep(100);
                }

                var message = writeRequestQueue.take();
                this.writeTask.handle(message);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
