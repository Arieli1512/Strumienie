package Udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class MulticastReceiver {
    static void main() {
        try(MulticastSocket socket = new MulticastSocket(5678))
        {
            socket.setSoTimeout(5000);
            InetAddress group = InetAddress.getByName("239.255.255.255");
            socket.joinGroup(group);

            byte[] buffor = new byte[1024];
            System.out.println("Waiting for messages");

            for (int i = 0; i < 10; i++) {
                DatagramPacket packet = new DatagramPacket(buffor, buffor.length);
                socket.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received: " + message + " #"+ i);


                if (i==3)
                {
                    socket.leaveGroup(group);
                    System.out.println("Left the group");

                }
            }

        }
        catch (Exception e)
        {
            System.out.println("Error " + e.getMessage());
        }
    }
}
