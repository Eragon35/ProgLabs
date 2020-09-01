package Lab7;

import Lab6.Request;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.Callable;

public class ReadRequest implements Callable<Request> {

    @Override
    public Request call() throws Exception {
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
}
