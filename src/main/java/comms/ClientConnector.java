package comms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientConnector implements Runnable {

    private String ip;
    private int port = 7070;
    private MyTask controller;
    private Connection connection;
    private ConnectionInfo connectionInfo;
    private boolean running = true;


    public ClientConnector(Connection connection, String ip, MyTask controller) {
        this.connection = connection;
        this.ip = ip;
        this.controller = controller;
        Thread clientThread = new Thread(this);
        clientThread.start();
    }

    //Crèdits a nen Dani per ajudar-me a descobrir que puc comparar Strings per assegurar que és es que toca, grax
    private void newConnection(){
        Socket socket;
        try {
            if (!this.connection.isOk()) {
                socket = new Socket(this.ip, this.port);
                DataOutputStream outPutStream = new DataOutputStream(socket.getOutputStream());
                String handshake = "RESIBIDO";
                outPutStream.writeUTF(handshake);
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                String rebut = inputStream.readUTF();

                if (rebut.equals("CLIENTIDENTIFICAT")) {
                    this.connection.setSocket(socket);
                    System.out.println("POR FIN S'HA ESTABLERT SA CONNEXIÓOOOOOO");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        @Override
    public void run() {
        while (this.running) {
            try {
                newConnection();
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void setIp(String clientIp) {
        this.ip = clientIp;
    }
}
