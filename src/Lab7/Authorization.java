package Lab7;

import java.util.HashMap;
import java.util.Map;

public class Authorization {

//    TODO: add singleton https://habr.com/ru/post/129494/

    private static Map<User, Integer> authenticator = new HashMap<>();
    public static int signIn(User user){
        return authenticator.getOrDefault(user, -1);
    }
    public static int addUser (User user){
        return authenticator.put(user, authenticator.size()+1) == null ? authenticator.get(user) : -1;
    }
}
