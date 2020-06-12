package Lab6;


import Lab3.Humanoid;
import Lab3.Predmet;
import Lab5.InputFile;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
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
        Request rqst = new Request();
        Response rsp = new Response();
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
        else {
            System.out.println("File not found.");
            java.lang.System.exit(0);
        }


        //waiting Command from client
        try ( DatagramSocket socket = new DatagramSocket(port))
        {
            byte[] recvBuf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
            socket.receive(packet);
            rqst = (Request) deserialize(recvBuf);
        }
        catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
        }
        Humanoid hm = rqst.getHuman();
        baggage = rqst.getBaggage();
        System.out.println(rqst.getCommand().toString() + " " + hm.getName() + " " + hm.getPlace().toString()  + " " + baggage.size());


        //do request on server
        try {
            ServerHandler.reader(rqst, map, rsp);
        }catch (Exception e){
            rsp.setCommand(ServerCommand.error);
            e.printStackTrace();
        }

        //send response to client

    }
    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(in));
        return is.readObject();
    }
}
