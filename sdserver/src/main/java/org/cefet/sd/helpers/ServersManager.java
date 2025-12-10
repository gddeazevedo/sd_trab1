package org.cefet.sd.helpers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.HashMap;
import java.util.List;
import org.cefet.sd.interfaces.MessageTypes;
import org.cefet.sd.providers.ServerProvider;

public class ServersManager implements MessageTypes {
    private static final List<String> writeRequests = Collections.synchronizedList(new ArrayList<>());
    private static final HashMap<String, Integer> allServers = new HashMap<>();
    private static final ServerProvider serverProvider = new ServerProvider();
    private static int currentPort;


    public static void setCurrentPort(int port) {
        currentPort = port;
    }

    public static void addWriteRequest(String message) {
        writeRequests.add(message);
    }

    public static int getWriteRequestsCount() {
        return writeRequests.size();
    }

    public static boolean verifyConsistency() {
        HashSet<Integer> counterSet = new HashSet<>();
        counterSet.add(writeRequests.size());

        allServers.put("localhost", 5001);
        allServers.put("127.0.0.1", 5002);
        allServers.put("127.0.0.2", 5003);

        for (var entry : allServers.entrySet()) {
            String host = entry.getKey();
            int port = entry.getValue();

            if (port == currentPort) {
                continue;
            }

            try {
                var response = serverProvider.send(COUNT, host, port);
                System.out.println("RESPONSE: " + response);
                counterSet.add(Integer.parseInt(response));
            } catch (IOException e) {
                System.out.println("Error 5: " + e.getMessage());
            }
        }

        return counterSet.size() == 1;
    }

    public static void replicateMessage(String message) {
        allServers.put("localhost", 5001);
        allServers.put("127.0.0.1", 5002);
        allServers.put("127.0.0.2", 5003);

        for (var entry : allServers.entrySet()) {
            String host = entry.getKey();
            int port = entry.getValue();

            if (port == currentPort) {
                continue;
            }

            try {
                serverProvider.send(message, host, port);
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
