package org.cefet.sd.services;

import java.util.HashMap;
import java.util.Random;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.net.Socket;
import java.io.PrintWriter;


public class WriteRequestHandlerService {
    private final HashMap<String, Integer> servers;

    public WriteRequestHandlerService(HashMap<String, Integer> servers) {
        this.servers = servers;
    }

    public void sendRequestAtRandom(String message) throws IOException {
        var chosenServer = this.getServerAtRandom();

        String host = chosenServer.getKey();
        int port = chosenServer.getValue();

        var socket = new Socket(host, port);
        var printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(message);
        socket.close();
    }

    private Map.Entry<String, Integer> getServerAtRandom() {
        var random = new Random();
        var serversList = new ArrayList<Map.Entry<String, Integer>>(this.servers.entrySet());
        int index = random.nextInt(serversList.size());
        return serversList.get(index);
    }
}
