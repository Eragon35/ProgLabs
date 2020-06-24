package Lab6;


import Lab3.Humanoid;
import Lab3.Predmet;
import Lab5.InputFile;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;

public class Server {

    //TODO add checking from cli for saving and turn off server and loop

    public static void main(String[] args) {
        System.out.println("\nBeging of Lab6, variant 11250");
        int port = 11111;
        String filename = "test_1.csv";
        SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();
        Request rqst;
        Response rsp = new Response();

        if (args.length == 0){
            System.out.println("You haven't define port, set default 11111");
        }
        else port = Integer.parseInt(args[0]);


        //writing collection from file
        File file = new File(filename);
        if (file.exists() && !file.isDirectory()) {
            InputFile.parser(filename, map);
            System.out.println("Read collection");
        }
        else {
            System.out.println("File not found.");
            java.lang.System.exit(0);
        }

        //waiting Command from client
        while (true) {
            rqst = receive(port);
            assert rqst != null;
            System.out.println("Request: " + rqst.getCommand());


            //do request on server
            try {
                ServerHandler.reader(rqst, map, rsp);
            } catch (Exception e) {
                rsp.setCommand(ServerCommand.error);
                e.printStackTrace();
            }

            //send response to client
            write(rsp, port);
        }
    }

    private static Request receive (int port){
        try ( DatagramSocket socket = new DatagramSocket(port))
        {
            byte[] recvBuf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
            socket.receive(packet);
            ByteArrayInputStream in = new ByteArrayInputStream(recvBuf);
            ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(in));
            return (Request) is.readObject();
        }
        catch (ClassNotFoundException | IOException e)
        {
            e.printStackTrace();
        }
        //TODO: think about more beautiful return
        return null;
    }

    private static void write(Response rsp, int port) {
        try(DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            channel.bind(null);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(rsp);
            oos.flush();
            oos.close();
            bos.close();
            byte[] sendBuf = bos.toByteArray();
//            TODO: rework to use port from args[]
            channel.send(ByteBuffer.wrap(sendBuf), new InetSocketAddress(InetAddress.getLocalHost(), port));
            System.out.println("Response send " + rsp.getMap().size());
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
