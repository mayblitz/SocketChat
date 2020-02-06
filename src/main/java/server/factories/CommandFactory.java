package server.factories;

import server.commands.*;
import server.interfaces.*;

import java.util.Set;

public class CommandFactory {
    public Command createBroadcastCommand(Set<Client> clients, Client sender, String message) {
        return new BroadcastCommand(clients, sender, message);
    }

    public Command createChangeNameCommand(Set<Client> clients, Client sender, String[] args) {
        return new ChangeNameCommand(clients, sender, args);
    }

    public Command createSendPrivateCommand(Set<Client> clients, Client sender, String[] args) {
        return new SendPrivateCommand(clients, sender, args);
    }
}
