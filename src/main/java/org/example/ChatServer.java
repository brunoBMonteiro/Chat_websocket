package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ChatServer {

    //Porta fixa para facilitar conexão dos clientes com o servidor.
    public static final int PORT = 4000;

    // Classe responsável por aceitar conexões de outros sockets.
    private ServerSocket serverSocket;

    // Classe responsável pelo tratamento de mensagens do servidor.
    private ServerMessager serverMessager;

    /**
     * Método start inicia o server. IOException é capturada e tratada no main.
     *
     * Função do this.serverSocker: inicia o socket de servidor com a porta fixa de 4000.
     * A porta é fixa para os clientes saberem exatamente a porta que devem conectar para enviar mensagens.
     *
     * ServerMessager: classe responsável por tratar as mensagens vinda dos clientes
     *
     * **/
    public void start() throws IOException {
        this.serverSocket = new ServerSocket(PORT);
        this.serverMessager = new ServerMessager();
        clientConnectionLoop();
        System.out.println("Server initiated on port: " + PORT);
    }

    /**
     * Método que espera clientes se conectarem.
     * Ao cliente se conectar adiciona ele na lista de clientes.
     * */
    public void clientConnectionLoop() throws IOException{
        while (true) {
            ClientSocket clientSocket = new ClientSocket(serverSocket.accept());
            serverMessager.addClient(clientSocket);
        }
    }

    // Método main padrão. Inicia o server.
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        try {
            server.start();
        } catch (IOException ex) {
            System.out.println("Error initianing the server: " + ex.getMessage());
        }
        System.out.println("Server has shutdown!");
    }
}