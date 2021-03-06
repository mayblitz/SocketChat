package server.commands;

import server.interfaces.*;

import java.util.Optional;
import java.util.Set;

// REVIEW: public classes should be documented.
public final class SendPrivateCommand implements Command {
    private Set<Client> clients;
    private Client sender;
    private String[] args;

    public SendPrivateCommand(Set<Client> clients, Client sender, String[] args) {
        this.clients = clients;
        this.sender = sender;
        this.args = args;
    }

    public void execute() {
        if (args.length != 3) {
            this.sender.sendMessage("Invalid command.");
        } else {
            String receiverName = args[1];
            // REVIEW: so if we have 500 000 chat user if we want to send one message we will iterate through them all
            // in order to find that one user? Nice.
            Optional<Client> receiver = this.clients.stream()
                    .filter(x -> x.getName().equals(receiverName))
                    .findFirst();

            if (receiver.isPresent()) {
                String message = "/w " + this.sender.getName() + ": " + args[2];
                receiver.get().sendMessage(message);
            } else {
                this.sender.sendMessage("User does not exist.");
            }
        }
    }
}
