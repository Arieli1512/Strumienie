package ThreadedServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadedServer {
    static void main() {

        ExecutorService executorService = Executors.newFixedThreadPool(20);

        try(ServerSocket server = new ServerSocket(5678, 10)) {
            while (true) {
                Socket client = server.accept();
                executorService.submit(new ClientHandler(client));
            }
        } catch (IOException e) {
            System.out.println("Server Error " + e.getMessage());
        }finally {
            executorService.shutdown();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        public ClientHandler(Socket clientSocket) {
            this.clientSocket = clientSocket;
        }
        @Override
        public void run() {
            try {
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);

                String request;
                while (true) {
                    request = input.readLine();
                    if (request == null) {
                        System.out.println("Client " + clientSocket.getPort() + " disconnected");
                        break;
                    }
                    System.out.printf("Received request from #%d: %s\n", clientSocket.getPort(), request);
                    if (request.equals("exit")) {
                        System.out.println("Client " + clientSocket.getPort() + " disconnected");
                        break;
                    }
                    output.println(request);
                }
            } catch (IOException e) {
                System.out.println("Client " + clientSocket.getPort() + " "+ e.getMessage());
            }finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
