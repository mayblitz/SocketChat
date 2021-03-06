package server;

import server.factories.CommandFactory;
import server.interfaces.Command;
import server.interfaces.Client;

import java.util.Set;
import java.util.regex.Pattern;

// REVIEW: document!
// REVIEW: This class couples the server with the client. It is play for such small task but normally an optimal
// solution would be to have the server independent of the client.
public class MessageHandler {

    private Set<Client> clients;
    private Client sender;
    private Pattern changeNamePattern;
    private Pattern privateMessagePattern;
    private CommandFactory factory;

    public MessageHandler(Set<Client> clients, Client sender) {
        this.clients = clients;
        this.sender = sender;
        // REVIEW: you could move those directly to the fields.
        this.changeNamePattern = Pattern.compile("^/changename\\s");
        this.privateMessagePattern = Pattern.compile("^/w\\s");
        this.factory = new CommandFactory();
    }

    public void handle(String message) {
        Command command;
        // REVIEW: More readable would be this.changeNamePattern.matcher(message).matches().
        if (this.changeNamePattern.matcher(message).find()) {
            // REVIEW: dat variable name. MINDBLOWN.
            // REVIEW: what if I have clicked by mistake two spaces?
            String[] args = message.split("\\s");
            command = this.factory.createChangeNameCommand(this.clients, this.sender, args);
        } else if (this.privateMessagePattern.matcher(message).find()) {
            String[] args = message.split("\\s", 3);
            command = this.factory.createSendPrivateCommand(this.clients, this.sender, args);
        } else {
            command = this.factory.createBroadcastCommand(this.clients, this.sender, message);
        }

        command.execute();
    }
}
