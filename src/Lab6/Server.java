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
        Command cmd = new Command();


        //writing collection from file
        File file = new File(filename);
        if (file.exists() && !file.isDirectory()) {
            InputFile.parser(filename, map);
        }
        else System.out.println("File not found.");


        //waiting Command from client

        try ( DatagramSocket socket = new DatagramSocket (Integer.parseInt(args[0]))) {
            byte [] buf = new byte [1024];
            DatagramPacket packet = new DatagramPacket (buf, buf.length);
            socket.receive(packet);

            ByteArrayInputStream bais = new ByteArrayInputStream(buf);
            ObjectInputStream ois = new ObjectInputStream(bais);

            cmd = (Command) ois.readObject();
            ois.close();
            System.out.println(cmd.getHuman().getName() + " " + cmd.getHuman().getPlace() + " " + cmd.getCommand().toString());



//            while (true){
//                socket.receive(packet);
//                cmd = (Command) ois.readObject();
//                System.out.println(cmd.getHuman().getName() + " " + cmd.getHuman().getPlace() + " " + cmd.getCommand().toString());
//            }
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Error");
            e.printStackTrace();
        }

        //do it on server
//        ServerHandler.reader(cmd, map);

        //send request to clientg

    }
}
