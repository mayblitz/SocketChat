package client;

public class ClientMain {
    public static void main(String[] args) {
        // REVIEW: what if I don't want to connect to localhost?
        // do I have to change the code every time I wish to connect to a different server?
        SocketClient client = new SocketClient("localhost", 8082);
        client.start();
    }
}
