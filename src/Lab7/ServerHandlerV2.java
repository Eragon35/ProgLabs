package Lab7;

import Lab3.Humanoid;
import Lab3.Predmet;
import Lab3.Sumka;
import Lab6.ClientCommand;
import Lab6.Request;
import Lab6.Response;
import Lab6.ServerCommand;

import java.sql.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class ServerHandlerV2 implements Runnable {
    private static final List<String> tables = Arrays.asList("bag", "hat", "bottle");

    private final Request request;
    private final ConcurrentHashMap<Humanoid, List<Predmet>> map;
    private final Response response = new Response();

    public ServerHandlerV2(Request request, ConcurrentHashMap<Humanoid, List<Predmet>> map) {
        this.request = request;
        this.map = map;
    }

    @Override
    public void run() {
        ClientCommand command = request.getCommand();
        response.setAddress(request.getAddress());
        Integer hashCode;
        int id = Authorization.signIn(request.getUser());
        System.out.println("Handling " + command.toString() + " from " + request.getUser().getName() + " with id = " + id);
        if (id == 0) response.setCommand(ServerCommand.auth_error_user_not_found);
        else if (id == -1) response.setCommand(ServerCommand.auth_error_wrong_password);
        else if (id < -1) response.setCommand(ServerCommand.error);
        else {
            request.getUser().setId(id);
            response.setUserId(id);
            if (request.getHuman() != null) request.getHuman().setUser_id(id);
            switch (command) {
                case remove_all:
                    hashCode = request.getBaggage().hashCode();
                    map.keySet().stream()
                            .filter(key -> request.getUser().getId() == key.getUser_id())
                            .filter(key -> hashCode.equals(map.get(key).hashCode()))
                            .forEach(ServerHandlerV2::delete);
                    //                map.keySet().removeIf(key -> hashCode.equals(map.get(key).hashCode()));
                    map.keySet().removeAll(map.keySet().stream()
                            .filter(key -> request.getUser().getId() == key.getUser_id())
                            .filter(key -> hashCode.equals(map.get(key).hashCode()))
                            .collect(Collectors.toSet()));
                    response.setMap(map);
                    response.setCommand(ServerCommand.success);
                    break;
                case remove:
                    assert request.getHuman() != null;
                    delete(request.getHuman());
                    map.keySet().removeIf(key -> (key.equals(request.getHuman()) && key.getUser_id() == request.getUser().getId()));
                    response.setMap(map);
                    response.setCommand(ServerCommand.success);
                    break;
                case add_if_max:
                    List<Predmet> baggage = request.getBaggage();
                    if (map.size() > 0) {
                        int maxHashCode = Integer.MIN_VALUE;
                        for (Humanoid key : map.keySet()) {
                            if (map.get(key).hashCode() > maxHashCode) maxHashCode = map.get(key).hashCode();
                        }
                        if (baggage.hashCode() > maxHashCode) {
                            insert(request.getHuman(), baggage);
                            map.put(request.getHuman(), baggage);
                        }
                    } else {
                        insert(request.getHuman(), baggage);
                        map.put(request.getHuman(), baggage);
                    }
                    response.setMap(map);
                    response.setCommand(ServerCommand.success);
                    break;
                case insert:
                    insert(request.getHuman(), request.getBaggage());
                    map.put(request.getHuman(), request.getBaggage());
                    response.setMap(map);
                    response.setCommand(ServerCommand.success);
                    break;
                case remove_lower:
                    map.keySet().stream()
                            .filter(key -> request.getUser().getId() == key.getUser_id())
                            .filter(key -> map.get(key).hashCode() < request.getBaggage().hashCode())
                            .forEach(ServerHandlerV2::delete);
                    map.keySet().removeAll(map.keySet().stream()
                            .filter(key -> request.getUser().getId() == key.getUser_id())
                            .filter(key -> map.get(key).hashCode() < request.getBaggage().hashCode()).collect(Collectors.toSet()));
                    response.setMap(map);
                    response.setCommand(ServerCommand.success);
                    break;
                case get_map:
                case show:
                case info:
                    response.setMap(map);
                    response.setCommand(ServerCommand.success);
                    break;
                //            case sign_in: break;
                case add_user:
                    Authorization.addUser(request.getUser(), response);
                    response.setCommand(ServerCommand.success);
                    break;
                case other:
                default:
                    response.setCommand(ServerCommand.error);
                    break;
            }
            ServerV2.responseQueue.add(response);
        }
    }

    private static void delete(Humanoid humanoid) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DBconfigs.url, DBconfigs.dbUser, DBconfigs.dbPassword);
            Statement statement = connection.createStatement();
            String select = "SELECT id FROM s207704.human WHERE name ='" + humanoid.getName() +
                    "' AND place = '" + humanoid.getPlace() + "'";
            ResultSet rs = statement.executeQuery(select);
            int id = -1;
            while (rs.next()) {
                id = rs.getInt("id");
            }
            String sql = "DELETE FROM s207704.human WHERE id = " + id;
            statement.executeUpdate(sql);
            for (String table : tables) {
                String delete = "DELETE FROM s207704." + table + " WHERE human_id = " + id;
                statement.executeUpdate(delete);
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insert(Humanoid human, List<Predmet> baggage) {
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DBconfigs.url, DBconfigs.dbUser, DBconfigs.dbPassword);
            String sql = "INSERT INTO s207704.human (user_id, name, place) Values (?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, human.getUser_id());
            preparedStatement.setString(2, human.getName());
            preparedStatement.setString(3, human.getPlace().toString());
            preparedStatement.executeUpdate();
            int human_id = -1;
            String select = "SELECT id FROM s207704.human WHERE name = '" + human.getName() +
                    "' and place = '" + human.getPlace() + "' and user_id = '" + human.getUser_id() + "'";
            ResultSet resultSet = connection.createStatement().executeQuery(select);

            if (!resultSet.isBeforeFirst() ) {
                System.out.println("Return shit from db");
            }
            else {
                resultSet.next();
                human_id = resultSet.getInt("id");
            }
            if ((baggage.size() > 0) && (human_id > 0)) {
                for (Predmet predmet : baggage){
                    PreparedStatement preparedStatementBaggage;
                    String insert = "";
                    if (predmet instanceof Predmet.Butilka) {
                        insert = "INSERT INTO s207704.bottle (human_id, name, value) Values (?, ?, ?)";
                    }
                    if (predmet instanceof Predmet.Shlyapa) {
                        insert = "INSERT INTO s207704.hat (human_id, name, value) Values (?, ?, ?)";
                    }
                    if (predmet instanceof Sumka) {
                        insert = "INSERT INTO s207704.bag (human_id, name, value) Values (?, ?, ?)";
                    }
                    preparedStatementBaggage = connection.prepareStatement(insert);
                    preparedStatementBaggage.setInt(1, human_id);
                    preparedStatementBaggage.setString(2, predmet.getName());
                    preparedStatementBaggage.setDouble(3, predmet.getValue());
                    preparedStatementBaggage.executeUpdate();
                }
            }
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
