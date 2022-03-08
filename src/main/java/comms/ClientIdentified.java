package comms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientIdentified implements Runnable {

    private Socket socket;
    private MyTask controller;
    private Connection connection;
    private Thread checkConns;
    private boolean clientIdentified = false;

    public Thread startIdCheck() {
        return checkConns;
    }

    public ClientIdentified(Socket socket, Connection connection) {
        this.socket = socket;
        this.connection = connection;
        this.checkConns = new Thread(this);
    }

    //
    @Override
    public void run() {
        while (!this.clientIdentified) {
            try {
                DataInputStream inputStream = new DataInputStream(this.socket.getInputStream());
                String handshake = inputStream.readUTF();
                if (handshake.equals("RESIBIDO")){
                    System.out.println("Detectada nova connexió");
                    this.connection.setSocket(this.socket);
                    DataOutputStream dataOutputStream = new DataOutputStream(this.socket.getOutputStream());
                    dataOutputStream.writeUTF("CLIENTIDENTIFICAT");
                } else {
                    System.out.println("No s'ha pogut determinar l'origen");
                }
                this.clientIdentified = true;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}