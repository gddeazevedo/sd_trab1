package org.cefet.sd;

import java.net.ServerSocket;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;
import org.cefet.sd.services.FileManagerService;
import org.cefet.sd.workers.QueueWorker;
import org.cefet.sd.workers.ServerWorker;
import org.cefet.sd.helpers.ServersManager;


public class Server {
    static void main(String[] args) {
        int port = Integer.parseInt(System.getenv("PORT"));

        var lock = new ReentrantLock();
        var writeRequestsQueue = new LinkedBlockingQueue<String>();

        var fileName = "./data/server_" + port + ".txt";
        new FileManagerService(fileName)
                .createFile();

        new QueueWorker(writeRequestsQueue, lock).start();
       
        try (var server = new ServerSocket(port)) {
            System.out.println("Server [" + port + "] started");

            ServersManager.setCurrentPort(port);
            ServersManager.setServers();

            while (true) {
                var request = server.accept();
                new ServerWorker(request, writeRequestsQueue, lock).start();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
