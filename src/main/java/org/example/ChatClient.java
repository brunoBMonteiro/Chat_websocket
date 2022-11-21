package org.example;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_ADDRESS = "127.0.0.1";
    private Scanner scanner;
    private BufferedWriter out;

    public ChatClient() {
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        Socket clientSocket = new Socket(SERVER_ADDRESS, ChatServer.PORT);
        this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        System.out.println("Cliente conectado ao servidor em " + SERVER_ADDRESS + ":" + ChatServer.PORT);
        messageLoop();
    }

    private void messageLoop() throws IOException {
        String entryMessage;
        do {
            System.out.println("Digite uma mensagem (ou sair para finalizar): ");
            entryMessage = scanner.nextLine();
            out.write(entryMessage);
            out.newLine();
            out.flush();
        } while (!entryMessage.equalsIgnoreCase("sair"));
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient();
        try {
            client.start();
        } catch (IOException e) {
            System.out.println("Erro ao iniciar cliente! " + e.getMessage());
        }
    }
}
