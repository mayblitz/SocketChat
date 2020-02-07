package server.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import server.interfaces.Client;
import server.interfaces.Command;

import java.util.HashSet;

public class BroadcastCommandTest {

    // REVIEW: why is this declares as a member variable? Also why is it missing an access modifier?
    // I ask a lot of question, don't I?
    String actual;

    @Test
    public void testBroadcastCommand() {
        Client client = mock(Client.class);
        Client sender = mock(Client.class);
        String expected = "mock";

        doAnswer((arguments) -> {
            this.actual = "mock";
            return null;
        }).when(client).sendMessage(any());

        // REVIEW: So what's wrong here?
        HashSet<Client> set = new HashSet<>();
        set.add(client);

        Command command = new BroadcastCommand(set, sender, expected);
        command.execute();

        assertEquals(expected, this.actual);
    }
}
