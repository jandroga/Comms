package comms;

import java.io.IOException;

public class HealthService implements Runnable{
    private Connection connection;
    private boolean tamoMal;

    public HealthService(Connection connection){
        this.connection = connection;
        Thread hpThread = new Thread(this);
        hpThread.start();
    }

    @Override
    public void run() {
    while (this.connection.isOk()){
        int segonsEspera = 0;
        setTamoBien(false);
        this.connection.testCon();
        while(segonsEspera < 5 && !this.tamoMal){
            segonsEspera++;
            try{
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    if (!this.tamoMal) {
        this.connection.setStatus(false);
        System.out.println("me cagunsaputa");
        try {
            this.connection.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }
    }

    public void setTamoBien(boolean b){
        this.tamoMal = b;
    }
}
