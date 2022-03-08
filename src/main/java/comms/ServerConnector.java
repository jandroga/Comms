package comms;

import javax.swing.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerConnector implements Runnable {

    private int port = 7070;
    private MyTask controller;
    private Thread serverThread;
    private Connection connection;
    private Socket socket;
    private ServerSocket serverSocket;
    private boolean running = true;

    public ServerConnector(Connection connection, MyTask controller) {

        try {
            this.serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.controller = controller;
        this.connection = connection;
        this.serverThread = new Thread(this);
        this.serverThread.start();
    }

    private void setPort() {
        this.port = Integer.parseInt(JOptionPane.showInputDialog("Escriu el port del sv"));
    }

    //Sv hosteant per client

    private void startConnection() {
        try {
            if (!this.connection.isOk()) {
                //Acceptam
                this.socket = serverSocket.accept();
                System.out.println("Server furulant a nes port: " + this.port);
                String clientIp = this.socket.getInetAddress().getHostAddress();
                System.out.println("Connectant amb: " + clientIp);
                this.controller.setClientIp(clientIp);
                ClientIdentified clientIdentified = new ClientIdentified(this.socket, this.connection);
                clientIdentified.startIdCheck().start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void run() {
        while (this.running) {
            try {
                startConnection();
            } catch (Exception e) {
                System.out.println("Ha fallat per: " + e);
            }
        }
    }
    //this.socket = serverSocket.accept();
    //                this.controller.setConnectionCreated(true);
    //                if(this.controller.clientConnector == null) {
    //                    controller.connectionInfo.setIp(this.socket.getInetAddress().getHostAddress());
    //                    controller.connectionInfo.setPort(this.port);
    //                    controller.clientConnector = new ClientConnector(this.connection, controller.connectionInfo, this.controller);
    //                }
    //                String clientAddress = this.socket.getInetAddress().getHostAddress();
    //                System.out.println("ServerConnection: New connection from: " + clientAddress);
    //                ClientIdentified clientIdentified = new ClientIdentified(this.socket, this.connection, this.controller);
    //                clientIdentified.getIdentifiedThread().start();
}