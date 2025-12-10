package org.cefet.sd.workers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

import org.cefet.sd.helpers.ServersManager;
import org.cefet.sd.tasks.ReadTask;
import org.cefet.sd.tasks.ReplicateTask;
import org.cefet.sd.interfaces.MessageTypes;

public class ServerWorker extends Thread implements MessageTypes {
    private final Socket request;
    private final LinkedBlockingQueue<String> writeRequestsQueue;
    private final ReadTask readTask;
    private final ReplicateTask replicateTask;

    public ServerWorker(Socket request, LinkedBlockingQueue<String> writeRequestsQueue, ReentrantLock lock) {
        this.request = request;
        this.writeRequestsQueue = writeRequestsQueue;
        this.readTask = new ReadTask(lock);
        this.replicateTask = new ReplicateTask(lock);
    }

    @Override
    public void run() {
        try {
            var printWriter = new PrintWriter(this.request.getOutputStream(), true);
            var bufferedReader = new BufferedReader(new InputStreamReader(this.request.getInputStream()));

            var message = bufferedReader.readLine();
            var messageType = message.split("\\|")[0];

            switch (messageType) {
                case READ -> {
                    readTask.handle(message);
                    printWriter.println("OK");
                }
                case WRITE -> {
                    writeRequestsQueue.put(message);
                    printWriter.println("OK");
                }
                case REPL -> {
                    replicateTask.handle(message);
                    printWriter.println("OK");
                }
                case COUNT -> printWriter.println(ServersManager.getWriteRequestsCount());
                default -> printWriter.println("Unknown message type");
            }

            request.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
