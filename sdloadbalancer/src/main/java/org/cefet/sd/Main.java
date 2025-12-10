package org.cefet.sd;

import org.cefet.sd.tasks.LoadBalancerTask;
import java.io.IOException;
import java.util.HashMap;


public class Main {
    static void main() {
        int port = 8888;
        var servers = new HashMap<String, Integer>();

        servers.put("localhost", 5001);
        servers.put("127.0.0.1", 5002);
        servers.put("127.0.0.2", 5003);

        try {
            LoadBalancerTask loadBalancerTask = new LoadBalancerTask(port, servers);
            loadBalancerTask.execute();
        } catch (IOException e) {
            System.out.println("An error occured: " + e.getMessage());
        }
    }
}
