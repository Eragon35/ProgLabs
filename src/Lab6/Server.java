package Lab6;


import Lab3.Humanoid;
import Lab3.Predmet;
import Lab5.InputFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class Server {
    public static Date datePublic = new Date();

    //TODO add checking from cli for saving and turn off server
    public static void main(String[] args) {
        System.out.println("\nBeging of Lab6, variant 11250");
        datePublic = new Date();
        String filename = "test_1.csv";
        SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();
        Command cmd;


        //writing collection from file
        System.out.println(filename);
        File file = new File(filename);
        if (file.exists() && !file.isDirectory()) {
            InputFile.parser(filename, map);
        }
        else System.out.println("File not found.");


        //waiting Command from client

        //do it on server

        //send request to client

    }
//    public static void main(String[] args) throws IOException {
//        try ( DatagramSocket socket = new DatagramSocket (11111)) {
//            byte [] buf = new byte [1024];
//            DatagramPacket packet = new DatagramPacket (buf, buf.length);
//            ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(packet.getData(), packet.getOffset(), packet.getLength()));
//            while (true){
//                socket.receive(packet);
//                System.out.println(decodePacket(packet));
//                System.out.println(new String(packet.getData()));
//            }
//        } catch (SocketException e){
//            System.out.println("Error");
//        }
//
//    }
//    private static String decodePacket(DatagramPacket packet){
//        return new String(
//                packet.getData(),
//                packet.getOffset(),
//                packet.getOffset() + packet.getLength(),
//                StandardCharsets.UTF_8);
//    }

}
