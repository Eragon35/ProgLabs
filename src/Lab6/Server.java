package Lab6;


import Lab3.Humanoid;
import Lab3.Predmet;
import Lab5.InputFile;
import Lab5.OutputFile;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.*;

public class Server {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("\nBeginning of Lab6, variant 11250");

        String filename = "test_1.csv";
        SortedMap<Humanoid, List<Predmet>> map = new TreeMap<>();
        Request rqst;
        Response rsp = new Response();

        //writing collection from file
        File file = new File(filename);
        if (file.exists() && !file.isDirectory()) {
            InputFile.parser(filename, map);
            System.out.println("Read collection");
        }
        else {
            System.out.println("File not found.");
            System.exit(0);
        }
        while (true) {
            //waiting Command from client
            rqst = receive();
            assert rqst != null;
            System.out.println("Income: " + rqst.getCommand());
            if (rqst.getCommand().equals(ClientCommand.exit)){
                //save file
                OutputFile.writeCSV(filename, map);
            }
            else {
                ServerHandler.reader(rqst, map, rsp);
                //send response to client
                Thread.sleep(100);
                send(rsp);
            }
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
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress address = InetAddress.getLocalHost();
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1024);
            ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
            os.flush();
            os.writeObject(response);
            os.flush();
            //retrieves byte array
            byte[] sendBuf = byteStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, 11111);
            socket.send(packet);
            os.close();
            byteStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
