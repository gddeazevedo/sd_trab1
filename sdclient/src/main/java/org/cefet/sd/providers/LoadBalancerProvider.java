package org.cefet.sd.providers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class LoadBalancerProvider {
    protected final int loadBalancerPort;
    protected final String loadBalancerIp;

    public LoadBalancerProvider() {
        this.loadBalancerPort = 8888;
        this.loadBalancerIp   = "0.0.0.0";
    }

    public String sendRequest(String message) throws IOException {
        boolean mustAutoFlush = true;

        try (var socket = new Socket(loadBalancerIp, loadBalancerPort);
             var printWriter = new PrintWriter(socket.getOutputStream(), mustAutoFlush);
             var inputStreamReader = new InputStreamReader(socket.getInputStream());
             var bufferedReader = new BufferedReader(inputStreamReader)
        ) {
            printWriter.println(message);
            return bufferedReader.readLine();
        }
    }
}
