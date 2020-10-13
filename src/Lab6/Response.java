package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class Response implements Serializable {
    private ServerCommand command;
    private ConcurrentHashMap<Humanoid, List<Predmet>> map = new ConcurrentHashMap<>();
    private int userId;
    private InetSocketAddress address;


    public Response() {}

    public ServerCommand getCommand() { return command; }

    public void setCommand(ServerCommand command) { this.command = command; }

    public ConcurrentHashMap<Humanoid, List<Predmet>> getMap() {return map; }

    public void setMap(ConcurrentHashMap<Humanoid, List<Predmet>> map) {this.map = map; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }

    public InetSocketAddress getAddress() { return address; }

    public void setAddress(InetSocketAddress address) { this.address = address; }
}
