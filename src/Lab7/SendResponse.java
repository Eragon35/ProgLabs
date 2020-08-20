package Lab7;

import Lab6.Response;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class SendResponse extends Thread{
    private Response response;
    private InetSocketAddress address;

    public SendResponse(Response response, InetSocketAddress address) {
        this.response = response;
        this.address = address;
    }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(1024);
            ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
            os.flush();
            os.writeObject(response);
            os.flush();
            //retrieves byte array
            byte[] sendBuf = byteStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address);
            socket.send(packet);
            os.close();
            byteStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
