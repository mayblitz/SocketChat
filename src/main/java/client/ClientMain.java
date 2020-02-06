package client;

public class ClientMain {
    public static void main(String[] args) {
        SocketClient client = new SocketClient("localhost", 8082);
        client.start();
    }
}
