package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Response {
    private ServerCommand command;
    private SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();

    public Response(ServerCommand command, SortedMap<Humanoid, List<Predmet>> map) {
        this.command = command;
        this.map = map;
    }

    public Response() {}

    public ServerCommand getCommand() { return command; }

    public void setCommand(ServerCommand command) { this.command = command; }

    public SortedMap<Humanoid, List<Predmet>> getMap() {return map; }

    public void setMap(SortedMap<Humanoid, List<Predmet>> map) {this.map = map; }
}
