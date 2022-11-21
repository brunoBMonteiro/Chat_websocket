package org.example;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ServerMessager {

    // Lista de clientes inscritos no server.
    private final List<ClientSocket> clients = new LinkedList<>();

    /**
     * Adiciona cliente conectado ao servidor na lista de clientes.
     * Inicia uma thread para cada cliente conectado.
     * Utilizamos threads porque esperar uma mensagem do cliente é um procedimento bloqueante.
     * Assim podemos esperar de vários ao mesmo tempo.
     */
    public void addClient(ClientSocket clientSocket) {
        this.clients.add(clientSocket);
        new Thread(() -> clientMessageLoop(clientSocket)).start();
    }

    /**
     * Manda mensagem para todos os clientes da lista.
     */
    private void sendMessageToAll(ClientSocket sender, String msg) {
        Iterator<ClientSocket> iterator = clients.iterator();
        while (iterator.hasNext()) {
            ClientSocket clientSocket = iterator.next();
            if(!sender.equals(clientSocket)) {
                if(!clientSocket.sendMsg(msg)) {
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Manda mensagem para cliente específico.
     * Formato da mensagem ocorre como: send NUMERO_DA_PORTA_DO_CLIENTE mensagem a ser enviada
     *
     * Filtra clientes da lista que não possuem a porta destino.
     * Filtra origem para cliente não enviar mensagem para si mesmo.
     */
    private void sendMessageToSpecific(String msg, int source) {
        try {
            String[] arguments = msg.split(" ");

            int destiny = Integer.parseInt(arguments[1]);

            StringBuilder formmatedMessage = new StringBuilder();
            for (int i = 2; i < arguments.length; i++) {
                formmatedMessage.append(arguments[i] + " ");
            }

            clients.stream()
                    .filter(clientSocket -> clientSocket.getPort() == destiny)
                    .filter(clientSocket -> clientSocket.getPort() != source)
                    .forEach(clientSocket ->
                            clientSocket.sendMsg("Message from "+ source+ ": " + formmatedMessage));
        } catch (RuntimeException runtimeException) {
            System.out.println("Received message from client with invalid args!");
        }
    }

    /**
     * Espera mensagem de clientes. Se mensagem começa com send, método de enviar para específico é chamado.
     * Se não, envia para todos.
     */
    public void clientMessageLoop(ClientSocket clientSocket) {
        String msg;
        try {
            while ((msg = clientSocket.getMessage()) != null) {
                if("sair".equalsIgnoreCase(msg)) return;
                System.out.printf("Message recieved from %s: %s\n",
                        clientSocket.getRemoteSocketAddress(), msg);
                if(msg.startsWith("send")) {
                    sendMessageToSpecific(msg, clientSocket.getPort());
                } else {
                    sendMessageToAll(clientSocket, msg);
                }
            }
        } finally {
            clientSocket.close();
        }
    }
}
