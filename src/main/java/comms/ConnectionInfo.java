package comms;

import java.net.InetAddress;

public class ConnectionInfo {
    public String ip;
    public int port;

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}