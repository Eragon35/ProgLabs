package Lab6;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class Server {
    public static void main(String[] args) throws SocketException, IOException {
        try ( DatagramSocket socket = new DatagramSocket (11111)) {
            byte [] buf = new byte [1024];
            DatagramPacket packet = new DatagramPacket (buf, buf.length);
            while (true){
                socket.receive(packet);
                System.out.println(decodePacket(packet));
            }
        }

    }
    private static String decodePacket(DatagramPacket packet){
        return new String(
                packet.getData(),
                packet.getOffset(),
                packet.getOffset() + packet.getLength(),
                StandardCharsets.UTF_8);
    }

}
