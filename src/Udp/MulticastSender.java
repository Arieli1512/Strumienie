package Udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class MulticastSender {
    static void main() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Press enter to start");
        scanner.nextLine();

        try(DatagramSocket datagramSocket = new DatagramSocket())
        {
            String message = "Hello from MulticastSender";
            InetAddress group = InetAddress.getByName("239.255.255.255");

            DatagramPacket packet = new DatagramPacket(message.getBytes(), message.length(), group, 5678);
            for (int i = 0; i < 5; i++) {
                System.out.println("Sending: #" + i);
                datagramSocket.send(packet);
                Thread.sleep(500);
            }
        }
        catch (Exception e)
            {
            System.out.println("Sender e: " + e.getMessage());
            }
    }
}
