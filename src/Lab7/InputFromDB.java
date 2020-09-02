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
    public static void read(SortedMap<Humanoid, List<Predmet>> map) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DBconfigs.url, DBconfigs.dbUser, DBconfigs.dbPassword)) {
            Statement statement = connection.createStatement();
            String sql = "SELECT * FROM s207704.human";
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.isBeforeFirst()) {
                System.out.println("users not found");
            }
            else while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String place = resultSet.getString("place");
                int user_id = resultSet.getInt("user_id");
                Humanoid hm = new Humanoid(name, Palace.valueOf(place), user_id);
                List<Predmet> baggage = new LinkedList<>();
                String sqlHat = "SELECT * FROM s207704.hat" +
                        "WHERE human_id = " + id;
                ResultSet baggageHat = statement.executeQuery(sqlHat);
                if (baggageHat.isBeforeFirst()) {
                    while (baggageHat.next()){
                        String hatName = baggageHat.getString("name");
                        double value = baggageHat.getInt("value");
                        baggage.add(new Predmet.Shlyapa(hatName, value));
                    }
                }
                String sqlBottle = "SELECT * FROM s207704.bottle" +
                        "WHERE human_id = " + id;
                ResultSet baggageBottle = statement.executeQuery(sqlBottle);
                if (baggageBottle.isBeforeFirst()) {
                    while (baggageBottle.next()){
                        String bottleName = baggageBottle.getString("name");
                        double value = baggageBottle.getInt("value");
                        baggage.add(new Predmet.Butilka(bottleName, value));
                    }
                }
                String sqlBag = "SELECT * FROM s207704.bag" +
                        "WHERE human_id = " + id;
                ResultSet baggageBag = statement.executeQuery(sqlBag);
                if (baggageBag.isBeforeFirst()) {
                    while (baggageBag.next()){
                        String bagName = baggageBag.getString("name");
                        double value = baggageBag.getInt("value");
                        baggage.add(new Bagazh( bagName, value));
                    }
                }
                map.put(hm, baggage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
