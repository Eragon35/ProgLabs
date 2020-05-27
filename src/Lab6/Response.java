package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Response {
    private ServerResponse response;
    private SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();

    public Response(ServerResponse response, SortedMap<Humanoid, List<Predmet>> map) {
        this.response = response;
        this.map = map;
    }
}
