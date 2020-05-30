package Lab6;

import Lab3.Humanoid;
import Lab3.Predmet;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Client {
    /*
    variant 11250
     */

    public static void main(String[] args) {
        System.out.println("\nBeging of Lab6, variant 11250");
        String s = " ";
        Command cmd = new Command();
        SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();


        //reading command from cli and preparing it to send to server
        while (!s.equals("exit")) {
            System.out.print("Введите команду:");
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            if (str.contains("null")) System.out.println("Параметр не может быль null");
            else ConsoleV2.reader(cmd, str);
            s = str;
            if (cmd.getCommand().equals(ClientCommand.other)) System.out.println("Ваша команда была выполнена локально. Запрос на сервер не отправлен");
            else {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
                    ObjectOutputStream oos = new ObjectOutputStream(baos);
                    oos.writeObject(cmd);
                    final byte[] data = baos.toByteArray();

                    final DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), Integer.parseInt(args[0]));
                    DatagramSocket socket = new DatagramSocket();
                    socket.send(packet);
                    System.out.println("Запрос отправлен");


                }catch (Exception e){

                }
//                try(DatagramSocket recieveSocket = new DatagramSocket(Integer.parseInt(args[0]))){
//                    byte[] recieveBuf = new byte[1024];
//                    DatagramPacket packet = new DatagramPacket(recieveBuf, recieveBuf.length);
//                    recieveSocket.receive(packet);
//                    int byteCount = packet.getLength();
//                    ByteArrayInputStream bais = new ByteArrayInputStream(recieveBuf);
//                    ObjectInputStream ois = new ObjectInputStream(bais);
//                    Response rsp = (Response) ois.readObject();
//                }catch (IOException| ClassNotFoundException e){
//
//                }

                //sending Command to server
            }

            //waiting response and do sout it to cli
        }



    }
    public static void main2(String[] args) throws IOException, InterruptedException {

        try (DatagramSocket socket = new DatagramSocket(Integer.parseInt(args[0]))) {

            DatagramPacket packet = encodePacket("Hello world!");
//            packet.setSocketAddress(new InetSocketAddress(11111));
            socket.send(packet);
//            while (!socket.isConnected()){
//                System.out.println("waiting");
//                Thread.sleep(1000);
//            }
//            System.out.println("Connected");
        }

    }
    private static DatagramPacket encodePacket(String text) throws UnknownHostException {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        return new DatagramPacket(bytes, bytes.length, InetAddress.getLocalHost(), 11111);
    }
}
