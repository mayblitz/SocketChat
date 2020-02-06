package client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class WriteHandlerTest {

    @Mock
    Closeable client;

    @Mock
    PrintWriter writer;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testWriteHandler() throws IOException {
        String input = "exit";
        AtomicBoolean isClosed = new AtomicBoolean(false);

         doAnswer((arguments) -> {
             isClosed.set(true);
             return null;
         }).when(this.client).close();

        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        WriteHandler handler = new WriteHandler(this.writer, this.client);
        handler.run();

        assertTrue(isClosed.get());
    }
}
