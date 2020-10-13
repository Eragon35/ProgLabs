package Lab7;

import Lab3.Bagazh;
import Lab3.Humanoid;
import Lab3.Palace;
import Lab3.Predmet;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class InputFromDB {
    private static final List<Predmet> baggage = new LinkedList<>();
    public static void read(ConcurrentHashMap<Humanoid, List<Predmet>> map) {
        System.out.println("Reading data from DB");
        try {
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DBconfigs.url, DBconfigs.dbUser, DBconfigs.dbPassword);
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM s207704.human";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {

//            if (resultSet.next() == false) {
//                System.out.println("Data in table 'Human' in DB not found");
//            } else do {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String place = resultSet.getString("place");
                int user_id = resultSet.getInt("user_id");
                Humanoid hm = new Humanoid(name, Palace.valueOf(place), user_id);
                baggage.clear();
                readBaggage("hat", id, connection);
                readBaggage("bottle", id, connection);
                readBaggage("bag", id, connection);
                map.put(hm, baggage);
//            } while (resultSet.next());

            }
            resultSet.close();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Can't connect to DataBase");
            e.printStackTrace();
            System.exit(20);
        }
        System.out.println("Data from DB was read");
    }
    private static void readBaggage(String s, int id, Connection connection) throws SQLException {
        Statement baggageStatement = connection.createStatement();
        String sql = "SELECT * FROM s207704." + s + " WHERE human_id = " + id;
        ResultSet baggage = baggageStatement.executeQuery(sql);
        if (baggage.isBeforeFirst()) {
            while (baggage.next()){
                String name = baggage.getString("name");
                double value = baggage.getDouble("value");
                switch (s){
                    case "hat": InputFromDB.baggage.add(new Predmet.Shlyapa(name, value)); break;
                    case "bottle": InputFromDB.baggage.add(new Predmet.Butilka(name, value)); break;
                    case "bag": InputFromDB.baggage.add(new Bagazh(name, value)); break;
                }
            }
        }
        baggageStatement.close();
    }
}
