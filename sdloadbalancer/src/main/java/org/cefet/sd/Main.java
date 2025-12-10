package org.cefet.sd;

import org.cefet.sd.tasks.LoadBalancerTask;
import java.io.IOException;
import java.util.HashMap;


public class Main {
    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8888"));
        var servers = getServers();

        try {
            LoadBalancerTask loadBalancerTask = new LoadBalancerTask(port, servers);
            loadBalancerTask.execute();
        } catch (IOException e) {
            System.out.println("An error occured: " + e.getMessage());
        }
    }

    private static HashMap<String, Integer> getServers() {
        var servers = new HashMap<String, Integer>();
        String serversEnv = System.getenv("SERVERS");

        if (serversEnv == null || serversEnv.isEmpty()) {
            servers.put("localhost", 5001);
            servers.put("127.0.0.1", 5002);
            servers.put("127.0.0.2", 5003);
            return servers;
        }

        String[] serverList = serversEnv.split(",");

        for (String server : serverList) {
            String[] parts = server.trim().split(":");
            var host = parts[0];
            var port = Integer.parseInt(parts[1]);
            servers.put(host, port);
        }

        return servers;
    }
}
