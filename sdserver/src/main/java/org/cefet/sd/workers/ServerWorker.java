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
    private final ReentrantLock lock;

    public ServerWorker(Socket request, LinkedBlockingQueue<String> writeRequestsQueue,  ReentrantLock lock) {
        this.request = request;
        this.writeRequestsQueue = writeRequestsQueue;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            var printWriter = new PrintWriter(this.request.getOutputStream(), true);
            var inputStreamReader = new InputStreamReader(this.request.getInputStream());
            var bufferedReader = new BufferedReader(inputStreamReader);

            var message = bufferedReader.readLine();
            var messageType = message.split("\\|")[0];

            if (messageType.equals(READ)) {
                new ReadTask(lock).handle(message);
                printWriter.println("OK");
            }

            if (messageType.equals(WRITE)) {
                writeRequestsQueue.put(message);
                printWriter.println("OK");
            }

            if (messageType.equals(REPL)) {
                new ReplicateTask(lock).handle(message);
                printWriter.println("OK");
            }

            if (messageType.equals(COUNT)) {
                printWriter.println(ServersManager.getWriteRequestsCount());
            }

            this.request.close();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
