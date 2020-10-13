package Lab7;

import Lab6.Response;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class SendResponse implements Runnable {
    private final Response response;

    public SendResponse(Response response) { this.response = response; }

    @Override
    public void run() {
        try (DatagramSocket socket = new DatagramSocket()) {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(4096);
            ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
            os.flush();
            os.writeObject(response);
            os.flush();
            //retrieves byte array
            byte[] sendBuf = byteStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, response.getAddress());
            socket.send(packet);
            os.close();
            byteStream.close();
            System.out.println("Send response to user with id = " + response.getUserId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
