package Lab7;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/*
temporary class instead of bd
server should ask
 */

public class Authorization {

//    think about add singleton https://habr.com/ru/post/129494/


    private static Map<User, Integer> authenticator = new HashMap<>();
    /*
    signIn return 0 if user.name not found
    return -1 if wrong password
    if user.name found and password hashes are equal return user.Id
     */
    public static int signIn(User user){
        return authenticator.getOrDefault(user, -1);
//        with db use
//        select id, password from user
//        where name = 'name';

    }
    public static void addUser (User user) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DBconfigs.url, DBconfigs.dbUser, DBconfigs.dbPassword)){
//            Statement statement = connection.createStatement();
            String sql = "INSERT INTO user (name, password) Values (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.name);
            preparedStatement.setString(2, user.password);
            int rows = preparedStatement.executeUpdate();
            System.out.printf("%d rows added", rows);
    } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
//    return authenticator.put(user, authenticator.size()+1) == null ? authenticator.get(user) : -1;
