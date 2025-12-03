package org.cefet.sd.services.requestSender;

public class ReadRequestSenderService extends BaseRequestSender {
    @Override
    public void send() {
        try {
            String message = "LEITURA";
            System.out.println("Sending request: " + message);
            String response = this.loadBalancerProvider.sendRequest(message);
            System.out.println("Leitura recebida: " + response);
        } catch (Exception e) {
            System.out.println("Erro ao enviar leitura: " + e.getMessage());
        }
    }
}
