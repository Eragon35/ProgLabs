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
    static int port = 11111;

    //TODO add checking from cli for saving and turn off server and loop

    public static void main(String[] args) {
        System.out.println("\nBeging of Lab6, variant 11250");
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
        }
        else {
            System.out.println("File not found.");
            java.lang.System.exit(0);
        }
        while (true) {
            //waiting Command from client

            rqst = receive();
            assert rqst != null;
            System.out.println("Income: " + rqst.getCommand());

            ServerHandler.reader(rqst, map, rsp);

            //send response to client
            send(rsp);
        }
    }
    private static Request receive(){
        try (DatagramSocket socket = new DatagramSocket(1111)) {
            byte[] recvBuf = new byte[1024];
            DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);

            socket.receive(packet);
            ByteArrayInputStream bis = new ByteArrayInputStream(recvBuf);
            ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bis));
            Request request =(Request) ois.readObject();
            bis.close();
            ois.close();
            socket.close();
            return request;
        }catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private static void send(Response response){
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            channel.bind(null);

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(response);
            oos.flush();
            oos.close();
            bos.close();
            byte[] sendBuf = bos.toByteArray();

            channel.send(ByteBuffer.wrap(sendBuf), new InetSocketAddress(InetAddress.getLocalHost(), 11111));
            channel.close();
            System.out.println("response send");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
