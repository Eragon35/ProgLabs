package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Response {
    private ServerCommand response;
    private SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();

    public Response(ServerCommand response, SortedMap<Humanoid, List<Predmet>> map) {
        this.response = response;
        this.map = map;
    }
}
