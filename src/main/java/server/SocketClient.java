package server;

import server.interfaces.Client;

import java.io.*;
import java.net.Socket;
import java.util.Set;

// REVIEW: document!
public class SocketClient implements Runnable, Client {
    private Socket socket;
    private Server server;
    private String name;
    private PrintWriter writer;

    public SocketClient(Socket socket, Server server, String name) {
        this.socket = socket;
        this.server = server;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void sendMessage(String message) {
        this.writer.println(message);
    }

    public void run() {
        try {
            MessageHandler handler = this.createMessageHandler();
            BufferedReader reader = this.createReader();
            this.initWriter();
            handler.handle(this.name + " joined.");
            this.sendMessage("username: " + this.name);
            String message;
            do {
                message = reader.readLine();
                handler.handle(message);
            } while (!message.equals("exit"));

            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            this.server.removeClient(this);
        }
    }

    private MessageHandler createMessageHandler () {
        Set<Client> clients = this.server.getClients();
        return new MessageHandler(clients, this);
    }

    private BufferedReader createReader() throws IOException {
        InputStream input = this.socket.getInputStream();
        return new BufferedReader(new InputStreamReader(input));
    }

    private void initWriter() throws IOException {
        OutputStream output = this.socket.getOutputStream();
        this.writer = new PrintWriter(output, true);
    }
}