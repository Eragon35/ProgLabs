package Lab7;

import Lab3.Bagazh;
import Lab3.Humanoid;
import Lab3.Palace;
import Lab3.Predmet;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;

public class InputFromDB {
    private static List<Predmet> baggage = new LinkedList<>();
    public static void read(SortedMap<Humanoid, List<Predmet>> map) {
        System.out.println("Reading data from DB");
        try{
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(DBconfigs.url, DBconfigs.dbUser, DBconfigs.dbPassword);
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM s207704.human";
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.isBeforeFirst()) {
                System.out.println("Data in table 'Human' in DB not found");
            }
            else while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String place = resultSet.getString("place");
                int user_id = resultSet.getInt("user_id");
                Humanoid hm = new Humanoid(name, Palace.valueOf(place), user_id);
                baggage.clear();
                addBaggage("hat", id, statement);
                addBaggage("bottle", id, statement);
                addBaggage("bag", id, statement);
                map.put(hm, baggage);
                connection.close();
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Data from DB was read");
    }
    private static void addBaggage(String s, int id, Statement statement) throws SQLException {
        String sql = "SELECT * FROM s207704." + s + "WHERE human_id = " + id;
        ResultSet baggage = statement.executeQuery(sql);
        if (baggage.isBeforeFirst()) {
            while (baggage.next()){
                String name = baggage.getString("name");
                double value = baggage.getInt("value");
                switch (s){
                    case "hat": InputFromDB.baggage.add(new Predmet.Shlyapa(name, value)); break;
                    case "bottle": InputFromDB.baggage.add(new Predmet.Butilka(name, value)); break;
                    case "bag": InputFromDB.baggage.add(new Bagazh(name, value)); break;
                }
            }
        }
    }
}
