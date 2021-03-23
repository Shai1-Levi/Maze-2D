package Client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * this class is for connect test package tp test the server.
 * new instance of client should contain the request we want from the server
 */
public class Client  {

    private IClientStrategy ClientStrategy;
    private InetAddress serverIP;
    private int port;

    public Client(InetAddress ServerIP, int Port, IClientStrategy iClientStrategy) {
        this.serverIP = ServerIP;
        this.port = Port;
        this.ClientStrategy = iClientStrategy;
    }

    /**
     * this method execute the test from the test package.
     */
    public void communicateWithServer() {
        try{
            Socket socket = new Socket(serverIP, port);
            //System.out.println("Client is connected to server!");
            ClientStrategy.clientStrategy(socket.getInputStream(), socket.getOutputStream());
            socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
