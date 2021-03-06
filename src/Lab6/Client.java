package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;

public class Client {
    /*
    variant 11250
     */

    static int size = 0;
    static Response response = null;

//    public static void main(String[] args) {
//        System.out.println("\nBeginning of Lab6, variant 11250");
//        String s = "";
//        Request request = new Request();
//        SortedMap<Humanoid, List<Predmet>> map;
//
////        first connection with server for map initialization
//        request.setCommand(ClientCommand.get_map);
//        write(request);
//        System.out.println("Send initiation request");
//        response = read();
////        assert response != null;
//        map = response.getMap();
//        size = map.size();
//
////        reading command from cli and preparing it to send to server
//        while (!s.equals("exit")) {
//            System.out.print("Введите команду:");
//            Scanner scanner = new Scanner(System.in);
//            String str = scanner.nextLine();
//            if (str.contains("null")) System.out.println("Параметр не может быль null");
//            else ConsoleInput.reader(request, str);
//            s = str;
//
////            if command type isn't local send request to server else do it local else
//            if (request.getCommand().equals(ClientCommand.other)) continue;
////            if (request.getCommand().isLocal()) {
////                System.out.print("Ваша команда была выполнена локально: ");
////                ConsoleOutput.write(map, request.getCommand());
////            }
////            else {
//                write(request);
////                send exit command to server to save collection to file
//                if(request.getCommand().equals(ClientCommand.exit)) System.exit(0);
////                waiting response and do sout it to cli
//                response = read();
//                assert response != null;
//                if (response.getCommand().equals(ServerCommand.success)) {
//                    map = response.getMap();
//                    ConsoleOutput.write(response.getMap(), request.getCommand());
//
//                    size = map.size();
//                } else System.out.println("Shit happens, server send error");
////            }
//        }
//    }
    private static void write(Request request){
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.bind(null);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(request);
            oos.flush();
            oos.close();
            bos.close();
            byte[] sendBuf = bos.toByteArray();
            channel.send(ByteBuffer.wrap(sendBuf), new InetSocketAddress(InetAddress.getLocalHost(), 1111));
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Response read(){
        try (DatagramChannel channel = DatagramChannel.open()){
            byte[] recvBuf = new byte[1024];
            channel.socket().bind(new InetSocketAddress(11111));
            channel.socket().setSoTimeout(5000);
            channel.configureBlocking(false);
            Thread.sleep(500);
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
        catch (StreamCorruptedException ex){
            System.out.println("Can't connect to server.\nShut down the program");
            System.exit(0);
            return null;
        }
        catch (IOException | ClassNotFoundException | InterruptedException e){
            e.printStackTrace();
            return null;
        }

    }
}
