package server;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.Whitebox;
import static org.mockito.Mockito.*;

import server.factories.CommandFactory;
import server.interfaces.Command;

import java.util.concurrent.atomic.AtomicBoolean;

public class MessageHandlerTest {

    // REVIEW: missing the access modifiers.
    AtomicBoolean hasSuccessfullyFinished;

    @Mock
    Command command;

    @Mock
    CommandFactory factory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.hasSuccessfullyFinished = new AtomicBoolean(false);
        doAnswer((arguments) -> {
            hasSuccessfullyFinished.set(true);
            return null;
        }).when(command).execute();
    }

    @Test
    public void testHandleBroadcast() {
        when(this.factory.createBroadcastCommand(any(), any(), any())).thenReturn(this.command);
        MessageHandler handler = new MessageHandler(null, null);
        Whitebox.setInternalState(handler, "factory", this.factory);
        handler.handle("mock");

        assertTrue(this.hasSuccessfullyFinished.get());
    }

    @Test
    public void testHandleChangeName() {
        when(this.factory.createChangeNameCommand(any(), any(), any())).thenReturn(this.command);
        MessageHandler handler = new MessageHandler(null, null);
        Whitebox.setInternalState(handler, "factory", this.factory);
        handler.handle("/changename mock");

        assertTrue(this.hasSuccessfullyFinished.get());
    }

    @Test
    public void testHandlePrivateMessage() {
        when(this.factory.createSendPrivateCommand(any(), any(), any())).thenReturn(this.command);
        MessageHandler handler = new MessageHandler(null, null);
        Whitebox.setInternalState(handler, "factory", this.factory);
        handler.handle("/w mock mock");

        assertTrue(this.hasSuccessfullyFinished.get());
    }
}
