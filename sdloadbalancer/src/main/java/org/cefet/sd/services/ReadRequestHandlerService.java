package org.cefet.sd.services;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.cefet.sd.providers.ServerProvider;

public class ReadRequestHandlerService {
    private final HashMap<String, Integer> servers;
    private final ExecutorService executor;
    private final ServerProvider serverProvider;

    public ReadRequestHandlerService(HashMap<String, Integer> servers) {
        this.servers = servers;
        this.executor = Executors.newCachedThreadPool();
        this.serverProvider = new ServerProvider();
    }

    public void broadcastRequest(String message) {
        for (var entry : servers.entrySet()) {
            final String host = entry.getKey();
            final int port = entry.getValue();
            System.out.println("Sending READ to " + port);
            executor.submit(() -> this.serverProvider.sendMessage(host, port, message));
        }
    }
}
