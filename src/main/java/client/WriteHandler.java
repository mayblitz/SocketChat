package client;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

// REVIEW: public classes should be documented!
public class WriteHandler implements Runnable {
    private PrintWriter writer;
    private Closeable client;

    public WriteHandler(PrintWriter writer, Closeable client){
        this.writer = writer;
        this.client = client;
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        String message;
        do {
            message = scanner.nextLine();
            this.writer.println(message);
        } while(!message.equals("exit"));

        try {
            this.client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}