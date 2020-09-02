package Lab7;

import Lab6.Response;
import Lab6.ServerCommand;

import java.sql.*;

public class Authorization {
    /*
    signIn return 0 if user.name not found
    return -1 if wrong password
    if user.name found and password hashes are equal return user.Id
     */
    public static int signIn(User user) throws ClassNotFoundException {
        int id = 0;
        String password = "";
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DBconfigs.url, DBconfigs.dbUser, DBconfigs.dbPassword)) {
            Statement statement = connection.createStatement();
            String sql = "SELECT id, password FROM s207704.user WHERE name = '" + user.getName() + "'";
            ResultSet resultSet = statement.executeQuery(sql);
            if (!resultSet.isBeforeFirst() ) {
                System.out.println("user not found");
                return 0;
            }
            else {
                while (resultSet.next()) {
                    password = resultSet.getString("password");
                    id = resultSet.getInt("id");
                }
                if (password.equals(user.getPassword())) {
                    System.out.println("Password is right for " + user.getName());
                    return id;
                } else {
                    System.out.println("Wrong password for " + user.getName());
                    return  -1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -2;
    }
        public static void addUser (User user, Response response) throws ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        try (Connection connection = DriverManager.getConnection(DBconfigs.url, DBconfigs.dbUser, DBconfigs.dbPassword)){
            Statement statement = connection.createStatement();
            String checkName = "SELECT \"name\" FROM s207704.user";
            ResultSet resultSet = statement.executeQuery(checkName);
            if (!resultSet.isBeforeFirst() ) {
                System.out.println("Name already defined");
                response.setCommand(ServerCommand.auth_error_user_already_exist);
            }
            else {
                System.out.println("Trying to add user");
                String sql = "INSERT INTO public.user (name, password) Values (?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, user.name);
                preparedStatement.setString(2, user.password);
                int rows = preparedStatement.executeUpdate();
                System.out.printf("%d rows added", rows);
                response.setCommand(ServerCommand.success);
            }
    } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
//    return authenticator.put(user, authenticator.size()+1) == null ? authenticator.get(user) : -1;
