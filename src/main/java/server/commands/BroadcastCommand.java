package server.commands;

import server.interfaces.*;

import java.util.Set;

public final class BroadcastCommand implements Command {
    private Set<Client> clients;
    private Client sender;
    private String message;

    public BroadcastCommand(Set<Client> clients, Client sender, String message) {
        this.clients = clients;
        this.sender = sender;
        this.message = message;
    }

    public void execute() {
        message = this.sender.getName() + ": " + message;
        for (Client client : this.clients) {
            if (client != sender) {
                client.sendMessage(message);
            }
        }
    }
}

