package streams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer
{
static void main()
{
    try (ServerSocket serverSocket = new ServerSocket(5678, 10)) {
        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();

                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                String request;
                while (true) {
                    request = input.readLine();
                    if (request == null) {
                        System.out.println("Client " + clientSocket.getPort() + " disconnected");
                        break;
                    }
                    System.out.printf("Received request from #%d: %s\n", clientSocket.getPort(),request);
                    if (request.equals("exit")) {
                        System.out.println("Client " + clientSocket.getPort() + " disconnected");
                        break;
                    }
                        output.println(request);
                }
            } catch (IOException e) {
                System.out.println("Client exception" + e.getMessage());
            }
        }
    } catch (IOException e) {
        IO.println("Server Error"+ e.getMessage());
    }

}
}
