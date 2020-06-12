package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

public class ServerHandler {
    public static void reader(Request cmd, SortedMap<Humanoid, List<Predmet>> map){
        ClientCommand command = cmd.getCommand();
        Integer hashCode;
        switch (command){
            case remove_all:
                hashCode = cmd.getBaggage().hashCode();
                map.keySet().removeIf(key -> hashCode.equals(map.get(key).hashCode()));
                break;
            case remove:
                map.keySet().removeIf(key -> key.equals(cmd.getHuman()));
                break;
            case add_if_max:
                List<Predmet> baggage = new LinkedList<>();
                baggage = cmd.getBaggage();
                if (map.size() > 0) {
                    int maxHashCode = map.get(map.firstKey()).hashCode();
                    for (Humanoid key : map.keySet()) {
                        if (map.get(key).hashCode() > maxHashCode) maxHashCode = map.get(key).hashCode();
                    }
                    if (baggage.hashCode() > maxHashCode) map.put(cmd.getHuman(), baggage);
                }
                else {
                    map.put(cmd.getHuman(), baggage);
                }
                break;
            case insert:
                map.put(cmd.getHuman(), cmd.getBaggage());
                break;
            case remove_lower:
                map.keySet().removeIf(key -> map.get(key).hashCode() < cmd.getBaggage().hashCode());
                break;
            default:
                break;
        }
    }
}
