package org.cefet.sd.tasks;

import java.util.HashMap;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import org.cefet.sd.services.RequestHandlerService;

public class LoadBalancerTask {
    private final RequestHandlerService requestHandlerService;

    public LoadBalancerTask(HashMap<String, Integer> servers) {
        this.requestHandlerService = new RequestHandlerService(servers);
    }

    public void execute(ServerSocket server) throws IOException {
        while (true) {
            Socket request = server.accept();
            var inputStreamReader = new InputStreamReader(request.getInputStream());
            var bufferedReader = new BufferedReader(inputStreamReader);
            String message = bufferedReader.readLine();
            this.requestHandlerService.handle(message);
            request.close();
        }
    }
}
