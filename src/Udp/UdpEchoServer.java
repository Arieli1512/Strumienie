package Udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UdpEchoServer
{
    static void main() {
        try(DatagramSocket socket = new DatagramSocket(5678)){
             System.out.println("Server started");

             byte[] buffer = new byte[1024];
             while (true)
             {
                 //odbieranie pakietu
                 DatagramPacket request = new DatagramPacket(buffer, buffer.length);
                 socket.receive(request);

                 String message = new String(request.getData(), 0, request.getLength());
                 System.out.println("Received: " + message);

                 // wysylanie odpowiedzi
                 DatagramPacket response = new DatagramPacket(
                         message.getBytes(),
                         message.length(),
                         request.getAddress(),
                         request.getPort()
                 );

                 socket.send(response);
                 System.out.println("Sent: " + message);


             }
        }
        catch (Exception e) {
            System.out.println("Server Error " + e.getMessage());
        }
    }
}
