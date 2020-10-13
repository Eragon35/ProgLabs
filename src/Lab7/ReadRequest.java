package Lab7;

import Lab6.Request;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.BindException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;


import static Lab7.ServerV2.requestQueue;

public class ReadRequest implements Runnable {

    @Override
    public void run() {
        while (true) {
            try (DatagramSocket socket = new DatagramSocket(ServerV2.port)) {
                byte[] recvBuf = new byte[1024];
                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
                socket.receive(packet);
                ByteArrayInputStream bis = new ByteArrayInputStream(recvBuf);
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bis));
                Request request = (Request) ois.readObject();
                bis.close();
                ois.close();
                socket.close();
                requestQueue.add(request);
            } catch (BindException ex){
                System.out.println("Can't get address");
                ex.printStackTrace();
                System.exit(35);
            }catch (ClassNotFoundException | IOException e) {
                e.printStackTrace();
            }

//            try (DatagramSocket socket = new DatagramSocket(1111)) {
//                byte[] recvBuf = new byte[1024];
//                DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
//                socket.receive(packet);
//                ByteArrayInputStream bis = new ByteArrayInputStream(recvBuf);
//                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(bis));
//                Request request = (Request) ois.readObject();
//                bis.close();
//                ois.close();
//                requestQueue.add(request);
//                System.out.println("Read request from " + request.getUser().getName());
//            } catch (BindException e){
//                System.out.println("Can't get address");
//                e.printStackTrace();
//                System.exit(35);
//            }
//            catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
        }
    }
}
