package server.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import server.interfaces.Client;
import server.interfaces.Command;

import java.util.HashSet;

import static org.mockito.Mockito.*;

public class ChangeNameCommandTest {

    // REVIEW: where are the access modifier here? The white beast ate 'em?
    @Mock
    Client client;

    @Mock
    Client sender;

    // private final static... etc. Have you ever heard of those? ;)
    String actual;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testChangeNameCommand() {
        String[] args = { "/changename", "mock"};
        when(this.client.getName()).thenReturn("anotherMock");
        doAnswer((arguments) -> {
            this.actual = "mock";
            return null;
        }).when(this.sender).setName(any());

        HashSet<Client> set = new HashSet<>();
        set.add(this.client);

        Command command = new ChangeNameCommand(set, this.sender, args);
        command.execute();

        assertEquals(args[1], this.actual);
    }

    @Test
    public void testUsernameTaken() {
        String[] args = { "/changename", "mock"};
        when(this.client.getName()).thenReturn("mock");
        doAnswer((arguments) -> {
            this.actual = "mock";
            return null;
        }).when(this.sender).setName(any());

        HashSet<Client> set = new HashSet<>();
        set.add(this.client);

        Command command = new ChangeNameCommand(set, this.sender, args);
        command.execute();

        assertNotEquals(args[1], this.actual);
    }

    @Test
    public void testInvalidUsername() {
        String[] args = { "mock" };
        doAnswer((arguments) -> {
            this.actual = arguments.toString();
            return null;
        }).when(this.sender).sendMessage(any());
        Command command = new ChangeNameCommand(null, this.sender, args);
        command.execute();

        assertTrue(this.actual.contains("Invalid new username."));
    }
}
