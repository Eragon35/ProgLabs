package Lab7;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class ClientV2 {
    /*
    variant xxxxxx
     */
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {


        boolean logIn = false;
        while (!logIn){
            System.out.print("Username: ");
            String username = scanner.nextLine();
            if(username.contains("create")){
                create();
            }
            System.out.print("Password: ");
            String password = scanner.nextLine();
            User user = new User(username, encrypt(password));
            int id = Authorization.signIn(user);
            if (id != -1){
                user.setId(id);
                logIn = true;
            }
            else System.out.println("Authorization failed");
        }

    }
    private static String encrypt(String psw) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] data = psw.getBytes();
            md.update(data);
            byte[] bytes = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aByte : bytes) {
                String hex = Integer.toHexString(0xFF & aByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
            }
            return hexString.toString();
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return null;
    }
    private static void create() {
        System.out.print("Print username: ");
        String username = scanner.nextLine();
        System.out.print("Print password: ");
        String password = scanner.nextLine();
        int id = Authorization.addUser(new User(username,encrypt(password)));

    }
}
