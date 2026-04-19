package Udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class UdpEchoClient {
    static void main() {
        try(DatagramSocket socket = new DatagramSocket()) {
            Scanner scanner = new Scanner(System.in);
            while (true)
            {
                // wysylanie pakietu

                System.out.print("Enter message: ");
                String message = scanner.nextLine();

                if(message.equalsIgnoreCase("exit"))
                    break;

                DatagramPacket request = new DatagramPacket(
                        message.getBytes(),
                        message.length(),
                        InetAddress.getByName("localhost"),
                        5678
                );
                socket.send(request);

                byte[] buffer = new byte[1024];
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);

                String responseMessage = new String(response.getData(), 0, response.getLength());
                System.out.println("Received: " + responseMessage);
            }
        }catch (Exception e) {
            System.out.println("Client Error " + e.getMessage());
        }
    }
}
