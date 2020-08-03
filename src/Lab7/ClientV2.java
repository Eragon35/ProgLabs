package Lab7;

import Lab6.ClientCommand;
import Lab6.Request;
import Lab6.Response;
import Lab6.ServerCommand;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class ClientV2 {
    /*
    variant xxxxxx
     */
    private static Scanner scanner = new Scanner(System.in);
    static Response response;

    public static void main(String[] args) {
        Request request = new Request();


        boolean logIn = false;
        while (!logIn){
//            TODO: вынести авторизацию в отдельный метод
            System.out.print("Username:");
            String username = scanner.nextLine();
            if(username.contains("create")){
                create(request);
            }
            else {
                System.out.print("Password:");
                String password = scanner.nextLine();
                User user = new User(username, encrypt(password));

                int id = Authorization.signIn(user);
                //send request and read response

                if (id != -1) {
                    user.setId(id);
                    logIn = true;
                } else System.out.println("Authorization failed!\nTry one more time");
            }
        }


    }
    private static String encrypt(String psw) {
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
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return null;
    }
    private static void create(Request request) {
        System.out.print("Print username:");
        String username = scanner.nextLine();
        System.out.print("Print password:");
        String password = scanner.nextLine();
        System.out.print("Confirm password:");
        String password1 = scanner.nextLine();
        if (password.equals(password1)){
            User user = new User(username, encrypt(password));
            request.setCommand(ClientCommand.add_user);
            request.setUser(user);
            write(request);
            response = read();
            if(response.getCommand().equals(ServerCommand.success)){
                System.out.println("Success! Your account has been created.");
            }
            else System.out.println("Error! Something goes wrong.");
        }
        else System.out.println("Passwords are different try one more time");
    }
    private static void write(Request request){
        try (DatagramSocket socket = new DatagramSocket()){
            InetAddress address = InetAddress.getLocalHost();
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(bos));
            oos.flush();
            oos.writeObject(request);
            oos.flush();
            byte[] sendBuf = bos.toByteArray();
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, 1111);
            socket.send(packet);
            oos.close();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Response read(){
        try (DatagramChannel channel = DatagramChannel.open()){
            byte[] recvBuf = new byte[1024];
            channel.socket().bind(new InetSocketAddress(11111));
            ByteBuffer buffer = ByteBuffer.wrap(recvBuf);
            buffer.clear();
            channel.receive(buffer);
            ByteArrayInputStream bis = new ByteArrayInputStream(recvBuf);
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bis));
            Response response = (Response) ois.readObject();
            bis.close();
            ois.close();
            channel.close();
            return response;
        }
        catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }
}
