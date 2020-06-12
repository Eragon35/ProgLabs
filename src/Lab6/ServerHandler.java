package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

public class ServerHandler {
    public static void reader(Request request, SortedMap<Humanoid, List<Predmet>> map, Response response){
        ClientCommand command = request.getCommand();
        Integer hashCode;
        switch (command){
            case remove_all:
                hashCode = request.getBaggage().hashCode();
                map.keySet().removeIf(key -> hashCode.equals(map.get(key).hashCode()));
                response.setCommand(ServerCommand.success);
                break;
            case remove:
                map.keySet().removeIf(key -> key.equals(request.getHuman()));
                response.setCommand(ServerCommand.success);
                break;
            case add_if_max:
                List<Predmet> baggage = new LinkedList<>();
                baggage = request.getBaggage();
                if (map.size() > 0) {
                    int maxHashCode = map.get(map.firstKey()).hashCode();
                    for (Humanoid key : map.keySet()) {
                        if (map.get(key).hashCode() > maxHashCode) maxHashCode = map.get(key).hashCode();
                    }
                    if (baggage.hashCode() > maxHashCode) map.put(request.getHuman(), baggage);
                }
                else {
                    map.put(request.getHuman(), baggage);
                }
                response.setCommand(ServerCommand.success);
                break;
            case insert:
                map.put(request.getHuman(), request.getBaggage());
                response.setCommand(ServerCommand.success);
                break;
            case remove_lower:
                map.keySet().removeIf(key -> map.get(key).hashCode() < request.getBaggage().hashCode());
                response.setCommand(ServerCommand.success);
                break;
            default:
                break;
        }
    }
}
