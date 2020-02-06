package server.commands;

import server.interfaces.*;

import java.util.Set;

public final class ChangeNameCommand implements Command {
    private Set<Client> clients;
    private Client sender;
    private String[] args;

    public ChangeNameCommand(Set<Client> clients, Client sender, String[] args) {
        this.clients = clients;
        this.sender = sender;
        this.args = args;
    }

    public void execute() {
        if (this.args.length != 2) {
            this.sender.sendMessage("Invalid new username.");
        } else {
            String newName = this.args[1];
            for (Client client : this.clients) {
                if (client.getName().equals(newName)) {
                    this.sender.sendMessage("Username is taken.");
                    return;
                }
            }

            this.sender.setName(newName);
            this.sender.sendMessage("Successfully changed name to " + newName + "!");
        }
    }
}
