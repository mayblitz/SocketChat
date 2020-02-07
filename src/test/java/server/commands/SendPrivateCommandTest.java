package server.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import server.interfaces.Client;
import server.interfaces.Command;

import java.util.HashSet;

import static org.mockito.Mockito.*;

public class SendPrivateCommandTest {

    // REVIEW: again missing the access modifiers.
    @Mock
    Client client;

    @Mock
    Client sender;

    String actual;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendPrivateCommand() {
        String[] args = { "/w", "mock", "message"};
        when(this.client.getName()).thenReturn("mock");
        when(this.sender.getName()).thenReturn("smith");
        doAnswer((arguments) -> {
            this.actual = "mock";
            return null;
        }).when(this.sender).getName();

        doAnswer((arguments) -> {
            this.actual = "message";
            return null;
        }).when(this.client).sendMessage(any());

        HashSet<Client> set = new HashSet<>();
        set.add(this.client);

        Command command = new SendPrivateCommand(set, this.sender, args);
        command.execute();

        assertEquals(args[2], this.actual);
    }

    @Test
    public void testUserNotExist() {
        String[] args = { "/w", "mock", "message"};
        when(this.sender.getName()).thenReturn("smith");

        doAnswer((arguments) -> {
            this.actual = arguments.toString();
            return null;
        }).when(this.sender).sendMessage(any());

        HashSet<Client> set = new HashSet<>();

        Command command = new SendPrivateCommand(set, this.sender, args);
        command.execute();

        assertTrue(this.actual.contains("User does not exist."));
    }

    @Test
    public void testInvalidInput() {
        String[] args = { "qwerty"};
        doAnswer((arguments) -> {
            this.actual = arguments.toString();
            return null;
        }).when(this.sender).sendMessage(any());

        Command command = new SendPrivateCommand(null, this.sender, args);
        command.execute();

        assertTrue(this.actual.contains("Invalid command."));
    }

}
