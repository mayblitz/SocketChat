package client;

import client.interfaces.Connectable;

import java.io.*;
import java.net.Socket;

public class SocketClient implements Closeable, Connectable {
    private String address;
    private int port;
    private Socket socket;

    public SocketClient(String address, int port) {
        this.address = address;
        this.port = port;
    }

    public void start() {
        try {
            this.socket = new Socket(this.address, this.port);
            this.startReadThread(socket);
            this.startWriteThread(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startReadThread(Socket socket) throws IOException {
        InputStream input = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(input));
        ReadHandler readHandler = new ReadHandler(reader, this);
        Thread readThread = new Thread(readHandler);
        readThread.start();
    }

    private void startWriteThread(Socket socket) throws IOException {
        OutputStream stream = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(stream, true);
        WriteHandler writeHandler = new WriteHandler(writer, this);
        Thread writeThread = new Thread(writeHandler);
        writeThread.start();
    }

    public boolean isConnected() {
        return !this.socket.isClosed();
    }

    public void close() throws IOException {
        this.socket.close();
    }
}