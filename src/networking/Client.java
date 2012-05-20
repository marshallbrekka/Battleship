package networking;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import model.Fleet;
import model.ShotLocation;
import controller.Controller;

public class Client implements Runnable {
    String serverName;
    int port;
    Controller main;
    Socket client;

    public Client(String serverName, int port, Controller main) {
        this.serverName = serverName;
        this.port = port;
        this.main = main;
    }

    @Override
    public void run() {
        try {
            System.out.println("trying to connect to server");
            this.client = new Socket(serverName, port);
            main.setChannel(new Channel(client, main));
        } catch (UnknownHostException e) {
            main.startClientError("There was an error connecting to the host. "
                    + "Make sure the address and port are correct");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("exception in Client.run");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void stop() {

        try {
            client.close();
        } catch (IOException e) {
            System.out.println("exception in Client.stop");
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}