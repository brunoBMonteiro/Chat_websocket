package org.example;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {

    private static final String LOCAL_HOST = "127.0.0.1";
    private ClientMessager clientMessager;

    /**
     * Método inicia o cliente.
     * Inicia a classe ClientMessager que é responsável por tratar as mensagens enviadas e recebidas.
     * Uma nova thread iniciada para receber mensagens porque a lida de mensagens é bloqueante.
     * Assim, a aplicação pode ler e escrever mensagens.
     */
    public void start() throws IOException {
        try {
            clientMessager = new ClientMessager(new ClientSocket(new Socket(LOCAL_HOST, ChatServer.PORT)));
            System.out.println("Client initiated at port: " + clientMessager.getClientSocket().getLocalPort());
            System.out.println("Client connected at server: " + LOCAL_HOST + ":" + ChatServer.PORT);
            new Thread(clientMessager::receiveMessageLoop).start();
            clientMessager.messageLoop();
        } finally {
            clientMessager.close();
        }
    }

    // Método main padrão. Inicia cliente.
    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        try {
            client.start();
        } catch (IOException e) {
            System.out.println("Client initing error: " + e.getMessage());
        }
    }
}
