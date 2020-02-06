package server.interfaces;

public interface Client {
    String getName();

    void setName(String name);

    void sendMessage(String message);
}
