package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {

    public void start() throws IOException {
        int PORT = 4000;
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("_________________TESTE___________" + PORT);
    }

    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        try {
            server.start();
        } catch (IOException ex) {
            //Logger.getLogger(ChatServer.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro ao iniciar o servidor: " + ex.getMessage());
        }
        System.out.println("SERVIDOR TERMINOU");
    }
}