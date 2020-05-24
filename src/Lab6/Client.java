package Lab6;

import Lab3.Humanoid;
import Lab3.Palace;
import Lab3.Pismak;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.Buffer;
import java.nio.charset.StandardCharsets;

public class Client {
    /*
    variant 11250
     */
    public static void main(String[] args) throws IOException {
        try (DatagramSocket socket = new DatagramSocket()) {
            DatagramPacket packet = encodePacket("Hello world!");
//            packet.setSocketAddress(new InetSocketAddress(11111));
            socket.send(packet);
        }

    }
    private static DatagramPacket encodePacket(String text) throws UnknownHostException {
        byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        return new DatagramPacket(bytes, bytes.length, InetAddress.getLocalHost(), 11111);
    }
}
