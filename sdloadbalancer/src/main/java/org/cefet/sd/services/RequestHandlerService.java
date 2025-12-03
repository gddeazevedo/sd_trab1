package org.cefet.sd.services;

import java.io.IOException;
import java.util.HashMap;

public class RequestHandlerService {
    private static final String READ  = "LEITURA";
    private static final String WRITE = "ESCRITA";

    private final ReadRequestHandlerService readRequestHandlerService;
    private final WriteRequestHandlerService writeRequestHandlerService;

    public RequestHandlerService(HashMap<String, Integer> servers) {
        this.readRequestHandlerService = new ReadRequestHandlerService(servers);
        this.writeRequestHandlerService = new WriteRequestHandlerService(servers);
    }

    public void handle(String message) throws IOException {
        String requestType = message.split("\\|")[0];

        switch (requestType) {
            case READ:
                this.readRequestHandlerService
                        .broadcastRequest(message);
                break;
            case WRITE:
                this.writeRequestHandlerService
                        .sendRequestAtRandom(message);
                break;
        }
    }
}
