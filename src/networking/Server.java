package networking;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import controller.Controller;

public class Server implements Runnable {
    private ServerSocket serverSocket;
    private boolean running = true;
    Controller main;

    public Server(int port, Controller main) throws IOException {
        this.main = main;
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            running = false;
            main.startServerError("There was a problem starting the game, it "
                    + "probably means the port you selected is already in use");
        }

        // serverSocket.setSoTimeout(10000);
    }

    public void stop() {
        System.out.println("server stopped");
        running = false;
        try {
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("exception in Server.stop");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void run() {
        while (running) {
            try {
                Socket server = serverSocket.accept();
                Channel channel = new Channel(server, main);
                main.setChannel(channel);
                running = false;
            } catch (SocketTimeoutException s) {
                System.out.println("Socket timed out!");
            } catch (IOException e) {
                System.out.println("exception in Client.run");
                stop();
            }
        }
    }

}