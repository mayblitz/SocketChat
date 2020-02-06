package client;

import client.interfaces.Connectable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.Pattern;

public class ReadHandler implements Runnable {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_PURPLE = "\u001B[35m";

    private BufferedReader reader;
    private Connectable client;
    private Pattern privateMessagePattern;

    public ReadHandler(BufferedReader reader, Connectable client) {
        this.reader = reader;
        this.client = client;
        this.privateMessagePattern = Pattern.compile("^/w\\s");
    }

    public void run() {
        while (client.isConnected()) {
            try{
                String message = this.reader.readLine();
                if (this.privateMessagePattern.matcher(message).find()) {
                    String[] args = message.split("\\s", 2);
                    System.out.println(ANSI_PURPLE + args[1]);
                } else {
                    System.out.println(ANSI_RESET + message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
