package Lab6;


import Lab3.Humanoid;
import Lab3.Predmet;
import Lab5.InputFile;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Server {
    public static Date datePublic = new Date();

    //TODO add checking from cli for saving and turn off server
    public static void main(String[] args) {
        System.out.println("\nBeging of Lab6, variant 11250");
        datePublic = new Date();
        String filename = "test_1.csv";
        SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();
        List<Predmet> baggage = new LinkedList<>();
        Command cmd = new Command();
        int port = 11111;
        if (args.length == 0){
            System.out.println("You haven't define port, set default 11111");
        }
        else port = Integer.parseInt(args[0]);


        //writing collection from file
        File file = new File(filename);
        if (file.exists() && !file.isDirectory()) {
            InputFile.parser(filename, map);
        }
        else System.out.println("File not found.");


        //waiting Command from client
        try ( DatagramSocket socket = new DatagramSocket(port))
        {
            byte[] recvBuf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
            socket.receive(packet);
            ByteArrayInputStream byteStream = new ByteArrayInputStream(recvBuf);
            ObjectInputStream inputStream = new ObjectInputStream(new BufferedInputStream(byteStream));
            cmd = (Command) inputStream.readObject();
            inputStream.close();
        }
        catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
        }
//        Humanoid hm = cmd.getHuman();
//        baggage = cmd.getBaggage();
//        System.out.println(cmd.getCommand().toString() + " " + hm.getName() + " " + hm.getPlace().toString()  + " " + baggage.size());


        //do request on server
        ServerHandler.reader(cmd, map);

        //send response to client

    }
}
