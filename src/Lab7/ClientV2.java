package Lab7;

import Lab3.Humanoid;
import Lab3.Predmet;
import Lab6.*;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;

@SuppressWarnings("DuplicatedCode")
public class ClientV2 {
    /*
    variant xxx
     */
    static int size = 0;
    static int port = 11111;
    private static final Scanner scanner = new Scanner(System.in);
    static Response response;

    public static void main(String[] args) {
        String s = "";
        Request request = new Request();
        SortedMap<Humanoid, List<Predmet>> map;
//        if (args.length == 0){
//            port = 1024 + (int) (Math.random() * 48128);
//            if (port == 1111) port++;
//            System.out.println("You haven't define port, you port is " + port);
//        }
//        else {
//            port = Integer.parseInt(args[0]);
//            if((port > 65536) || (port < 1024)) {
//                System.out.println("You define bullshit port, try one more time");
//                System.exit(0);
//            }
//        }

        signIn(request);


        map = response.getMap();
        size = map.size();

//        reading command from cli and preparing it to send to server
        while (!s.equals("exit")) {
            System.out.print("Введите команду:");
            String str = scanner.nextLine();
            if (str.contains("null")) System.out.println("Параметр не может быль null");
            else ConsoleInput.reader(request, str);
            s = str;

            if (request.getCommand().equals(ClientCommand.other)) continue;
            write(request);
//                send exit command to server to save collection to file
            if(request.getCommand().equals(ClientCommand.exit)) System.exit(0);
//                waiting response and do sout it to cli
            response = read();
            assert response != null;
            if (response.getCommand().equals(ServerCommand.success)) {
                map = response.getMap();
                ConsoleOutput.write(response.getMap(), request.getCommand());

                size = map.size();
            } else System.out.println("Shit happens, server send error");
        }


    }
    private static void signIn(Request request){
        boolean logIn = false;
        while (!logIn){
            System.out.print("Username:");
            String username = scanner.nextLine();
            if(username.contains("create")){
                create();
            }
            else {
                System.out.print("Password:");
                String password = scanner.nextLine();
                User user = new User(username, Cryptography.encrypt(password));
                request.setCommand(ClientCommand.sign_in);
                request.setUser(user);
                write(request);
                response = read();
                assert response != null;
//                TODO: think about necessity of UserId
                user.setId(response.getUserId());
                if (response.getCommand().equals(ServerCommand.success)){
                    logIn = true;
                    System.out.println("Welcome to lab 7 by Antipin Arsentii,\nvariant xxxxxx");
                }
                else if (response.getCommand().equals(ServerCommand.auth_error_user_not_found)){
                    System.out.println("Authorization failed: user not found!\nTry one more time");
                }
                else if (response.getCommand().equals(ServerCommand.auth_error_wrong_password)){
                    System.out.println("Authorization failed:wrong password!\nTry one more time");
                }
                else if(response.getCommand().equals(ServerCommand.error)){
                    System.out.println("Try one more time error on server");
                }
                else System.out.println("All failed!\nTry one more time");
            }
        }
    }
    private static void create() {
        System.out.print("Print username:");
        String username = scanner.nextLine();
        System.out.print("Print password:");
        String password = scanner.nextLine();
        System.out.print("Confirm password:");
        String password1 = scanner.nextLine();
        if (password.equals(password1)){
            User user = new User(username, Cryptography.encrypt(password));
            Request request = new Request();
            request.setCommand(ClientCommand.add_user);
            request.setUser(user);
            write(request);
            response = read();
            assert response != null;
            if(response.getCommand().equals(ServerCommand.success)){
                System.out.println("Success! Your account has been created.");
            }
            else System.out.println("Error! Something goes wrong.");
        }
        else System.out.println("Passwords are different try one more time");
    }
    private static void write(Request request){
        try (DatagramSocket socket = new DatagramSocket()){
            request.setAddress(new InetSocketAddress(InetAddress.getLocalHost(), port));
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1024);
            ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(bos));
            oos.flush();
            oos.writeObject(request);
            oos.flush();
            byte[] sendBuf = bos.toByteArray();
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, InetAddress.getLocalHost(), 1111);
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
            channel.socket().bind(new InetSocketAddress(port));
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
