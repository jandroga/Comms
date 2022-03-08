package comms;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyTask extends JFrame{
    private Connection connection;
    private int port;
    public ClientConnector clientConnector;
    private ServerConnector serverConnector;

    public MyTask(String ip){
        super("Comms 542.0");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setSize(500,500);
        this.setVisible(true);
        this.setLayout(new BorderLayout());

        this.connection = new Connection(this);
        this.serverConnector = new ServerConnector(this.connection, this);
        this.clientConnector = new ClientConnector(this.connection, ip, this);

        JButton send = new JButton("Clicka per enviar un missatge deprova");
        send.addActionListener(e -> connection.send());
        send.setVisible(true);
        this.add(send);
    }

    private void buttons(){
        JButton startSv = new JButton("Clicka per començar un sv");
        JButton startClient = new JButton("Clicka per començar un client");
    }

    private void selectPort(){
        this.port = Integer.parseInt(JOptionPane.showInputDialog("Escriu el port"));
    }

    public static void main(String[] args) {
        String firstIp = JOptionPane.showInputDialog("Escriu la ip a la que te vols connectar");
        new MyTask(firstIp);
    }

    public void setClientIp(String clientIp) {
        this.clientConnector.setIp(clientIp);
    }

}
