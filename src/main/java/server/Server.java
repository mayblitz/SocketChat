package server;

import server.interfaces.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

// REVIEW: document!
public class Server {
    // REVIEW: you NEVER declare a collection by its implementation. Which SOLID principle does this line violate?
    private HashSet<Client> clients;
    private int port;

    public Server(int port) {
        this.clients = new HashSet<>();
        this.port = port;
    }

    // Nice!
    public Set<Client> getClients() {
        return Collections.unmodifiableSet(this.clients);
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(this.port)) {
            // REVIEW: alright why not just implement a graceful shutdown like you did for the client and avoid
            // waiting for process termination?
            while (true) {
                Socket socket = server.accept();
                String name = this.getNewUsername();
                SocketClient client = new SocketClient(socket, this, name);
                this.clients.add(client);
                Thread clientThread = new Thread(client);
                clientThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void removeClient(SocketClient client) {
        this.clients.remove(client);
    }

    private String getNewUsername() {
        String name;
        boolean exists;
        int number = 1;
        do {
            name = "User" + number++;
            exists = this.clients.stream().map(Client::getName).anyMatch(name::equals);
        } while (exists);
        return name;
    }
}
