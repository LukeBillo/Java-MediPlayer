package server;

import java.io.IOException;
import java.net.*;

class ServerNetwork implements Runnable
{
    private final int SERVER_PORT = 45612;

    private ServerSocket _serverSocket = null;
    private ServerModel _dataModel = new ServerModel();

    ServerNetwork()
    {
        try {
            _serverSocket = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            System.err.println("I/O Exception when creating server socket on port " + SERVER_PORT + ".");
        }
    }

    public void run()
    {
        boolean running = true;

        while (running)
        {
            try {
                Socket newClient = _serverSocket.accept();
                new Thread(new ClientConnection(newClient, _dataModel)).start();

            } catch (IOException e) {
                System.err.println("There was an I/O Exception when accepting an incoming client connection.");
            }
        }
    }
}
