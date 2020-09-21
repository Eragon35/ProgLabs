package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.io.Serializable;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Response implements Serializable {
    private ServerCommand command;
    private SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();
//    TODO: think about need of userId
    private int userId;


    public Response() {}

    public ServerCommand getCommand() { return command; }

    public void setCommand(ServerCommand command) { this.command = command; }

    public SortedMap<Humanoid, List<Predmet>> getMap() {return map; }

    public void setMap(SortedMap<Humanoid, List<Predmet>> map) {this.map = map; }

    public int getUserId() { return userId; }

    public void setUserId(int userId) { this.userId = userId; }
}
