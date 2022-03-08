package comms;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection implements Runnable {

    private MyTask controller;
    private Socket socket;
    private boolean ok;
    private Thread connectionThread;
    private DataOutputStream writer;
    private DataInputStream income;
    private HealthService hs;


    public Connection(MyTask controller) {
        this.controller = controller;
    }

    /**
     * Method to assign a Socket to channel
     * @param socket Socket to set
     */

    public synchronized void setSocket(Socket socket) {
        if (!this.ok) {
            this.ok = true;
            this.socket = socket;
            this.connectionThread = new Thread(this);
            this.connectionThread.start();
            this.hs = new HealthService(this);
            try {
                writer = new DataOutputStream((this.socket.getOutputStream()));
                income = new DataInputStream((this.socket.getInputStream()));
            } catch (IOException e) {
            }
        }
    }

    //Test per HealthService
    public void testCon(){
        DataOutputStream outputStream = null;
        try{
            outputStream = new DataOutputStream(this.socket.getOutputStream());
            outputStream.writeUTF("ALO");
        } catch (IOException e) {
            e.printStackTrace();
            this.ok = false;
        }
    }

    //Mètode x enviar missatges on puta toqui
    //TODO FICAR VALOR A NES SEND
    public void send() {
        try {
            String message = JOptionPane.showInputDialog("Escriu el missatge que vulguis enviar");
            writer.writeUTF(message);
        } catch (IOException e) {
            System.out.println("S'ha perdut la connexió, en breus es tornarà a connectar");
            this.ok = false;
        }
    }



    //this.ok = false;
    //Trucar per rebre estat sv
    public void receiveInfo() {
        while (true) {
            try {
                //Reiniciam els buffers d'entrada i de sortida per comprovar quin tipus de missatge reb l'altre peer,
                //i depenent del resultat que obté es compleix una condició o una altra
                income = new DataInputStream(this.socket.getInputStream());
                String rebut = null;
                rebut = income.readUTF();

                //Si reb el missatge de testCon "ALO", tot bé
                if (rebut.equals("ALO")) {
                    DataOutputStream outputStream = new DataOutputStream(this.socket.getOutputStream());
                    outputStream.writeUTF("OLA");

                    //Si reb el missatge de l'antic if
                } else if (rebut.equals("OLA")) {
                    this.hs.setTamoBien(true);
                    System.out.println("TAMO BIEN");
                } else {
                    //FARCEIX-ME
                    System.out.println("No s'ha rebut res");
                    this.setOk(false);
                }

            } catch (IOException e) {
                System.out.println("Error en rebre info, ja no estam ok");
                this.ok = false;
            }
        }
    }

    public String message() {
        while (true) {
            String missatge = null;
            DataInputStream rebent = null;
            try {
                System.out.println("EAEA");
                rebent = new DataInputStream(this.socket.getInputStream());
                missatge = rebent.readUTF();
                System.out.println(missatge);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return missatge;
        }
    }

    public void run() {
        while (this.isOk()) {
            try {
                receiveInfo();
                message();
            } catch (Exception e) {
                System.out.println(". " + e);
            }
        }
    }

    public synchronized boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public Thread getConThread() {
        return connectionThread;
    }

    public void setConThread(Thread conThread) {
        this.connectionThread = conThread;
    }

    public boolean isStatus() {
        return ok;
    }

    public void setStatus(boolean status) {
        this.ok = status;
    }

    public Socket getSocket() {
        return socket;
    }
}
