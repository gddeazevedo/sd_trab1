package org.cefet.sd.services;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadRequestHandlerService {
    private final HashMap<String, Integer> servers;
    private final ExecutorService executor;

    public ReadRequestHandlerService(HashMap<String, Integer> servers) {
        this.servers = servers;
        this.executor = Executors.newCachedThreadPool();
    }

    public void broadcastRequest(String message) {
        for (var entry : servers.entrySet()) {
            final String host = entry.getKey();
            final int port = entry.getValue();

            executor.submit(() -> sendToServer(host, port, message));
        }
    }

    private void sendToServer(String host, int port, String message) {
        try {
            Socket socket = new Socket(host, port);
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(message);
        } catch (IOException e) {
            System.out.printf("Erro enviando para %s:%d - %s%n", host, port, e.getMessage());
        }
    }
}
