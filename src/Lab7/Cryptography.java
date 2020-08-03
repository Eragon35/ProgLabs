package Lab7;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cryptography {
    public static String encrypt(String psw) {
        try {

            MessageDigest md = MessageDigest.getInstance("MD5"); //change param to switch to another crypto algorithm

            byte[] data = psw.getBytes();
            md.update(data);
            byte[] bytes = md.digest();
            StringBuilder hexString = new StringBuilder();
            for (byte aByte : bytes) {
                String hex = Integer.toHexString(0xFF & aByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            System.out.println(hexString.toString());
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }
}
