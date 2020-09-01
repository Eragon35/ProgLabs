package Lab7;

import java.io.Serializable;

public class User implements Serializable {
    String name;
    String password;
    int id;

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public String getPassword() { return password; }
    public int getId() { return id; }
}
