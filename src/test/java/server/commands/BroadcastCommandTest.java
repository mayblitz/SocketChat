package server.commands;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import server.interfaces.Client;
import server.interfaces.Command;

import java.util.HashSet;

public class BroadcastCommandTest {

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

        HashSet<Client> set = new HashSet<>();
        set.add(client);

        Command command = new BroadcastCommand(set, sender, expected);
        command.execute();

        assertEquals(expected, this.actual);
    }
}
