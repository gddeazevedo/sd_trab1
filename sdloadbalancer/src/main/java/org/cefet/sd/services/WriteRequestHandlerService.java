package org.cefet.sd.services;

import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.Map;
import org.cefet.sd.providers.ServerProvider;


public class WriteRequestHandlerService {
    private final HashMap<String, Integer> servers;
    private final ServerProvider serverProvider;

    public WriteRequestHandlerService(HashMap<String, Integer> servers) {
        this.servers = servers;
        this.serverProvider = new ServerProvider();
    }

    public void sendRequestAtRandom(String message) {
        var chosenServer = this.getServerAtRandom();
        String host = chosenServer.getKey();
        int port = chosenServer.getValue();
        System.out.println("Sending WRITE to " + port);
        this.serverProvider.sendMessage(host, port, message);
    }

    private Map.Entry<String, Integer> getServerAtRandom() {
        var random = new Random();
        var serversList = new ArrayList<Map.Entry<String, Integer>>(this.servers.entrySet());
        int index = random.nextInt(serversList.size());
        return serversList.get(index);
    }
}
