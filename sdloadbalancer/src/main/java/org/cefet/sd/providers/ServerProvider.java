package org.cefet.sd.providers;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerProvider {
    public void sendMessage(String host, int port, String message) {
        try {
            var socket = new Socket(host, port);
            var printWriter = new PrintWriter(socket.getOutputStream(), true);
            printWriter.println(message);
            socket.close();
        } catch (IOException e) {
            System.out.println("An error occurred with: " + host + ":" + port + " - " + e.getMessage());
        }
    }
}
