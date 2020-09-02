package Lab7;

import Lab3.Humanoid;
import Lab3.Predmet;
import Lab6.ClientCommand;
import Lab6.Request;
import Lab6.Response;
import Lab6.ServerCommand;

import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Stream;

public class ServerHandlerV2 extends Thread {
    @Override
    public void run() {
            super.run();
    }
    public static void reader(Request request, SortedMap<Humanoid, List<Predmet>> map, Response response){
        ClientCommand command = request.getCommand();
        Integer hashCode;
        switch (command){
            case remove_all:
                hashCode = request.getBaggage().hashCode();
                map.keySet().removeIf(key -> hashCode.equals(map.get(key).hashCode()));
                response.setMap(map);
                response.setCommand(ServerCommand.success);
                break;
            case remove:
                map.keySet().removeIf(key -> key.equals(request.getHuman()));
                response.setMap(map);
                response.setCommand(ServerCommand.success);
                break;
            case add_if_max:
                List<Predmet> baggage;
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
                response.setMap(map);
                response.setCommand(ServerCommand.success);
                break;
            case insert:
                map.put(request.getHuman(), request.getBaggage());
                response.setMap(map);
                response.setCommand(ServerCommand.success);
                break;
            case remove_lower:
                map.keySet().stream().filter(key -> map.get(key).hashCode() < request.getBaggage().hashCode()).forEach(ServerHandlerV2::delete);
                //map.keySet().removeIf(key -> map.get(key).hashCode() < request.getBaggage().hashCode());
                response.setMap(map);
                response.setCommand(ServerCommand.success);
                break;
            case get_map:
                response.setMap(map);
                response.setCommand(ServerCommand.success);
                break;
            //TODO: think about add userId to request not to response;
            case sign_in:
                int id = 0;
                try {
                    id = Authorization.signIn(request.getUser());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (id == 0) response.setCommand(ServerCommand.auth_error_user_not_found);
                else if (id == -1) response.setCommand(ServerCommand.auth_error_wrong_password);
                else if (id > 0){
                    response.setUserId(id);
                    response.setCommand(ServerCommand.success);
                }
                else response.setCommand(ServerCommand.error);
                break;
            case add_user:
                try {
                    Authorization.addUser(request.getUser(), response);

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            default:
                response.setCommand(ServerCommand.error);
                break;
        }
    }
    private static void delete(Humanoid humanoid){


    }
}
