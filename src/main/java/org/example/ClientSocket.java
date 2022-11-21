package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Classe de encapsulamento de Socket Java padrão. Server para adicionar lógica adicional.
 */
public class ClientSocket {

    // Socket padrão do Java.
    private final Socket socket;

    // Classe para ler mensagens.
    private final BufferedReader in;

    // Classe para escrever mensagens.
    private final PrintWriter out;

    /**
     *
     */
    public ClientSocket(Socket socket) throws IOException {
        this.socket = socket;
        System.out.println("Client " + this.socket.getRemoteSocketAddress() + " connected.");
        this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.out = new PrintWriter(this.socket.getOutputStream(), true);;
    }

    // Getter padrão para porta do servidor
    public int getPort() {
        return socket.getPort();
    }

    // Getter padrão para porta do cliente
    public int getLocalPort() {
        return socket.getLocalPort();
    }

    // Getter Padrão para endereço remoto
    public SocketAddress getRemoteSocketAddress() {
        return socket.getRemoteSocketAddress();
    }

    // lê mensagem escrita
    public String getMessage() {
        try {
            return in.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    // finaliza socket
    public void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Erro ao fechar socket: " + e.getMessage());
        }
    }

    // envia mensagem
    public boolean sendMsg(String msg) {
        out.println(msg);
        return !out.checkError();
    }
}
