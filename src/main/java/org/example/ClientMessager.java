package org.example;

import java.io.IOException;
import java.util.Scanner;

public class ClientMessager {

    // Classe responsável por encapsular socket. Adicionando lógica adicional a classe.
    private final ClientSocket clientSocket;

    // Classe responsável por ler do console.
    private Scanner scanner;

    public ClientMessager(ClientSocket clientSocket) {
        this.scanner = new Scanner(System.in);
        this.clientSocket = clientSocket;
    }

    // Getter padrão
    public ClientSocket getClientSocket() {
        return clientSocket;
    }

    // Loop de envio de mensagens. Se a mensagem for sair sai do programa.
    public void messageLoop() throws IOException {
        String msg;
        do {
            System.out.println("Type a message (sair to quit): ");
            msg = scanner.nextLine();
            clientSocket.sendMsg(msg);
        } while (!msg.equalsIgnoreCase("sair"));
    }

    // Loop de recebimento de mensagens.
    public void receiveMessageLoop() {
        String msg;
        while((msg = clientSocket.getMessage()) != null) {
            System.out.printf("Message received from server: %s\n", msg);
        }
    }

    // Finaliza a aplicação
    public void close() {
        clientSocket.close();
    }
}
