package server.interfaces;

// REVIEW: public interfaces should ALWAYS be documented.
public interface Client {

    // REVIEW: document this.
    String getName();

    // REVIEW: document this.
    void setName(String name);

    // REVIEW: document this.
    void sendMessage(String message);
}
