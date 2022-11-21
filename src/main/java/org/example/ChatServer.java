package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {
    public static final int PORT = 4000;
    private ServerSocket serverSocket;

    public void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        clientConnectionLoop();
        System.out.println("Servidor iniciando na porta: " + PORT);
    }

    public void clientConnectionLoop() throws IOException{
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("Cliente " + clientSocket.getRemoteSocketAddress() + " conectou!");
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String message = in.readLine();
            System.out.println("Mensagem recebida do cliente " + clientSocket.getRemoteSocketAddress()
                    + ": " + message);
        }
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        try {
            server.start();
        } catch (IOException ex) {
            System.out.println("Erro ao iniciar o servidor: " + ex.getMessage());
        }
        System.out.println("SERVIDOR TERMINOU");
    }
}