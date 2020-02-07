package server.factories;

import server.commands.*;
import server.interfaces.*;

import java.util.Set;

// REVIEW: public classes should be documented.
public class CommandFactory {
    public Command createBroadcastCommand(Set<Client> clients, Client sender, String message) {
        return new BroadcastCommand(clients, sender, message);
    }

    // REVIEW: the args array - really nice way to pass arguments. Very meaningful. You can easily understand from the
    // interface what each argument does. If only there was another way to do it... oh wait there is ;)
    public Command createChangeNameCommand(Set<Client> clients, Client sender, String[] args) {
        return new ChangeNameCommand(clients, sender, args);
    }

    // REVIEW: the args array - really nice way to pass arguments. Very meaningful. You can easily understand from the
    // interface what each argument does. If only there was another way to do it... oh wait there is ;)
    public Command createSendPrivateCommand(Set<Client> clients, Client sender, String[] args) {
        return new SendPrivateCommand(clients, sender, args);
    }
}
