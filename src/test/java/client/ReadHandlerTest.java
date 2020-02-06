package client;

import client.interfaces.Connectable;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class ReadHandlerTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Mock
    Connectable client;

    @Mock
    BufferedReader reader;

    @BeforeEach
    public void setup() {
        System.setOut(new PrintStream(outContent));
        MockitoAnnotations.initMocks(this);
        when(this.client.isConnected()).thenReturn(true).thenReturn(false);
    }

    @Test
    public void testMessage() throws IOException {
        String expected = "mock";

        when(this.reader.readLine()).thenReturn(expected);

        ReadHandler handler = new ReadHandler(this.reader, this.client);
        handler.run();

        assertTrue(this.outContent.toString().contains(expected));
    }

    @Test
    public void testPrivateMessage() throws IOException {
        String message = "/w Mocker mock";
        String expected = "Mocker mock";

        when(this.reader.readLine()).thenReturn(message);

        ReadHandler handler = new ReadHandler(this.reader, this.client);
        handler.run();

        assertTrue(this.outContent.toString().contains(expected));
    }

    @Test
    public void testNoMessageDueToException() throws IOException {
        String expected = "mock";

        when(this.reader.readLine()).thenThrow(new IOException());

        ReadHandler handler = new ReadHandler(this.reader, this.client);
        handler.run();

        assertFalse(this.outContent.toString().contains(expected));
    }
}
