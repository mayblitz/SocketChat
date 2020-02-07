package server;

import java.io.IOException;

// REVIEW: as a general this has a clean and nice design. There are mostly code structure issues. But there are also
// somethings that could be improved.

// REVIEW: some general comments here
// REVIEW: you have committed your IDEA files. How about a .gitignore?
// REVIEW: a README.MD would be useful in the repo.
public class ServerMain {

    // REVIEW: the IOException is not thrown and is redundant here.
    public static void main(String[] args) throws IOException {
        Server server = new Server(8082);
        server.start();
    }
}